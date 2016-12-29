FROM anapsix/alpine-java:8
MAINTAINER Paul Hoang 2016-12-15
RUN ["mkdir", "-p", "/home/javaapp"]
COPY ./build/libs/mySharesCollector-1.0.jar /home/javaapp/mySharesCollector-1.0.jar
WORKDIR /home/javaapp
EXPOSE 5555
ENTRYPOINT ["java", "-jar", "mySharesCollector-1.0.jar"]
