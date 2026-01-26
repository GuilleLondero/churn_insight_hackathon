# 📦 Backend – ChurnInsight  
## Hackathon ONE – No Country 2025  
**Equipo 43 – H12-25-L**

---

## 📌 Descripción General

El backend de **ChurnInsight** fue desarrollado como un servicio robusto, seguro y escalable, encargado de orquestar la autenticación de usuarios, la integración con el modelo de Machine Learning y la persistencia de logs de predicción.

Está diseñado bajo una arquitectura **RESTful**, utilizando **Spring Boot**, autenticación **JWT**, control de acceso por roles, versionado de base de datos con **Flyway** y comunicación directa con un microservicio de **Data Science (FastAPI)**.

El backend actúa como el **núcleo del sistema**, conectando Frontend, modelo ML y base de datos, garantizando trazabilidad, seguridad y explicabilidad de las predicciones.

---

## 🏗️ Arquitectura del Backend

### Stack Tecnológico
- Java 17  
- Spring Boot  
- Spring Security + JWT  
- Spring Data JPA  
- PostgreSQL  
- Flyway (versionado de base de datos)  
- Swagger / OpenAPI  
- Docker (preparado para deploy)  
- Maven  
- OpenCSV  

### Arquitectura de Integración
```txt
Frontend (JWT)
      ↓
Backend Spring Boot
      ↓
FastAPI (Modelo ML)
      ↓
PostgreSQL (Logs + Usuarios)
🔐 Seguridad y Autenticación
Autenticación
Sistema de autenticación basado en JWT

Registro y login mediante endpoints públicos

Tokens con expiración de 10 horas

Encriptación de contraseñas con BCrypt

Autorización por Roles (RBAC)
Se implementó control de acceso basado en roles:

USER: puede realizar predicciones y consultar sus propios logs

ADMIN: acceso a estadísticas globales y gestión de logs

SUPER_ADMIN: gestión avanzada de roles

Los filtros JWT garantizan que solo usuarios autenticados y autorizados accedan a los endpoints protegidos.

🧠 Servicio de Predicción
El PredictionService cumple un rol central en el sistema:

Valida el request entrante según el contrato Backend–Data Science

Convierte los datos al formato esperado por FastAPI

Consume el endpoint /predict del microservicio ML

Parsea dinámicamente la respuesta (predicción, probabilidad, top features)

Persiste cada predicción en la base de datos

Registra tanto predicciones exitosas como errores

Persistencia de Logs
Cada predicción almacena:

Usuario autenticado

Timestamp

Predicción (Cancelará / No Cancelará)

Probabilidad de churn

Versión del modelo

Estado (OK / ERROR)

Mensaje de error (si aplica)

Esto permite auditoría, métricas y dashboards analíticos.

📊 Analítica y Logs
Se implementaron endpoints específicos para análisis:

Consulta de logs por usuario

Consulta de logs por rango de fechas

Estadísticas agregadas (promedios, máximos, mínimos)

Segmentación por nivel de riesgo (alto, medio, bajo)

El DTO DashLogsDTO consolida métricas clave para dashboards administrativos.

🗄️ Gestión de Base de Datos (Flyway)
Se integró Flyway para garantizar:

Versionado del esquema de base de datos

Migraciones controladas

Trazabilidad de cambios

Migraciones
Baseline inicial: permite trabajar con tablas existentes sin recrearlas

V2__agregar-rol-usuario.sql: incorporación del campo rol en usuarios sin pérdida de datos

Aislamiento de Tests
Los tests de integración se aislaron mediante:

Base de datos en memoria (H2)

Flyway desactivado en entorno de test

Hibernate create-drop solo para tests

Esto evitó conflictos entre migraciones reales y pruebas automatizadas.

⚠️ Manejo de Errores
Se implementó un GlobalExceptionHandler que unifica las respuestas de error:

400 Bad Request: errores de validación

403 Forbidden: acceso no autorizado

503 Service Unavailable: caída del servicio de Data Science

500 Internal Server Error: errores inesperados

Las respuestas son claras y consistentes para consumo por el Frontend.

📄 Documentación y Pruebas
Swagger / OpenAPI
Swagger UI habilitado

Autenticación JWT integrada (Bearer Token)

Ejemplos claros de uso

Endpoints protegidos correctamente documentados

Flujo de Prueba Recomendado
POST /auth/register

POST /auth/login

Autorizar token en Swagger

POST /api/v1/predict

Consultar logs según rol

Pruebas Realizadas
Registro y login exitosos

Predicciones individuales

Predicción batch (CSV)

Manejo de errores del servicio Data Science

Restricciones por rol

Migraciones Flyway sin pérdida de datos

Pruebas Manuales (Insomnia / Postman)
Durante el desarrollo y validación del backend, los endpoints fueron probados manualmente con Insomnia y Postman, incluyendo:

Registro de usuarios (/auth/register)

Autenticación y generación de JWT (/auth/login)

Acceso a endpoints protegidos con token Bearer

Predicciones individuales (/api/v1/predict)

Predicciones batch mediante carga de archivos CSV

Validación de control de acceso por roles (USER / ADMIN / SUPER_ADMIN)

Manejo de errores esperados:

400 Bad Request por validaciones

401 / 403 por falta de autorización

503 Service Unavailable cuando el servicio de Data Science no está disponible

Estas pruebas validaron la integración completa entre Backend, Data Science y Frontend.

🚀 Estado del Backend
Backend desplegado y operativo

Integración estable con FastAPI (local y cloud)

Persistencia y trazabilidad completas

Seguridad por roles funcionando

Listo para Demo Day y evaluación final

📌 Conclusión
El backend de ChurnInsight evolucionó desde un MVP funcional hacia un sistema sólido, seguro y preparado para producción, capaz de soportar predicciones en tiempo real, análisis histórico, control de acceso avanzado y versionado de base de datos.

Representa un componente clave del valor del proyecto, demostrando buenas prácticas de ingeniería, integración real con Machine Learning y foco en escalabilidad y mantenibilidad.



