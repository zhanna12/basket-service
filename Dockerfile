FROM openjdk:8
ADD target/basket-service.jar basket-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "basket-service.jar"]