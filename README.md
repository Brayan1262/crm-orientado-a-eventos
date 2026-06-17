# Salesflow CRM API 🚀

> **Staff Engineer Level Architecture Showcase**

Salesflow CRM API no es un simple CRUD. Es una plataforma de gestión comercial diseñada bajo estrictos estándares de **Arquitectura Orientada a Eventos (EDA)**, **Observabilidad** y **Resiliencia**, pensada para escalar masivamente en entornos de producción.

## 🏗️ Arquitectura del Sistema

El sistema ha sido evolucionado desde un monolito tradicional hacia un ecosistema distribuido y reactivo, implementando los siguientes patrones avanzados:

### 1. Data Pipeline & CDC (Change Data Capture)
Hemos desacoplado la base de datos de la lógica de negocio secundaria. En lugar de escribir eventos manualmente, utilizamos **Debezium** acoplado a **PostgreSQL** (`wal_level=logical`) para capturar cada inserción o modificación a nivel de transacción (Redo Logs). 
* Estos eventos son transmitidos en tiempo real (milisegundos) hacia un clúster de **Apache Kafka**.
* Microservicios en Java (Spring Boot Kafka Listeners) consumen estos tópicos para ejecutar procesos asíncronos pesados (envío de correos, indexación de búsquedas, etc.) sin impactar el tiempo de respuesta del usuario.

### 2. Observabilidad (IDP Ready)
La aplicación expone métricas de nivel interno mediante **Spring Boot Actuator** y **Micrometer**, las cuales son recolectadas por **Prometheus** y visualizadas en tiempo real a través de Dashboards personalizados en **Grafana**. 
* Esto permite monitorear JVM Heap, conexiones a base de datos (HikariCP), latencias de endpoints y el flujo de Kafka con total transparencia.

### 3. Chaos Engineering & Self-Healing
La plataforma está contenerizada con **Docker** y lista para despliegues orquestados en **Kubernetes**. 
Para garantizar la máxima fiabilidad, utilizamos **Chaos Mesh** inyectando fallos controlados (ej. `NetworkChaos` para latencias de 5 segundos, o `PodChaos` para asesinato de la BD). 
* La arquitectura sobrevive a caídas de la base de datos recuperándose en menos de 2 segundos gracias a los liveness probes de Kubernetes y el reintento de conexión de la API (Zero-Downtime auto-healing).

## 🛠️ Stack Tecnológico

* **Core:** Java 21, Spring Boot 3, Spring Data JPA, Spring Kafka.
* **Database:** PostgreSQL 15 (Logical Decoding activo).
* **Event Streaming:** Apache Kafka, Zookeeper, Kafka Connect (Debezium Plugin).
* **Observability:** Prometheus, Grafana.
* **Infra / SRE:** Docker, Kubernetes (`kind`), Chaos Mesh.

## 🚀 Cómo desplegar localmente

### Entorno Completo (Docker Compose)
Levanta Kafka, Postgres, Debezium, Prometheus y Grafana:
```bash
docker-compose up -d
```
Luego, ejecuta la API de Spring Boot:
```bash
./mvnw spring-boot:run
```
*(Grafana disponible en `http://localhost:3000`)*

### Pruebas de Resiliencia (Kubernetes & Chaos)
1. Construir imagen: `docker build -t salesflow-api:latest .`
2. Crear clúster: `kind create cluster --name salesflow`
3. Cargar imagen: `kind load docker-image salesflow-api:latest --name salesflow`
4. Aplicar manifiestos: `kubectl apply -f k8s/`
5. Ejecutar caos: `kubectl apply -f chaos/pod-kill-postgres.yaml`

---
*Desarrollado con mentalidad SRE.*