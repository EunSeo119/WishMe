FROM adoptopenjdk/openjdk11

COPY ./build/libs/user-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar

EXPOSE 9998

ENTRYPOINT [ "java" ,"-jar", "backend-0.0.1-SNAPSHOT.jar"]