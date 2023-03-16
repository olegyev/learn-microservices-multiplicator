# Training microservices project - core
Simple training project to learn microservices. <br>
This repository is for the core User and Challenge services.

## Source of knowledge:
https://github.com/Book-Microservices-v2

## Related repos:
<ul>
  <li><a href='https://github.com/olegyev/learn-microservices-multiplicator-frontend'>Frontend service</a></li>
  <li><a href='https://github.com/olegyev/learn-microservices-multiplicator-gamification'>Gamification service</a></li>
  <li><a href='https://github.com/olegyev/learn-microservices-multiplicator-gateway'>Gateway service</a></li>
  <li><a href='https://github.com/olegyev/learn-microservices-multiplicator-logs'>Centralized logging service</a></li>
  <li><a href='https://github.com/olegyev/learn-microservices-multiplicator-docker'>Dockerization</a></li>
</ul>

## Idea:
A user solves multiplication challenges with factors between 11 and 99. For example, 30x60. <br>
Results and leaderboard are displayed.

## Technologies:
<ul>
  <li>Java 14</li>
  <li>React.js</li>
  <li>Spring Boot 2</li>
  <li>Spring Cloud (Gateway, Consul, Load Balancer, Sleuth)</li>
  <li>Lombok</li>
  <li>Logback</li>
  <li>MongoDB</li>
  <li>JUnit 5</li>
  <li>Mockito</li>
  <li>AssertJ</li>
  <li>RabbitMQ</li>
  <li>Consul (service registry, health checks, load balancing, centralized configuration/logging)</li>
  <li>Docker</li>
  <li>Docker Compose</li>
</ul>

## To boot in dev mode:
<ol>
    <li>Install JDK 14.</li>
    <li>Install MongoDB - see <a href="https://docs.mongodb.com/manual/administration/install-community/">here</a>.</li>
    <li>Create MongoDB cluster which supports sessions (transactions) - see <a href="https://stackoverflow.com/a/62729445">here</a>.</li>
    <li>Install RabbitMQ server - see <a href="https://www.rabbitmq.com/download.html">here</a>.</li>
    <li>Enable RabbitMQ GUI manager using this command:<br>
        <code>$ rabbitmq-plugins enable rabbitmq_management</code><br>
        Will be available via <code>http://localhsot:15672</code><br>
        If needed, RabbitMQ server can be run manually from within the RabbitMQ <code>sbin</code> command prompt (run as administrator): <code>$ rabbitmq-server start</code></li>
    <li>Install Consul by HashiCorp as a Service Registry - see <a href="https://learn.hashicorp.com/tutorials/consul/get-started-install">here</a>.</li>
    <li>To run Consul in dev mode, use the following command: <code>$ consul agent -node=learnmicro -dev</code><br>
        If port is occupied on Windows, use the following commands:<br>
        <code>$ netstat -ano | findstr :\PORT\</code><br>
        <code>$ taskkill /PID \PID\ /F</code></li>
    <li>Consul GUI is available via <code>http://localhost:8500</code><br>
        NOTE! While adding YAML properties to the Consul KV using the Consul GUI, use 4 spaces instead of tab for indentation.<br>
        NOTE! While adding {application},{profile} property using the Consul GUI, need to literally name folder using separator (comma ',' by default; to change separator, specify <code>spring.cloud.consul.config.profile-separator</code> within bootstrap.properties).<br>
        Example: config/defaults,production/application.yml; config/multiplicator,rabbitmq-production/application.yml<br>
        To run application specifying active profiles, use the following command: <code>$ ./mvnw spring-boot:run '-Dspring-boot.run.arguments="--spring.profiles.active=rabbitmq-production,production"'</code></li>
    <li>To run the new microservice's instance use the following command from within the microservice's directory:<br>
        <code>$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=\PORT\"</code></li>
    <li>To run front-end, install <code>Node.js</code> and <code>npm</code> - see <a href="https://nodejs.org/en/download/">here</a>.</li>
    <li>To run front-end in the dev mode, use the following command from within the front-end application's directory:<br>
        <code>$ npm start</code></li>
</ol>

## To boot as local containers using Docker:
<ol>
    <li>Install Docker - see <a href="https://docs.docker.com/get-docker/">here</a>.</li>
    <li>Prepare JAR from within the root folder of the <a href='https://github.com/olegyev/learn-microservices-multiplicator'>multiplicator service</a>:<br>
        NOTE! This command can be used for all the services mentioned below:<br>
        <code>$ ./mvnw clean package</code><br>
        NOTE! All the services mentioned below may require running Consul agent to build Docker image properly (see <a href="#to-boot-in-dev-mode">previous section</a>):<br>
        <code>$ consul agent -node=learnmicro -dev</code></li>
    <li>Build Docker image for the multiplicator service from within its root folder:<br>
        <code>$ docker build -t multiplicator:\VERSION\</code><br>
        NOTE! Version should be provided as #.#.# (e.g. 1.0.0).<br>
        If changed, need to update also in Docker Compose file - see <a href='https://github.com/olegyev/learn-microservices-multiplicator-docker/blob/master/docker/docker-compose.yml'>here</a>.<br>
        NOTE! If project's version tag is changed in POM.xml, need also to edit Dockerfile's COPY command.</li>
    <li>Prepare JAR from within the root folder of the <a href='https://github.com/olegyev/learn-microservices-multiplicator-gamification'>gamification service</a>.<br>
    <li>Build Docker image for the gamification service from within its root folder:<br>
        <code>$ docker build -t gamification:\VERSION\ .</code></li>
    <li>Prepare JAR from within the root folder of the <a href='https://github.com/olegyev/learn-microservices-multiplicator-gateway'>gateway service</a>.<br>
    <li>Build Docker image for the gateway service from within its root folder:<br>
        <code>$ docker build -t gateway:\VERSION\ .</code></li>
    <li>Prepare JAR from within the root folder of the <a href='https://github.com/olegyev/learn-microservices-multiplicator-logs'>logging service</a>.<br>
    <li>Build Docker image for the logging service from within its root folder:<br>
        <code>$ docker build -t logs:\VERSION\ .</code></li>
    <li>Build Docker image for the <a href='https://github.com/olegyev/learn-microservices-multiplicator-frontend'>frontend service</a> from within its root folder:<br>
        <code>$ docker build -t challenges-frontend:\VERSION\ .</code></li>
    <li>Build Docker image for the <a href="https://github.com/olegyev/learn-microservices-multiplicator-docker/tree/master/docker/consul">Consul importer service</a> (serves only to import initial configurations by the Consul, exits immediately after completing this task):<br>
        <code>$ docker build -t consul-importer:\VERSION\ .</code></li>
    <li>Run the complete system using Docker Compose from within the <a href='https://github.com/olegyev/learn-microservices-multiplicator-docker/tree/master/docker'>dockerization folder</a>:<br>
        <code>$ docker-compose up</code></li>
    <li>To stop the whole application and completely remove the containers, use the following command from a different terminal:<br>
        <code>$ docker-compose down -v</code></li>
    <li>To start additional service's instances, use the following command:<br>
        <code>$ docker-compose up --scale \IMAGE_NAME\=\INSTANCES_AMOUNT\</code></li>
</ol>
