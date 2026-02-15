# 🚌 Sistema de Gestión de Transporte Público (PoC Cloud Native)

![Java](https://img.shields.io/badge/Java-21-orange.svg?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg?style=flat-square&logo=springboot)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Message_Broker-ff6600.svg?style=flat-square&logo=rabbitmq)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?style=flat-square&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg?style=flat-square&logo=docker)

Este proyecto es una Prueba de Concepto (PoC) para el ramo de **Desarrollo Cloud Native**. Consiste en una arquitectura orientada a eventos, diseñada bajo los principios de la **Arquitectura Hexagonal**, permitiendo un alto desacoplamiento entre la lógica de negocio y la infraestructura.

El sistema simula la ingesta y procesamiento asíncrono de datos de una flota de transporte público, dividiéndose en dos flujos principales.

---

## 🏛️ Arquitectura del Sistema

El ecosistema está compuesto por 4 microservicios independientes que se comunican a través de un clúster de RabbitMQ:

| Microservicio | Puerto | Misión | Destino de Datos |
| :--- | :---: | :--- | :--- |
| 🟢 **Productor 1** | `8081` | Recibe coordenadas GPS de los vehículos. | `ubicaciones.queue` |
| 🟢 **Productor 2** | `8083` | Recibe estados y desvíos de rutas. | `horarios.queue` |
| 🔵 **Consumidor 1** | `8082` | Procesa ubicaciones y las persiste. | Base de datos **MySQL** |
| 🔵 **Consumidor 2** | `8084` | Procesa horarios y genera reportes físicos. | **File System** (.json) |

> ⚙️ **Infraestructura Base:** RabbitMQ (`15672`, `5672`) y MySQL (`3306`) corren sobre contenedores Docker.

---

## 🛠️ Requisitos Previos

Asegúrate de tener instalado lo siguiente en tu entorno local:
* **Java 21** (JDK)
* **Maven**
* **Docker** y **Docker Compose**
* **Postman** (Para simular la emisión de eventos)

---

## 🚀 Guía de Ejecución Local

### 1. Levantar la Infraestructura (Docker)
Abre una terminal en la raíz del proyecto y ejecuta:

```bash
docker-compose up -d
```

Verifica que el panel de RabbitMQ esté accesible en http://localhost:15672 (Credenciales: guest / guest).

2. Iniciar los Microservicios
⚠️ IMPORTANTE: Debes levantar siempre primero los Productores. Ellos contienen la configuración que declara las colas físicas y los exchanges en RabbitMQ. Si levantas un consumidor primero, fallará al no encontrar su cola.

Ejecuta las aplicaciones Spring Boot en el siguiente orden:

ProducerQueue1Application

ProducerQueue2Application

ConsumerQueue1Application

ConsumerQueue2Application

## 🧪 Pruebas Automáticas con Postman Runner
Para simular una carga de datos realista y visualizar los picos de tráfico en los gráficos de RabbitMQ, utilizaremos la automatización de Postman.

Paso 1: Crear los archivos de datos (Data Driven)
Crea estos dos archivos .json en tu computador:

<details>
<summary>📂 Ver contenido de <b>data_ubicaciones.json</b></summary>

JSON
[
  {"idVehiculo": "FL-00-11", "lat": -33.518, "lon": -70.598},
  {"idVehiculo": "ST-11-20", "lat": -33.440, "lon": -70.650},
  {"idVehiculo": "MQ-99-01", "lat": -33.500, "lon": -70.580}
]
</details>

<details>
<summary>📂 Ver contenido de <b>data_horarios.json</b></summary>

JSON
[
  {"ruta": "104-BUS", "estado": "NORMAL", "desc": "Operando en tiempo."},
  {"ruta": "210-BUS", "estado": "ATRASO", "desc": "Congestión alta."},
  {"ruta": "L4-METRO", "estado": "NORMAL", "desc": "Frecuencia de 3 min."}
]
</details>

Paso 2: Configurar variables en Postman
Crea las peticiones POST y configura el body (raw -> JSON) apuntando a las variables de los archivos:

A. Endpoint Ubicaciones: http://localhost:8081/api/v1/locations

JSON
{
  "vehicleId": "{{idVehiculo}}",
  "latitude": {{lat}},
  "longitude": {{lon}}
}
B. Endpoint Horarios: http://localhost:8083/api/v1/schedules

JSON
{
  "routeId": "{{ruta}}",
  "status": "{{estado}}",
  "updatedTime": "2026-02-15T20:00:00",
  "description": "{{desc}}"
}
Paso 3: Disparar la ráfaga
Selecciona la carpeta de tu Colección en Postman y haz clic en Run.

Selecciona la petición que deseas estresar.

En la sección Data, carga tu archivo .json correspondiente.

Ajusta el Delay a 100ms para visualizar correctamente el flujo de consumo.

Haz clic en Run.

✅ Verificación de Resultados
MySQL: Conéctate a localhost:3306 (devuser / devpassword) y ejecuta SELECT * FROM locations;.

Archivos Físicos: Revisa la carpeta autogenerada schedules_output/ en la raíz del MS Consumidor 2.
