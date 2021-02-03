# Build layer

FROM maven:3.6.3-adoptopenjdk-11 as build
LABEL maintainer="renjithkumar1@gmail.com"

ARG ENVIRONMENT

LABEL service=salary-management-service \
      stage=build \
      env=${ENVIRONMENT}

RUN mkdir /ms
WORKDIR /ms
# Copy the source code to build it
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the executable jar (Spring Boot fat jar)
RUN  mvn clean package -DskipTests=true \
  && mv ./target/salary-management-service-0.0.1-SNAPSHOT.jar ./ms.jar


# Deployment layer

FROM adoptopenjdk:11-jre-hotspot
LABEL maintainer="renjithkumar1@gmail.com"

ARG ENVIRONMENT

LABEL service=salary-management-service \
      stage=deploy \
      env=${ENVIRONMENT}


# Add a non-root user user an
RUN addgroup -gid 1009  udemo \
 && adduser -uid 1009 -gid 1009 --gecos "udemo udemo,udemo,udemo,udemo" --disabled-password udemo \
 &&  mkdir -p /ms && chown -R udemo:udemo /ms

WORKDIR /ms
COPY --chown=udemo:udemo --from=build /ms/ms.jar ./ms.jar
# Set udemo it as the current user (instead of root)
USER udemo
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./ms.jar"]