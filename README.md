# Weather Forecast

To get the Metric Result hit the endpoint http://localhost:8080/data/XX/YY
where XX is the country code (ISO 3166 format) and YY the city name.

## Running 
Maven wrapper is configured in project. So you can use local maven installation as well as wrapped version.
All command provided in form of local installation
* run tests
    ```bash
    mvn clean test
    ```
* run
    ```bash
    mvn spring-boot:run
    ```