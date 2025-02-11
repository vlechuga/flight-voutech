# README #


### Requisitos técnicos ###

- Java 17
- Spring Boot (Spring Web, Spring Data JPA)
- Base de datos: H2 
- Tests automatizados:
  - Se implementarón test unitarios con JUnit en las capas Repoitory y Service
  - Se realizarón pruebas de los Endpoints con PowerTest. La App debe estar ejecutandose en el puerto 8080.

    ####StepByStep
    1.  Install nvm
    2.  Install nodeJS last version using nvm
        - run `npm install -g mocha` to install mocha as global package
        - run `cd tests`
        - run `npm install` inside _test_ folder
    3.  Run tests
        - run `sh test_runner.sh` command
    4.  Test results
        - Review test results inside `_./target/test-result/result.xml`
    
- Swagger para documentación de API: http://localhost:8080/swagger-ui/index.html
- Mensajería: RabbitMQ
  * queue.confirmed-reservation: cola para las reservas confirmadas.
  * queue.rejected-reservation: cola para las reservas rechazadas por asiento no disponible o por algún error.
  * queue.reservation: cola para las peticiones de reserva de asiento. Los mensajes tienes TTL=2min
  
- Manejo de concurrencia: Para la prueba se implementó Bloqueo Optimista en la entidad Asiento. Lo ideal sería crear un prodecimiento almacenado que actualice el Asiento y cree la Reservación.
- Diseño modular: config, controller, dto, entity, exception, mapper, notification, repository, service
- Manejo de cache con Caffeine para reducir los accesos a la Base de datos, principalmente a la tabla Asiento. 

### Ejecutar aplicación ###

```docker build -t flight-service .```

```docker run -p 8080:8080 flight-service```

### RabbitMQ ###
```bash
docker pull rabbitmq:3.11.16-management
docker run -d --restart unless-stopped --name rabbitmq -p 15672:15672 -p 5672:5672 --shm-size 2g -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3.11.16-management
 ```



