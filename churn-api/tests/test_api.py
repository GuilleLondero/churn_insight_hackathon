"""
Tests automáticos para la API de ChurnInsight.
Ejecutar con: pytest tests/test_api.py -v
"""
import pytest
from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)


def test_root():
    response = client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert "message" in data
    assert "version" in data
    assert "status" in data


def test_health():
    response = client.get("/health")
    assert response.status_code == 200
    data = response.json()
    assert data["status"] == "healthy"
    assert data["model_loaded"] is True
    assert "model_version" in data
    assert data["model_type"] == "XGBoost + CalibratedClassifierCV"


def test_model_info():
    response = client.get("/model-info")
    assert response.status_code == 200
    data = response.json()
    assert "version" in data
    assert "tipo_modelo" in data
    assert "umbral_decision" in data
    assert "metricas" in data
    assert "top_3_features" in data


def test_predict_bajo_riesgo():
    payload = {
        "antiguedad": 36,
        "plan": "premium",
        "metodo_pago": "tarjeta_credito",
        "facturas_impagas": 0,
        "frecuencia_uso": 45,
        "tickets_soporte": 0,
        "tipo_contrato": "anual",
        "cambios_plan": 0,
        "canal_adquisicion": "web"
    }
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 200
    
    data = response.json()
    assert "prediccion" in data
    assert "probabilidad_churn" in data
    assert "umbral_decision" in data
    assert "top_features" in data
    assert "modelo_version" in data
    
    assert 0 <= data["probabilidad_churn"] <= 1
    assert len(data["top_features"]) == 3


def test_predict_alto_riesgo():
    payload = {
        "antiguedad": 3,
        "plan": "basico",
        "metodo_pago": "transferencia_bancaria",
        "facturas_impagas": 3,
        "frecuencia_uso": 5,
        "tickets_soporte": 8,
        "tipo_contrato": "mensual",
        "cambios_plan": 2,
        "canal_adquisicion": "web"
    }
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 200
    
    data = response.json()
    assert data["prediccion"] == "cancelara"
    assert data["probabilidad_churn"] > 0.5


def test_predict_tipo_incorrecto():
    payload = {
        "antiguedad": "texto_invalido",
        "plan": "basico",
        "metodo_pago": "tarjeta_credito",
        "facturas_impagas": 0,
        "frecuencia_uso": 25,
        "tickets_soporte": 1,
        "tipo_contrato": "mensual",
        "cambios_plan": 0,
        "canal_adquisicion": "web"
    }
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 422


def test_predict_plan_invalido():
    payload = {
        "antiguedad": 12,
        "plan": "ultra_premium",
        "metodo_pago": "tarjeta_credito",
        "facturas_impagas": 0,
        "frecuencia_uso": 25,
        "tickets_soporte": 1,
        "tipo_contrato": "mensual",
        "cambios_plan": 0,
        "canal_adquisicion": "web"
    }
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 422


def test_predict_campo_faltante():
    payload = {
        "antiguedad": 12,
        "plan": "basico",
        "facturas_impagas": 0,
        "frecuencia_uso": 25,
        "tickets_soporte": 1,
        "tipo_contrato": "mensual",
        "cambios_plan": 0,
        "canal_adquisicion": "web"
    }
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 422


def test_predict_response_structure():
    payload = {
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
    
    response = client.post("/predict", json=payload)
    assert response.status_code == 200
    
    data = response.json()
    
    assert isinstance(data["prediccion"], str)
    assert data["prediccion"] in ["cancelara", "no_cancelara"]
    
    assert isinstance(data["probabilidad_churn"], float)
    assert 0 <= data["probabilidad_churn"] <= 1
    
    assert isinstance(data["umbral_decision"], float)
    assert 0 <= data["umbral_decision"] <= 1
    
    assert isinstance(data["top_features"], list)
    assert len(data["top_features"]) == 3
    
    for feature in data["top_features"]:
        assert "feature" in feature
        assert "valor_cliente" in feature
        assert "impacto" in feature
        assert "importancia" in feature
    
    assert isinstance(data["modelo_version"], str)
    assert "xgboost" in data["modelo_version"].lower()