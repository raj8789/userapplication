FROM java:8
WORKDIR /
ADD target/userapplication-1.0-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
CMD ["demo.jar"]