FROM maven:3.3-jdk-8

WORKDIR /usr/src/mymaven

ADD . /usr/src/mymaven

RUN mvn clean install -X