# Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
FROM openjdk:11.0.10

WORKDIR /opt/
COPY ./target/pikit-masterclass-camunda.jar ./pikit-masterclass-camunda.jar
EXPOSE 8004
CMD ["java" ,"-Xms256m", "-Xmx512m", "-jar", "/opt/pikit-masterclass-camunda.jar"]

