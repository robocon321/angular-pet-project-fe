FROM openjdk:17
EXPOSE 8080
ADD target/auth-service.war auth-service.war
ENTRYPOINT ["java","-war","/auth-service.war"]