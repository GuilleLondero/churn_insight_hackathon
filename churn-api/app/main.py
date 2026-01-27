"""
FastAPI principal - Microservicio de predicción de churn.
Este archivo contiene todos los endpoints de la API.
"""
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import dill
import pandas as pd
from pathlib import Path
from typing import Dict, Any
import logging

from app.schemas import PredictionRequest, PredictionResponse
from app.utils import get_top_features

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(
    title="ChurnInsight API",
    description="API de prediccion de churn con XGBoost calibrado",
    version="2.0"
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

MODEL = None
MODEL_METADATA = None
MODEL_PATH = Path(__file__).parent.parent / "models" / "churn_xgboost_calibrado.pkl"
MODEL_LOADED = False


def load_model():
    global MODEL, MODEL_METADATA, MODEL_LOADED
    
    if MODEL_LOADED:
        return
    
    try:
        logger.info(f"Cargando modelo desde: {MODEL_PATH}")
        
        with open(MODEL_PATH, 'rb') as f:
            data = dill.load(f)
        
        MODEL = data['pipeline']
        MODEL_METADATA = {
            'version': data['version'],
            'umbral': data['umbral'],
            'metricas': data['metricas'],
            'top_3_features': data.get('top_3_features', []),
            'feature_names': data.get('feature_names', [])
        }
        
        MODEL_LOADED = True
        
        logger.info(f"Modelo cargado exitosamente: {MODEL_METADATA['version']}")
        logger.info(f"Umbral: {MODEL_METADATA['umbral']:.4f}")
        logger.info(f"Recall: {MODEL_METADATA['metricas']['recall']:.4f}")
        
    except Exception as e:
        logger.error(f"Error al cargar modelo: {str(e)}")
        raise


@app.on_event("startup")
async def startup_event():
    load_model()


@app.get("/")
def root():
    return {
        "message": "ChurnInsight API - XGBoost Calibrado",
        "version": MODEL_METADATA['version'] if MODEL_METADATA else "unknown",
        "status": "running"
    }


@app.get("/health")
def health_check():
    return {
        "status": "healthy",
        "model_loaded": MODEL is not None,
        "model_version": MODEL_METADATA['version'] if MODEL_METADATA else None,
        "model_type": "XGBoost + CalibratedClassifierCV"
    }


@app.post("/predict", response_model=PredictionResponse)
def predict(request: PredictionRequest) -> PredictionResponse:
    
    if MODEL is None:
        raise HTTPException(status_code=503, detail="Modelo no disponible")
    
    try:
        input_data = {
            'antiguedad': request.antiguedad,
            'plan': request.plan,
            'metodo_pago': request.metodo_pago,
            'facturas_impagas': request.facturas_impagas,
            'frecuencia_uso': request.frecuencia_uso,
            'tickets_soporte': request.tickets_soporte,
            'tipo_contrato': request.tipo_contrato,
            'cambios_plan': request.cambios_plan,
            'canal_adquisicion': request.canal_adquisicion
        }
        
        df_input = pd.DataFrame([input_data])
        
        prediccion_binaria = MODEL.predict(df_input)[0]
        probabilidad_churn = float(MODEL.predict_proba(df_input)[0, 1])
        
        prediccion_str = "cancelara" if prediccion_binaria == 1 else "no_cancelara"
        
        top_features = get_top_features(
            input_data=input_data,
            probabilidad=probabilidad_churn,
            top_3_features_names=MODEL_METADATA.get('top_3_features', [])
        )
        
        response = PredictionResponse(
            prediccion=prediccion_str,
            probabilidad_churn=round(probabilidad_churn, 4),
            umbral_decision=round(MODEL_METADATA['umbral'], 4),
            top_features=top_features,
            modelo_version=MODEL_METADATA['version']
        )
        
        logger.info(f"Prediccion exitosa: {prediccion_str} (prob: {probabilidad_churn:.4f})")
        
        return response
        
    except Exception as e:
        logger.error(f"Error en prediccion: {str(e)}")
        raise HTTPException(status_code=500, detail=f"Error en prediccion: {str(e)}")


@app.get("/model-info")
def model_info():
    
    if MODEL_METADATA is None:
        raise HTTPException(status_code=503, detail="Modelo no disponible")
    
    return {
        "version": MODEL_METADATA['version'],
        "tipo_modelo": "XGBoost + CalibratedClassifierCV",
        "umbral_decision": MODEL_METADATA['umbral'],
        "metricas": MODEL_METADATA['metricas'],
        "top_3_features": MODEL_METADATA.get('top_3_features', []),
        "estrategia": "Recall ~80% (priorizar deteccion de churn)"
    }


# Cargar modelo al importar el modulo (para tests)
load_model()



# Para ejecutar: uvicorn app.main:app --reload