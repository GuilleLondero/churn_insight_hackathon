"""
Schemas de Pydantic para validación de entrada/salida de la API.
Estos modelos validan automáticamente el JSON que llega y se envía.
"""

from pydantic import BaseModel, Field, field_validator
from typing import List, Literal


class PredictionRequest(BaseModel):
    
    antiguedad: int = Field(..., ge=0, description="Meses de antiguedad del cliente")
    plan: Literal["basico", "estandar", "premium"] = Field(..., description="Plan contratado")
    metodo_pago: Literal["tarjeta_credito", "tarjeta_debito", "transferencia_bancaria"] = Field(
        ..., description="Metodo de pago"
    )
    facturas_impagas: int = Field(..., ge=0, description="Cantidad de facturas impagas")
    frecuencia_uso: int = Field(..., ge=0, description="Frecuencia de uso del servicio")
    tickets_soporte: int = Field(..., ge=0, description="Cantidad de tickets de soporte")
    tipo_contrato: Literal["mensual", "anual"] = Field(..., description="Tipo de contrato")
    cambios_plan: int = Field(..., ge=0, description="Cantidad de cambios de plan")
    canal_adquisicion: Literal["web", "referido", "publicidad"] = Field(
        ..., description="Canal de adquisicion del cliente"
    )
    
    class Config:
        json_schema_extra = {
            "example": {
                "antiguedad": 12,
                "plan": "basico",
                "metodo_pago": "tarjeta_credito",
                "facturas_impagas": 1,
                "frecuencia_uso": 25,
                "tickets_soporte": 3,
                "tipo_contrato": "mensual",
                "cambios_plan": 0,
                "canal_adquisicion": "web"
            }
        }


class TopFeature(BaseModel):
    
    feature: str = Field(..., description="Nombre de la feature")
    valor_cliente: str = Field(..., description="Valor del cliente para esta feature")
    impacto: str = Field(..., description="Nivel de impacto en churn")
    importancia: float = Field(..., description="Importancia de la feature en el modelo")


class PredictionResponse(BaseModel):
    
    prediccion: Literal["cancelara", "no_cancelara"] = Field(..., description="Prediccion de churn")
    probabilidad_churn: float = Field(..., ge=0, le=1, description="Probabilidad de churn (0-1)")
    umbral_decision: float = Field(..., ge=0, le=1, description="Umbral usado para la decision")
    top_features: List[TopFeature] = Field(..., description="Top 3 features mas influyentes")
    modelo_version: str = Field(..., description="Version del modelo")
    
    class Config:
        json_schema_extra = {
            "example": {
                "prediccion": "cancelara",
                "probabilidad_churn": 0.7234,
                "umbral_decision": 0.2190,
                "top_features": [
                    {
                        "feature": "tickets_soporte",
                        "valor_cliente": "5",
                        "impacto": "alto_riesgo",
                        "importancia": 0.1692
                    },
                    {
                        "feature": "tipo_contrato",
                        "valor_cliente": "mensual",
                        "impacto": "alto_riesgo",
                        "importancia": 0.1017
                    },
                    {
                        "feature": "facturas_impagas",
                        "valor_cliente": "2",
                        "impacto": "alto_riesgo",
                        "importancia": 0.0854
                    }
                ],
                "modelo_version": "v2.0_xgboost_calibrado"
            }
        }