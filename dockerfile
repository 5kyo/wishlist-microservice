FROM debian:bullseye
RUN apt update
RUN apt install -y openjdk-11-jdk
WORKDIR ./wishlist
COPY ./target/quarkus-app .
CMD ["java", "-jar","quarkus-run.jar"]
EXPOSE 8080