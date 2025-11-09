# WSToken

 Servicio que genera y valida un token con algortimo SHA-256
 Se utiliza como parte del proceso de acceso al API de Prestamos

## Tecnologias

 - Java 21  
 - Spring Boot 3.5.7  
 - Azure App Service  
 - SwaggerHub
 - Postman 


## URL servicio

 - [Azure App Service](https://wstoken-e4bbh5cqfcdrghe3.canadacentral-01.azurewebsites.net)

## Repositorio GitHub

- Código fuente: https://github.com/andresaquinogarcia/WSToken.git
				
## Endpoints

 - POST /api/token/generar Genera un nuevo token con SHA-256
 - GET  /api/token/validar Valida que el token enviado sea valido y vigente
 - GET  /api/token/status  Verifica que el servicio este disponible 

## Documentacion Swagger

 La documentacion del API esta publicada en SwaggerHub en el siguiente enlace

 - [Documentacion SwaggerHub](https://app.swaggerhub.com/apis/mexico-5bb/WSToken/0.0.1)

## Coleccion Postman

 En el repositorio se incluye el archivo

 - WSToken.postman_collection.json
 
Contiene contiene las peticiones

 - Generar token  
 - Validar token  
 - Consultar estado del servicio

 Para probar el servicio se incluye el archivo WSToken.postman_collection.json

 Pasos

 - abrir Postman  
 - importar el archivo WSToken.postman_collection.json  
 - seleccionar la peticion Generar token  
 - enviar la peticion y revisar la respuesta  
 - opcionalmente ejecutar Validar token (pasandole en el header Authorization: Bearer <TOKEN>) y Consultar estado del servicio  

## CI/CD con GitHub Actions

El proyecto cuenta con dos flujos automatizados en .github/workflows/

  - maven.yml - Ejecucion de integracion continua  
  - Se ejecuta al hacer push a master  
  - Configura JDK 21 y ejecuta mvn clean package
  - Compila y valida el proyecto

  - master_wstoken.yml - Despliegue continuo  
  - Compila el proyecto con JDK 21
  - Sube el artefacto generado y lo despliega en Azure App Service - WSToken
  - Se ejecuta al hacer push en master

  Estos pipelines garantizan la compilación, validación y despliegue automático del servicio

## Despliegue

 - El sercicio se encuentra desplegado en Azure APP Service y es consumido por el API WSPrestamos

## Analisis de Calidad con SonarCloud

WSToken se integro con SonarCLoud para realizar el analisi del codigo
Se utilizo Jacoco para las pruebas unitarias para las metricas de mantenimiento, cobertura y duplicacion

[Reporte SonarCloud](https://sonarcloud.io/project/overview?id=andresaquinogarcia_WSToken)

- Evidencia de analisis en SonarCloud en docs/sonar-coverage-wstoken.png
