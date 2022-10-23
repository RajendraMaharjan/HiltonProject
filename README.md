# HiltonProject

How to start the HiltonProject application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/dropwiz-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`


## Edit Run Configuration: HiltonProject application
Add these in program argument

    `server /Users/rajendramaharjan/hiltonpraveen/dropwiz/config.yml`


Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
