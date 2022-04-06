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
</ul>

## Idea:
A user solves multiplication challenges with factors between 11 and 99. For example, 30x60. <br>
Results and leaderboard are displayed.

## Technologies:
<ul>
  <li>Java 14</li>
  <li>React.js</li>
  <li>Spring Boot 2</li>
  <li>Spring Cloud (Gateway, Consul, Sleuth)</li>
  <li>Lombok</li>
  <li>Logback</li>
  <li>MongoDB</li>
  <li>JUnit 5</li>
  <li>Mockito</li>
  <li>AssertJ</li>
  <li>RabbitMQ</li>
  <li>Consul (service registry, health checks, load balancing, centralized configuration/logging)</li>
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
