# Sistema de Gestión de Transporte Público (Cloud Native - Local Environment)

Este proyecto es una prueba de concepto (PoC) de una arquitectura orientada a eventos para un sistema de transporte público, desarrollado bajo los principios de Arquitectura Hexagonal. Consta de 4 microservicios en Spring Boot que se comunican de forma asíncrona mediante RabbitMQ y persisten datos en MySQL y el sistema de archivos local.

## 🛠️ Requisitos Previos
* **Java 21** (o superior)
* **Maven**
* **Docker y Docker Compose**
* **IDE recomendado:** IntelliJ IDEA
* **Postman** (para pruebas de API)

## 🏗️ Arquitectura de Puertos
* `8081`: MS Productor 1 (Ubicaciones)
* `8082`: MS Consumidor 1 (MySQL)
* `8083`: MS Productor 2 (Horarios y Rutas)
* `8084`: MS Consumidor 2 (Archivos JSON)
* `15672`: RabbitMQ Management UI
* `3306`: MySQL Database (`transporte_db`)

---

## 🚀 Guía de Ejecución Local

### Paso 1: Levantar la Infraestructura (Docker)
Antes de iniciar los microservicios, necesitamos el broker de mensajería y la base de datos.
1. Abre una terminal en la raíz del proyecto.
2. Ejecuta el siguiente comando para levantar el clúster de RabbitMQ y MySQL en segundo plano:
   ```bash
   docker-compose up -d
3. Verifica que RabbitMQ esté operativo ingresando a http://localhost:15672 (Credenciales: guest / guest).

Paso 2: Levantar los Microservicios
Importante: Levanta siempre primero los Productores. Ellos son los encargados de declarar (crear) las colas físicas en RabbitMQ si estas no existen.

Inicia ProducerQueue1Application (Productor de Ubicaciones).

Inicia ProducerQueue2Application (Productor de Horarios).

Inicia ConsumerQueue1Application (Consumidor MySQL).

Inicia ConsumerQueue2Application (Consumidor Archivos Local).

🧪 Pruebas Automáticas con Postman (Collection Runner)
Para simular una carga real de datos y visualizar el tráfico en RabbitMQ, utilizaremos la herramienta Runner de Postman junto con archivos de datos JSON.

1. Preparar los archivos de datos
Crea dos archivos en tu equipo con los siguientes contenidos:

data_ubicaciones.json

JSON
[
  {"idVehiculo": "FL-00-11", "lat": -33.518, "lon": -70.598},
  {"idVehiculo": "ST-11-20", "lat": -33.440, "lon": -70.650},
  {"idVehiculo": "MQ-99-01", "lat": -33.500, "lon": -70.580}
]
data_horarios.json

JSON
[
  {"ruta": "104-BUS", "estado": "NORMAL", "desc": "Operando en tiempo."},
  {"ruta": "210-BUS", "estado": "ATRASO", "desc": "Congestión alta."},
  {"ruta": "L4-METRO", "estado": "NORMAL", "desc": "Frecuencia de 3 min."}
]
2. Configurar los Requests en Postman
Crea una Colección en Postman con dos peticiones POST y configura sus cuerpos (Body > raw > JSON) usando variables:

Request 1: Enviar Ubicaciones (POST http://localhost:8081/api/v1/locations)

JSON
{
  "vehicleId": "{{idVehiculo}}",
  "latitude": {{lat}},
  "longitude": {{lon}}
}
Request 2: Enviar Horarios (POST http://localhost:8083/api/v1/schedules)

JSON
{
  "routeId": "{{ruta}}",
  "status": "{{estado}}",
  "updatedTime": "2026-02-15T20:00:00",
  "description": "{{desc}}"
}
3. Ejecutar el Runner
Haz clic en el nombre de tu Colección en Postman y selecciona Run Collection.

Selecciona el Request que deseas probar.

En la sección Data, haz clic en Select File y carga el archivo correspondiente (data_ubicaciones.json o data_horarios.json).

Ajusta el Delay a 100ms para ver el flujo en tiempo real.

Haz clic en Run.

4. Verificación de Resultados
Flujo 1 (Ubicaciones): Conéctate a MySQL en localhost:3306 (usuario: devuser, clave: devpassword) y ejecuta SELECT * FROM locations;. Deberías ver los registros insertados.

Flujo 2 (Horarios): Revisa la carpeta schedules_output generada automáticamente en la raíz del proyecto consumer-queue-2. Encontrarás los archivos .json físicos creados por cada evento.
   
