# ChurnInsight API - XGBoost Calibrado

API REST para prediccion de churn de clientes usando XGBoost con calibracion de probabilidades.

## Modelo

- Algoritmo: XGBoost + CalibratedClassifierCV (Platt Scaling)
- Version: v2.0_xgboost_calibrado
- Umbral optimizado: ~0.2190
- Recall: ~80%
- F1-Score: ~52-53%

## Estructura del Proyecto
```
churn-api/
├── app/
│   ├── __init__.py
│   ├── main.py
│   ├── schemas.py
│   └── utils.py
├── models/
│   └── churn_xgboost_calibrado.pkl
├── tests/
│   ├── __init__.py
│   └── test_api.py
├── venv/
├── Dockerfile
├── .dockerignore
├── requirements.txt
└── README.md
```

## Instalacion Local

### Requisitos

- Python 3.12
- pip

### Pasos

1. Crear entorno virtual:
```bash
python -m venv venv
```

2. Activar entorno virtual:
```bash
# Windows
venv\Scripts\activate

# Linux/Mac
source venv/bin/activate
```

3. Instalar dependencias:
```bash
pip install -r requirements.txt
```

4. Ejecutar API:
```bash
uvicorn app.main:app --reload
```

5. Acceder a documentacion:
```
http://localhost:8000/docs
```

## Uso con Docker

### Construir imagen
```bash
docker build -t churn-api:v2.0 .
```

### Ejecutar contenedor
```bash
docker run -d -p 8000:8000 --name churn-api churn-api:v2.0
```

### Ver logs
```bash
docker logs -f churn-api
```

### Detener y eliminar
```bash
docker stop churn-api
docker rm churn-api
```

## Endpoints

### GET /

Status de la API

### GET /health

Health check del servicio

### GET /model-info

Informacion del modelo (metricas, version, top features)

### POST /predict

Prediccion de churn

Request:
```json
{
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
```

Response:
```json
{
  "prediccion": "cancelara",
  "probabilidad_churn": 0.7234,
  "umbral_decision": 0.2190,
  "top_features": [
    {
      "feature": "tickets_soporte",
      "valor_cliente": "3",
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
      "valor_cliente": "1",
      "impacto": "alto_riesgo",
      "importancia": 0.0854
    }
  ],
  "modelo_version": "v2.0_xgboost_calibrado"
}
```

## Tests

Ejecutar tests:
```bash
python -m pytest tests/test_api.py -v
```

## Integracion con Backend Java

El Backend Java debe hacer un POST HTTP al endpoint /predict:
```java
RestTemplate restTemplate = new RestTemplate();
String url = "http://churn-api:8000/predict";

ChurnRequest request = new ChurnRequest(...);
ChurnResponse response = restTemplate.postForEntity(url, request, ChurnResponse.class);
```

## Notas Tecnicas

- El modelo se carga UNA VEZ al iniciar la aplicacion
- No hay I/O en cada prediccion (todo en memoria)
- Latencia esperada: <100ms por request
- El umbral de decision esta optimizado para Recall ~80%
- Trade-off: Mayor deteccion de churn a cambio de mas falsos positivos

## Proyecto

- Hackathon: NoCountry H12-25-L
- Equipo: 43
- Duracion: 15/12/2025 - 25/01/2026
```