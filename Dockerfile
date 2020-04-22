FROM java:openjdk-8u91-jdk
COPY ./target/credential-1.0.0-SNAPSHOT.jar /tmp
WORKDIR /tmp
EXPOSE 8082
CMD ["java", "-jar", "credential-1.0.0-SNAPSHOT.jar"]