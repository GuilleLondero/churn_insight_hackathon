# ChurnInsight

> Plataforma predictiva de Machine Learning para detección temprana de churn en empresas de suscripción

[![Python](https://img.shields.io/badge/Python-3.12-blue.svg)](https://www.python.org/)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.104.1-teal.svg)](https://fastapi.tiangolo.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Netlify Status](https://api.netlify.com/api/v1/badges/f0f88f4d-974d-4e0e-b120-87e351c85b5d/deploy-status)](https://app.netlify.com/projects/churninsight/deploys)
[![License](https://img.shields.io/badge/License-Hackathon-yellow.svg)](LICENSE)

---

## 📋 Tabla de Contenidos

- [Sobre el Proyecto](#-sobre-el-proyecto)
- [El Problema](#-el-problema)
- [Nuestra Solución](#-nuestra-solución)
- [Arquitectura](#-arquitectura)
- [Tecnologías](#-tecnologías)
- [Demo en Vivo](#-demo-en-vivo)
- [Instalación Rápida](#-instalación-rápida)
- [Documentación por Componente](#-documentación-por-componente)
- [Equipo](#-equipo)
- [Resultados y Métricas](#-resultados-y-métricas)

---

## 🎯 Sobre el Proyecto

**ChurnInsight** es una plataforma integral, end-to-end, de predicción de churn desarrollada para el **Hackathon ONE – No Country 2025** por el equipo 43 (H12-25-L).

El sistema utiliza Machine Learning para predecir qué clientes tienen mayor probabilidad de cancelar su suscripción, así como las 3 principales variables que inciden en mayor medida en esa decisión, permitiendo a las empresas tomar acciones proactivas de retención con hasta **80% de efectividad** en la detección.

### ¿Qué hace ChurnInsight?

- 🔮 **Predice** la probabilidad de que un cliente cancele su servicio
- 🔍 **Identifica** los 3 factores más influyentes en cada predicción
- 📊 **Visualiza** métricas y estadísticas en tiempo real
- 📁 **Procesa** predicciones masivas mediante archivos CSV
- 🔐 **Gestiona** usuarios con autenticación segura (JWT)
- 📈 **Monitorea** el comportamiento del churn con dashboards administrativos

---

## 💼 El Problema

Las empresas con modelos de suscripción (telecom, fintech, streaming, SaaS) enfrentan un desafío crítico:

### Datos del Problema:
- 📉 **15-25%** de clientes se pierden anualmente por churn
- 💰 **Pérdidas millonarias** por detección tardía: 🔴 **$1.6 trillones**: Pérdidas anuales globales por churn (industria telecom)
- 📊 Adquirir un nuevo cliente cuesta **5-7 veces más** que retener uno existente
- ⏰ La detección manual llega **demasiado tarde**
- ⚠️ **68%**: Clientes que se van sin avisar (churn silencioso)
- ✅ **20-30%**: Tasa de éxito de retención cuando se detecta a tiempo
  
### Oportunidad de Mercado

ChurnInsight se dirige a más de **50,000 empresas** en LATAM con modelos de suscripción:
- Telecomunicaciones
- Banca digital y fintech
- Plataformas de streaming
- Software as a Service (SaaS)
- E-learning

### Impacto Real:
```
Empresa con 10,000 clientes
Tasa de churn: 20%
Pérdida anual: 2,000 clientes

Sin ChurnInsight:
→ Detecta 40% de los churns (800 clientes)
→ Pierde: 1,200 clientes

Con ChurnInsight (Recall 80%):
→ Detecta 80% de los churns (1,600 clientes)
→ Con estrategia de retención del 50%
→ Salva: 800 clientes adicionales
→ ROI: 2x-3x la inversión
```

---

## 💡 Nuestra Solución

### ChurnInsight ofrece:

#### 1. **Detección Temprana** (Recall 80%)
Identificamos 8 de cada 10 clientes que cancelarán **antes de que lo hagan**

#### 2. **Explicabilidad Total**
No solo predecimos, explicamos **por qué** cada cliente está en riesgo:
- Top 3 factores influyentes
- Importancia de cada variable
- Impacto positivo o negativo

#### 3. **Integración Simple**
API REST documentada con Swagger, lista para integrarse con sistemas existentes

#### 4. **Escalabilidad Real**
Arquitectura de microservicios desplegada en cloud (Render)

#### 5. **Costo Bajo**
Stack open-source, sin licencias costosas

---

## 🏗 Arquitectura

> **Nota:** El proyecto está compuesto por tres componentes independientes, desarrollados y versionados de forma separada.
> 
### Diagrama de Alto Nivel

```
┌─────────────────────────────────────────────────────────┐
│                     FRONTEND                            │
│              (HTML, CSS, JavaScript)                    │
│                                                         │
│  • Autenticación JWT                                   │
│  • Formularios de predicción                           │
│  • Dashboard con visualizaciones                       │
│  • Carga masiva (CSV)                                  │
└────────────────────┬────────────────────────────────────┘
                     │
                     │ HTTP/JSON + JWT
                     │
┌────────────────────▼────────────────────────────────────┐
│                    BACKEND                              │
│              (Java 17 + Spring Boot)                    │
│                                                         │
│  • Validación de datos                                 │
│  • Autenticación y autorización (JWT)                  │
│  • Gestión de usuarios y roles                         │
│  • Persistencia de predicciones (PostgreSQL)           │
│  • Logs y auditoría                                    │
│  • API REST documentada (Swagger)                      │
└────────────────────┬────────────────────────────────────┘
                     │
                     │ HTTP POST
                     │
┌────────────────────▼────────────────────────────────────┐
│              MICROSERVICIO ML                           │
│              (Python + FastAPI)                         │
│                                                         │
│  • Carga del modelo entrenado                          │
│  • Feature engineering automático                      │
│  • Predicción de churn (probabilidad)                  │
│  • Cálculo de feature importance                       │
│  • Health checks                                       │
└────────────────────┬────────────────────────────────────┘
                     │
                     │
┌────────────────────▼────────────────────────────────────┐
│           MODELO MACHINE LEARNING                       │
│         (XGBoost + Calibrated Classifier)               │
│                                                         │
│  • 19 features (9 originales + 5 engineerizadas)       │
│  • Recall: 80.09%                                      │
│  • ROC-AUC: 76.88%                                     │
│  • Umbral optimizado: 0.2190                           │
└─────────────────────────────────────────────────────────┘
```

### Flujo de Predicción

```
Usuario → Frontend → Backend → FastAPI → Modelo ML
   ↑                    ↓
   └────────── PostgreSQL (Logs)
```

---

## 🛠 Tecnologías

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.x** - Framework principal
- **Spring Security + JWT** - Autenticación y autorización
- **Spring Data JPA** - Persistencia
- **PostgreSQL** - Base de datos
- **Flyway** - Versionamiento base de datos
- **Maven** - Gestión de dependencias
- **Swagger/OpenAPI** - Documentación de API

### Data Science
- **Python 3.12** - Lenguaje de programación
- **FastAPI** - Framework web de alta performance
- **XGBoost** - Modelo de clasificación
- **Scikit-learn** - Pipeline ML y preprocesamiento
- **Pandas/NumPy** - Manipulación de datos
- **Pytest** - Testing automatizado

### Frontend
- **HTML5, CSS3, JavaScript** - Tecnologías core
- **Bootstrap 5** - Framework CSS
- **Chart.js** - Visualización de datos
- **JustGage** - Gráficos tipo Gauge
- **SweetAlert2** - Alertas elegantes

### DevOps
- **Docker** - Containerización
- **Docker Compose** - Orquestación de servicios
- **Render** - Plataforma de deployment
- **Netlify** - Hosting del frontend
- **GitHub Actions** - CI/CD (configurado)

---

## 🌐 Demo en Vivo

### Aplicaciones Desplegadas

| Componente | URL | Estado |
|------------|-----|--------|
| **Frontend** | [churninsight.netlify.app](https://churnisight.netlify.app) | 🟢 Activo |
| **Backend API** | [backend-churninsight-app-1.onrender](https://backend-churninsight-app-1.onrender.com/health) | 🟢 Activo |
| **ML API (FastAPI)** | [churn-api-v2-0.onrender.com](https://churn-api-v2-0.onrender.com/) | 🟢 Activo |

### Documentación Interactiva

- **Swagger Backend**: [/swagger-ui/index.html](https://backend-churninsight-app-1.onrender.com/swagger-ui/index.html)
- **Swagger FastAPI**: [churn-api-v2-0.onrender.com/docs](https://churn-api-v2-0.onrender.com/docs)

### Video Demo

📹 **[Ver Demo Completo en YouTube](https://www.youtube.com/watch?v=VHildc8i7Wo)**

---

## 🚀 Instalación Rápida

### Prerrequisitos

- Git instalado
- Docker y Docker Compose (para instalación con Docker)
- Java 17 y Maven (para backend manual)
- Python 3.12 y pip (para FastAPI manual)
- Navegador web moderno
- 
### Opción 1: Con Docker (Recomendado)

#### Backend
```bash
# 1. Clonar el repositorio
git clone https://github.com/GuilleLondero/churn_insight_hackathon.git
cd churn_insight_hackathon

# 2. Cambiar a la rama de backend
git checkout matias

# 3. Navegar a la carpeta con docker-compose
cd backend/churnInsight

# 4. Levantar los servicios
docker-compose up -d

# 5. Verificar que esté corriendo
docker-compose ps

# Acceder al Backend:
# - API: http://localhost:8080
# - Swagger: http://localhost:8080/swagger-ui/index.html
```

#### Datascience (FastApi)

```bash
# 1. Desde la raíz del proyecto
cd churn_insight_hackathon

# 2. Cambiar a la rama de data science
git checkout ds_guille

# 3. Navegar a la carpeta de FastAPI
cd churn-api

# 4. Construir la imagen Docker
docker build -t churninsight-ml .

# 5. Ejecutar el contenedor
docker run -d -p 8000:8000 --name churninsight-fastapi churninsight-ml

# Acceder a FastAPI:
# - API: http://localhost:8000
# - Swagger: http://localhost:8000/docs
# - Health: http://localhost:8000/health
```

#### Frontend
```bash
# 1. Clonar el repositorio del frontend
git clone https://github.com/lmbaezp/ChurnInsightFront.git
cd ChurnInsightFront

# 2. Asegurarse de estar en main
git checkout main

# 3. Configurar la URL del backend (archivo src/js/api.js)
# Cambiar API_URL si es necesario

# 4. Abrir con servidor local (elige uno):

# Opción A: Python
python -m http.server 3000

# Opción B: Node.js (si tienes http-server instalado)
npx http-server -p 3000

# Opción C: VS Code Live Server
# Click derecho en index.html → "Open with Live Server"

# Acceder:
# http://localhost:3000
# o http://localhost:5500 (Live Server)
```

### Opción 2: Instalación Manual

#### Backend

```bash
# 1. Clonar y navegar
git clone https://github.com/GuilleLondero/churn_insight_hackathon.git
cd churn_insight_hackathon
git checkout matias
cd backend/churnInsight

# 2. Configurar PostgreSQL
# Editar src/main/resources/application.yml con tus credenciales de BD

# 3. Ejecutar
./mvnw spring-boot:run

# O en Windows:
mvnw.cmd spring-boot:run

# Acceder:
# http://localhost:8080
# http://localhost:8080/swagger-ui/index.html
```

**Requisitos:** Java 17, Maven 3.6, PostgreSQL 15+ corriendo

#### Configuración de BD

```yml
# application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/churndb
    username: tu_usuario
    password: tu_password
```

#### Data Science (FastAPI)

```bash
# 1. Clonar y navegar
git clone https://github.com/GuilleLondero/churn_insight_hackathon.git
cd churn_insight_hackathon
git checkout ds_guille
cd churn-api

# 2. Crear entorno virtual (recomendado)
python -m venv venv

# Activar entorno virtual:
# Windows:
venv\Scripts\activate
# Linux/Mac:
source venv/bin/activate

# 3. Instalar dependencias
pip install -r requirements.txt

# 4. Ejecutar servidor
uvicorn app.main:app --reload

# Acceder:
# http://localhost:8000
# http://localhost:8000/docs
```

**Requisitos:** Python 3.12, pip

#### Frontend

```bash
# 1. Clonar
git clone https://github.com/lmbaezp/ChurnInsightFront.git
cd ChurnInsightFront

# 2. Configurar URL del backend
# Descomentar en src/js/api.js:
const API_URL = 'http://localhost:8080';

# 3. Servir archivos estáticos

# Opción A: Doble click en src/views/index.html
# (puede tener problemas de CORS)

# Opción B: Servidor Python (recomendado)
cd src
python -m http.server 3000

# Opción C: VS Code Live Server
# Instalar extensión "Live Server"
# Click derecho en index.html → "Open with Live Server"

# Acceder:
# http://localhost:3000
# o http://localhost:5500 (Live Server)
```

**Ver**: [Guías detalladas de instalación](#-documentación-por-componente)

---

## 📚 Documentación por Componente

Cada componente tiene su propia documentación detallada:

### 📘 [Backend - README](https://github.com/GuilleLondero/churn_insight_hackathon/blob/meiby/backend/churnInsight/README.md)
- Instalación y configuración
- Endpoints de API
- Autenticación JWT
- Base de datos y migraciones
- Testing

### 📗 [Data Science - README](https://github.com/GuilleLondero/churn_insight_hackathon/tree/ds_guille)
- Modelo de Machine Learning
- Endpoints del microservicio
- Feature engineering
- Métricas y evaluación
- Notebooks de desarrollo

### 📙 [Frontend - README](https://github.com/lmbaezp/ChurnInsightFront)
- Instalación y uso
- Funcionalidades
- Integración con Backend
- Diseño responsive
- Guía de estilos

---

## 👥 Equipo

### Backend Team (3 integrantes)

| Miembro | Rol | Responsabilidades | 
|---------|-----|-------------------|
| **Matias Solanes** | Backend Lead | Arquitectura, integración DS, Seguridad, autenticación | 
| **Luis Contreras** | Backend Dev | Endpoints, validaciones |
| **Meiby Burgos** | Backend Dev | Persistencia, logging |

### Data Science Team (3 integrantes)

| Miembro | Rol | Responsabilidades | GitHub |
|---------|-----|-------------------|--------|
| **Guillermo Londero** | DS Lead | Modelado ML, FastAPI |
| **Ezequiel Pérez** | ML Engineer | Evaluación, optimización, deploy |
| **Laura Báez** | Data Scientist | EDA, limpieza de datos |

---

## 📊 Resultados y Métricas

### Modelo de Machine Learning

| Métrica | Valor | Interpretación |
|---------|-------|---------------|
| **Accuracy** | 63.81% | Predicciones correctas totales |
| **Precision** | 39.50% | Eficiencia en detección positiva |
| **Recall** | **80.09%** ✅ | **8 de cada 10 churns detectados** |
| **F1-Score** | 52.91% | Balance Precision-Recall |
| **ROC-AUC** | 76.88% | Capacidad de discriminación |

### Estrategia de Negocio

✅ **Priorizamos Recall sobre Precision**

**¿Por qué?**
- En churn, perder un cliente cuesta **mucho más** que una falsa alarma
- Mejor detectar 8 de 10 clientes en riesgo (aunque haya falsos positivos)
- ROI positivo: Cada cliente salvado vale más que el costo de retención

### Top 3 Factores de Riesgo

1. **Facturas Impagas** (13.1% importancia)
2. **Contrato Mensual** (11.1% importancia)
3. **Cliente Problemático** (10.4% importancia)

---

## 🧪 Testing

### Backend
```bash
cd backend
./mvnw test
```

### Data Science
```bash
cd data_science/churn-api
pytest tests/ -v
```

**Cobertura:** >75% en ambos componentes

---

## 🚢 Deployment

El proyecto está desplegado en producción usando:

- **Render** (Backend + FastAPI)
- **Netlify** (Frontend)
- **PostgreSQL** (Base de datos en Render)

**Ver**: [Guías detalladas de instalación](#-documentación-por-componente)

---

## 📖 Documentación Adicional

- 📄 [Contrato de Integración Backend-DS](https://colab.research.google.com/drive/1je4ywQeHviSiTlVx1kxqpkHQeg1jgTDl)
- 📓 [Notebooks de Desarrollo Modelo ML](https://github.com/GuilleLondero/churn_insight_hackathon/tree/ds_guille/data_science/notebooks)
  - [Generación de datos](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Generacion_dataset_final.ipynb).
  - [EDA](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Eda_dataset_final.ipynb)
  - [Feature engineering](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/Feature_Enginnering_final.ipynb)
  - [One hot encoding y modelado](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/one_hot_encoding_%26_modelado.ipynb)
  - [Serialización del pipeline](https://github.com/GuilleLondero/churn_insight_hackathon/blob/ds_guille/data_science/notebooks/pipeline_serialization.ipynb)

---

## 🤝 Contribuir

Este proyecto fue desarrollado para el Hackathon ONE – No Country 2025.

Si deseas contribuir o reportar un bug:
1. Fork el repositorio
2. Crea una rama (`git checkout -b feature/amazing-feature`)
3. Commit tus cambios (`git commit -m 'Add amazing feature'`)
4. Push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

---

## 📞 Contacto

- **Team Lead General**: Guillermo Londero - guillelondero@gmail.com
- **Team Lead Backend**: Matias Solanes - matias.solanes14@gmail.com
- **Repositorio backend**: [GitHub - ChurnInsight BE](https://github.com/GuilleLondero/churn_insight_hackathon/tree/matias)
- **Repositorio datascience**: [GitHub - ChurnInsight DS](https://github.com/GuilleLondero/churn_insight_hackathon/tree/ds_guille)
- **Repositorio frontend**: [GitHub - ChurnInsight FE](https://github.com/lmbaezp/ChurnInsightFront)

---

## 📄 Licencia

Desarrollado como parte del **Hackathon ONE – No Country 2025** por el **Equipo 43 (H12-25-L)**.

---

## 🙏 Agradecimientos

- **No Country** por la organización del Hackathon ONE 2025
- Todos los mentores y coaches que nos apoyaron
- La comunidad open-source por las herramientas utilizadas

---

## 🏆 Demo Day

**Fecha**: 27 de Enero, 2026

---

<p align="center">
  <strong>Construyendo el futuro de la predicción de churn</strong>
  <br>
  Desarrollado con ❤️ por el Equipo 43
</p>
