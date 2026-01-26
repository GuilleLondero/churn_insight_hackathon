# ChurnInsight - Data Science

> Microservicio de Machine Learning para predicción de churn de clientes

[![Python](https://img.shields.io/badge/Python-3.12-blue.svg)](https://www.python.org/)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.104.1-green.svg)](https://fastapi.tiangolo.com/)
[![XGBoost](https://img.shields.io/badge/XGBoost-Calibrado-orange.svg)](https://xgboost.readthedocs.io/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

## TABLA DE CONTENIDOS

- [Descripción](#-descripción)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Modelo de Machine Learning](#-modelo-de-machine-learning)
- [Proceso de desarrollo](#-proceso-de-desarrollo)
- [Testing](#-testing)
- [Deploy](#-deploy)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Contribuidores](#-contribuidores)

---

## Descripción

ChurnInsight Data Science es el componente de Machine Learning del proyecto ChurnInsight, diseñado para predecir la probabilidad de cancelación (churn) de clientes en empresas de suscripción (telecom, fintech, streaming, SaaS).

El microservicio utiliza un modelo **XGBoost calibrado** que alcanza un **Recall del 80%**, priorizando la detección de clientes en riesgo sobre la reducción de falsas alarmas.

### *¿Qué problema resuelve?*

Las empresas con modelos de suscripción (telecom, fintech, streaming, SaaS) pierden entre 15-25% de sus clientes anualmente debido a cancelaciones (churn). La detección tardía de clientes en riesgo genera pérdidas millonarias y limita las estrategias de retención. Además, en comparación al costo de adquirir nuevos clientes, la retención resulta una estrategia de menor costo, y usualmente más eficaz.

En la revisión de la literatura, el fenómeno del churn no es un tema nuevo de estudio, y se ha usado el machine learning como herramienta eficiente para investigar el comportamiento del cliente y tomar medidas proactivas en base a datos reales. Los modelos de churn tienen dos objetivos primarios: predecir si el comportamiento de un cliente lo llevará a cancelar el servicio, y que factores de su comportamiento influirán más en esa decisión, lo cual, permite detectar en etapas tempranas la acción y contrarrestarla. 


### *¿Por qué es útil?*

- **Detección temprana**: Identifica 8 de cada 10 clientes que cancelarán
- **Explicabilidad**: Muestra las 3 variables más influyentes en cada predicción
- **Acción proactiva**: Permite estrategias de retención personalizadas
- **ROI comprobado**: Por cada $1 invertido en retención → $2+ de retorno

---

## Características

- ✅ Predicción en tiempo real de probabilidad de churn
- ✅ Explicabilidad del modelo (Top 3 features más influyentes)
- ✅ API REST documentada con Swagger/OpenAPI
- ✅ Modelo calibrado con Platt Scaling para probabilidades confiables
- ✅ Feature engineering automático integrado en el pipeline
- ✅ Validación de datos con Pydantic
- ✅ Tests automatizados (9/9 pasando)
- ✅ Dockerizado y listo para deploy en cloud
- ✅ Health checks para monitoreo en producción

---

## Tecnologías

### Core
- **Python**: 3.12
- **FastAPI**: Framework web de alta performance
- **Uvicorn**: Servidor ASGI

### Machine Learning
- **XGBoost**: Modelo de clasificación
- **Scikit-learn**: Pipeline y preprocesamiento
- **Pandas/NumPy**: Manipulación de datos
- **CalibratedClassifierCV**: Calibración de probabilidades (Platt Scaling)

### Testing y Validación
- **Pytest**: Tests automatizados
- **Pydantic**: Validación de datos de entrada

### Deploy
- **Docker**: Containerización
- **Render**: Plataforma de deploy en cloud

---

## Instalación

### Prerrequisitos

- Python 3.12 o superior
- pip (gestor de paquetes de Python)
- Git

### Opción 1: Instalación Local

```bash
# 1. Clonar el repositorio
git clone https://github.com/GuilleLondero/churn_insight_hackathon/tree/ds_guille/churn_api
cd app

# 2. Crear entorno virtual (recomendado)
python -m venv venv

# Activar entorno virtual
# En Windows:
venv\Scripts\activate
# En Linux/Mac:
source venv/bin/activate

# 3. Instalar dependencias
pip install -r requirements.txt
```

### Opción 2: Docker

```bash
# 1. Construir la imagen
docker build -t churninsight-ml .

# 2. Ejecutar el contenedor
docker run -p 8000:8000 churninsight-ml
```

---

## Uso

### Iniciar el servidor localmente

```bash
uvicorn app.main:app --reload
```

El servidor estará disponible en: `http://localhost:8000`

### Documentación interactiva

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc

### Realizar una predicción

#### Desde PowerShell

```powershell
$body = @{
    antiguedad = 36
    plan = "premium"
    metodo_pago = "tarjeta_credito"
    facturas_impagas = 0
    frecuencia_uso = 45
    tickets_soporte = 0
    tipo_contrato = "anual"
    cambios_plan = 0
    canal_adquisicion = "web"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8000/predict" `
    -Method Post `
    -ContentType "application/json; charset=utf-8" `
    -Body $body

$response
```

#### Desde curl (Linux/Mac)

```bash
curl -X POST "http://localhost:8000/predict" \
  -H "Content-Type: application/json" \
  -d '{
    "antiguedad": 36,
    "plan": "premium",
    "metodo_pago": "tarjeta_credito",
    "facturas_impagas": 0,
    "frecuencia_uso": 45,
    "tickets_soporte": 0,
    "tipo_contrato": "anual",
    "cambios_plan": 0,
    "canal_adquisicion": "web"
  }'
```

#### Desde Python

```python
import requests

response = requests.post(
    "http://localhost:8000/predict",
    json={
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
)

print(response.json())
```

### Respuesta esperada

```json
{
  "prediccion": "no_cancelara",
  "probabilidad_churn": 0.23,
  "umbral_decision": 0.2190,
  "top_features": [
    {
      "feature": "antiguedad",
      "valor_cliente": 36,
      "impacto": "bajo_riesgo",
      "importancia": 0.145
    },
    {
      "feature": "tipo_contrato_mensual",
      "valor_cliente": 0,
      "impacto": "bajo_riesgo",
      "importancia": 0.133
    },
    {
      "feature": "facturas_impagas",
      "valor_cliente": 0,
      "impacto": "bajo_riesgo",
      "importancia": 0.131
    }
  ],
  "modelo_version": "v2.0_xgboost_calibrado"
}
```

---

## API Endpoints

### 1. Status del servicio

```http
GET /
```

**Respuesta:**
```json
{
  "message": "ChurnInsight ML API",
  "version": "2.0",
  "status": "running"
}
```

### 2. Health Check

```http
GET /health
```

Verifica que el modelo esté cargado y el servicio operativo.

**Respuesta:**
```json
{
  "status": "healthy",
  "model_loaded": true,
  "model_version": "v2.0_xgboost_calibrado",
  "model_type": "XGBoost + CalibratedClassifierCV"
}
```

### 3. Información del Modelo

```http
GET /model-info
```

Devuelve métricas del modelo y top features.

**Respuesta:**
```json
{
  "version": "v2.0_xgboost_calibrado",
  "tipo_modelo": "XGBoost + CalibratedClassifierCV",
  "umbral_decision": 0.2190,
  "metricas": {
    "accuracy": 0.6381,
    "precision": 0.3950,
    "recall": 0.8009,
    "f1_score": 0.5291,
    "roc_auc": 0.7688
  },
  "top_3_features": [
    "facturas_impagas",
    "tipo_contrato_mensual",
    "cliente_problematico"
  ]
}
```

### 4. Predicción de Churn

```http
POST /predict
```

**Request Body:**

| Campo | Tipo | Valores Permitidos | Descripción |
|-------|------|-------------------|-------------|
| `antiguedad` | int | 0-120 | Meses como cliente |
| `plan` | str | "basico", "estandar", "premium" | Plan contratado |
| `metodo_pago` | str | "tarjeta_credito", "tarjeta_debito", "transferencia_bancaria" | Método de pago |
| `facturas_impagas` | int | 0-10 | Número de facturas sin pagar |
| `frecuencia_uso` | int | 0-100 | Sesiones mensuales |
| `tickets_soporte` | int | 0-20 | Tickets de soporte abiertos |
| `tipo_contrato` | str | "mensual", "anual" | Tipo de contrato |
| `cambios_plan` | int | 0-5 | Veces que cambió de plan |
| `canal_adquisicion` | str | "web", "referido", "redes_sociales", "call_center' | Cómo llegó el cliente |

**Respuesta:**

```json
{
  "prediccion": "cancelara" | "no_cancelara",
  "probabilidad_churn": 0.0-1.0,
  "umbral_decision": 0.2190,
  "top_features": [
    {
      "feature": "nombre_variable",
      "valor_cliente": valor,
      "impacto": "alto_riesgo" | "medio_riesgo" | "bajo_riesgo",
      "importancia": 0.0-1.0
    }
  ],
  "modelo_version": "v2.0_xgboost_calibrado"
}
```

**Códigos de respuesta:**

- `200 OK`: Predicción exitosa
- `422 Unprocessable Entity`: Datos de entrada inválidos
- `500 Internal Server Error`: Error del servidor

---

## Modelo de Machine Learning

### Arquitectura

```
Input (9 features)
    ↓
Feature Engineering (5 features adicionales)
    ↓
One-Hot Encoding (8 variables dummy)
    ↓
XGBoost Classifier
    ↓
CalibratedClassifierCV (Platt Scaling)
    ↓
Predicción + Probabilidad Calibrada
```

## Proceso de desarrollo

Este modelo fue desarrollado siguiendo un proceso riguroso de Data Science:

1. **Generación de Dataset**: 5,000 registros sintéticos con lógica de negocio realista
2. **Limpieza de Datos**: Tratamiento de nulos (10%), outliers (5%) y ruido (2%)
3. **Feature Engineering**: Creación de 5 features validadas (VIF < 5.4)
4. **Modelado**: Evaluación de 3 algoritmos (LR, RF, XGBoost)
5. **Calibración**: Platt Scaling para probabilidades confiables
6. **Optimización**: Umbral ajustado para Recall 80%

**Para detalles técnicos completos**, consulta los siguientes notebooks:
- Ver [**Contrato de integración DS-Backend**](https://colab.research.google.com/drive/1je4ywQeHviSiTlVx1kxqpkHQeg1jgTDl)
- Ver [**Generación de datos**](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Generacion_dataset_final.ipynb).
- Ver [**EDA**](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Eda_dataset_final.ipynb)
- Ver [**Feature engineering**](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Feature_Enginnering_final.ipynb)
- Ver [**One hot encoding y modelado**](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/one_hot_encoding_%26_modelado.ipynb)
- Ver [**Serialización del pipeline**](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/pipeline_serialization.ipynb)

**Hallazgo destacado**: Durante la evaluación, se detectó una casualidad estadística única con SMOTE (probabilidad < 0.01%)

### Features Utilizadas

#### Features Originales (9)
1. `antiguedad`: Meses como cliente
2. `plan`: Tipo de plan contratado
3. `metodo_pago`: Método de pago
4. `facturas_impagas`: Facturas sin pagar
5. `frecuencia_uso`: Uso mensual del servicio
6. `tickets_soporte`: Tickets de soporte
7. `tipo_contrato`: Mensual o anual
8. `cambios_plan`: Cambios de plan en los últimos 6 meses
9. `canal_adquisicion`: Canal de llegada

#### Features Engineerizadas (5)
1. **friccion_del_servicio**: `tickets_soporte / (frecuencia_uso + 1)` - Ratio de problemas vs engagement
2. **ratio_valor_uso**: `valor_plan / (frecuencia_uso + 1)` - ¿Paga mucho y usa poco?
3. **cliente_problematico**: Cliente con muchos tickets y poco uso
4. **early_churn_risk**: Cliente nuevo con facturas impagas
5. **premium_mensual**: Cliente premium sin compromiso anual

**Total**: 19 features finales sin multicolinealidad (VIF < 5.4)

### Métricas del Modelo

| Métrica | Valor | Interpretación |
|---------|-------|---------------|
| **Accuracy** | 63.81% | Predicciones correctas totales |
| **Precision** | 39.50% | De cada 100 predicciones de churn, 40 son correctas |
| **Recall** | 80.09% | De cada 100 clientes que cancelan, detectamos 80 ✅ |
| **F1-Score** | 52.91% | Balance entre Precision y Recall |
| **ROC-AUC** | 76.88% | Capacidad de discriminación (>75% es bueno) |

### Estrategia del Modelo

**Priorización de Recall sobre Precision**

- **Umbral optimizado**: 0.2190 (en lugar del estándar 0.5)
- **Justificación de negocio**: En churn, perder un cliente cuesta más que una falsa alarma
- **Trade-off**: +11% Recall vs modelo anterior, a cambio de -5% Precision

### Top 3 Features Más Importantes

1. **facturas_impagas** (13.1%): Principal señal de riesgo
2. **tipo_contrato_mensual** (11.1%): Contratos mensuales = 3x más riesgo
3. **cliente_problematico** (10.4%): Muchos tickets + poco uso = alta probabilidad de churn

### Dataset

- **Total de registros**: 4,251
- **Distribución de clases**: 74% No Churn / 26% Churn
- **Split**: 80% Train / 20% Test
- **Estrategia de balanceo**: `class_weight='balanced'` (sin SMOTE)

### Justificación de la elección de XGBOOST

Durante el desarrollo se evaluaron 3 modelos:
  
  | Modelo | Precisión | Recall | F1-Score | ROC-AUC |
  |--------|-----------|--------|----------|---------|
  | **Logistic Regression** | 43.48% | 69.44% | 53.48% | 77.87% |
  | **Random Forest** | 41.89% | 61.57% | 50.32% | 76.45% |
  | **XGBoost calibrado** | 39.50% | **80.09%** | 52.91% | 76.88% |

Se eligió **XGBoost calibrado** porque:

- **Recall superior:**  Es prioridad de negocio maximizar la detección de pérdidas.
- **Mejor manejo de relaciones no lineales:** Aprende interacciones complejas entre features automáticamente. Ejemplo: `facturas_impagas × tipo_contrato × antiguedad`. Captura patrones que Logistic Regression no puede (solo aprende relaciones lineales).
- **Robustez ante datos desbalanceados:** Parámetro scale_pos_weight ajusta automáticamente el peso de la clase minoritaria. Además, tiene mejor performance en datasets desbalanceados (documentado en papers académicos).
- **Calibración de probabilidades:** Usando `CalibratedClassifierCV` se ajustan las probabilidades usando *Platt Scaling*, mientras que la regresión logística produce probabilidades bien calibradas por naturaleza, pero su Recall es inferior.
- **Rendimiento en producción:** Optimizado para inferencia rápida. Tiempo de predicción: ~50ms por cliente. Bajo consumo de memoria (1.2 MB serializado).
  

---

## Testing

### Ejecutar tests automáticos

```bash
# Instalar pytest si no lo tienes
pip install pytest

# Correr todos los tests
pytest tests/test_api.py -v

# Correr con más detalles
pytest tests/test_api.py -v -s

# Correr un test específico
pytest tests/test_api.py::test_predict_bajo_riesgo -v
```

### Cobertura de tests

- ✅ Test de endpoints (`/`, `/health`, `/model-info`, `/predict`)
- ✅ Test de validaciones (tipos de datos, rangos, valores permitidos)
- ✅ Test de casos de error (campos faltantes, valores inválidos)
- ✅ Test de estructura de respuesta
- ✅ Test de escenarios de negocio (bajo, medio, alto riesgo)

**Resultado**: 9/9 tests pasando (100%)

### Casos de prueba

#### Cliente de Bajo Riesgo
```python
{
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
# Resultado esperado: no_cancelara (probabilidad < 30%)
```

#### Cliente de Alto Riesgo
```python
{
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
# Resultado esperado: cancelara (probabilidad > 80%)
```

---

## Deploy

### Deploy en Render (Cloud)

El microservicio está desplegado y disponible en:

**URL**: https://churn-api-v2-0.onrender.com/

**Endpoints en producción**:
- GET https://churn-api-v2-0.onrender.com/
- GET https://churn-api-v2-0.onrender.com/health
- GET https://churn-api-v2-0.onrender.com/model-info
- POST https://churn-api-v2-0.onrender.com/predict

### Deploy con Docker

```bash
# 1. Construir imagen
docker build -t churninsight-ml .

# 2. Ejecutar contenedor
docker run -d -p 8000:8000 --name churninsight churninsight-ml

# 3. Verificar logs
docker logs churninsight

# 4. Detener contenedor
docker stop churninsight
```

### Variables de entorno

No se requieren variables de entorno para este microservicio (el modelo está embebido en la imagen Docker).

---

## ESTRUCTURA DEL PROYECTO

> **Nota:** el código de Data Science se encuentra en la rama `ds_guille`

```
churn-insight-hackaton/
├── backend/
│
├── churn-api/                   # Aquí se encuentra todo el código para el despliegue en FastApi
│   ├── app/
│   │   ├── __init__.py          # Convierte app/ en módulo Python
│   │   ├── main.py              # Servidor FastAPI + endpoints
│   │   ├── schemas.py           # Modelos Pydantic de validación
│   │   └── utils.py             # Cálculo de feature importance
│   └── models/
│       ├── churn_xgboost_calibrado.pkl  # Modelo serializado (1.2 MB)
│   └── tests/
│       ├── __init__.py
│       └── test_api.py          # Tests automatizados (9 tests)
│
├── .dockerignore
├── .gitignore
├── Dockerfile
├── README.md
├── requeriments.txt
│
├── datascience
│   ├── datasets/
│   │   ├── dataset_feature_enginnering_final.csv
│   │   ├── dataset_limpio_final.csv
│   │   └── dataset_sucio_final.csv
│   └── models/
│       └── churn_xgboost_calibrado.pkl  # Modelo serializado (1.2 MB)
│
├── notebooks/                   # Notebooks de desarrollo (no en producción)
│   ├── CONTRATO_INTEGRACIÓN_DS_BACKEND.ipynb
│   ├── Eda_dataset_final.ipynb
│   ├── Feature_Enginnering_final.ipynb
│   ├── Generacion_dataset_final.ipynb
│   ├── one_hot_encoding_&_modelado.ipynb
│   └── pipeline_serialization.ipynb
```
---

## Contribuidores

### Data Science Team

| Nombre | Rol | Responsabilidades |
|--------|-----|-------------------|
| **Guillermo Londero** | DS Lead | Modelado ML, Microservicio FastAPI |
| **Ezequiel Pérez** | ML Engineer | Evaluación de modelos, optimización, deployment |
| **Laura Báez** | Data Scientist | EDA, limpieza de datos |


---

## Contacto y Soporte

- **Team Lead**: Guillermo Londero - guillelondero@gmail.com
- **Repositorio**: [GitHub - ChurnInsight](https://github.com/GuilleLondero/churn_insight_hackathon)
- **Swagger Backend**: [API Documentation](https://backend-churninsight-app-1.onrender.com/swagger-ui/index.html)
- **Swagger FastAPI**: [ML API Documentation](https://churn-api-v2-0.onrender.com/docs)
- **Video Demo**: [YouTube](https://www.youtube.com/watch?v=VHildc8i7Wo)

---

## Licencia

Este proyecto fue desarrollado como parte del **Hackathon ONE – No Country 2025** por el **Equipo 43 (H12-25-L)**.

---

## Agradecimientos

Desarrollado con ❤️ por el equipo ChurnInsight para **No Country - Hackathon ONE 2025**.

**Demo Day**: 27/01/2026

---

> **Nota**: Este microservicio es parte del proyecto ChurnInsight, que incluye también componentes de Backend (Spring Boot) y Frontend. Para la documentación completa del sistema, consulta el [README principal](https://github.com/GuilleLondero/churn_insight_hackathon).

