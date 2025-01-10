FROM openjdk:23
ADD target/*.jar irctcdemoone.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","irctcdemoone.jar"]