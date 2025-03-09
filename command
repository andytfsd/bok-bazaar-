# Run Spotless plugin
mvn clean spotless:apply

# Run service with command line
mvn spring-boot:run -Dspring-boot.run.arguments="--PORT=2031 --spring.application.instance_id=us1-030225"
mvn spring-boot:run -Dspring-boot.run.arguments="--PORT=2032 --spring.application.instance_id=us2-030225"


# Docker Commands
# MySql :
docker run -d --name mysql-docker -p3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=app-db -e MYSQL_USER=user -e MYSQL_PASSWORD=password -d mysql:8.0
