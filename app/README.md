# Music Festival App

This repository contains an Android Studio application, written in Kotlin. The applications does the following:
* Input and display Festival Name
* Input and display Festival Type
* Input and display Festival Date
* Input location and store in JSON file

This repository contains `Deployments` for the clients as well as `KafkaTopic` and `KafkaUsers` for use by Strimzi operators.
Logging configuration can be found in the `log4j2.properties` file for the producer and consumer separately.

## Build

The pre-built images are available on our Docker Hub.
But if you want to do any modifications to the examples, you will need to build your own versions.

To build these examples you need some basic requirements.
Make sure you have `make`, `docker`, `JDK 1.8` and `mvn` installed.
After cloning this repository to your folder Hello World example is fully ready to be build.
By one single command Java sources are compiled into JAR files, Docker images are created and pushed to repository.
By default the Docker organization to which images are pushed is the one defined by the `USER` environment variable which is assigned to the `DOCKER_ORG` one.
The organization can be changed exporting a different value for the `DOCKER_ORG` and it can also be the internal registry of an OpenShift running cluster.

The command for building the examples is:


## Usage