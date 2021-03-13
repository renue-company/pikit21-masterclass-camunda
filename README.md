# pikit21-masterclass-camunda
Example of pizza ordering process using Camunda and Spring Boot.

This project was created for educational purposes only.

### Requirements
JDK 11+

### How to build
Using terminal in project folder: ``mvnw clean package``

### How to run
Using terminal in project folder: ``mvnw spring-boot:run``

### After run
You can go to http://localhost:8080 (admin/admin credentials) for looking on deployed processes in Cockpit (for example),
unfortunately in the free version of Camunda only Runtime processes can be monitored.

You can run Excamad to monitor your history process instances https://github.com/KotskinKotskin/camunda-excamad.

In Docker for example: ``docker run -d -p 9090:80 kotovdenis/excamad:latest``. It is start Excamad on port 9090.

In Excamad UI Settings set **URL Camunda Engine REST** to http://localhost:8080/engine-rest/

### Testing
Run existing tests: ``mvnw -Dtest=ScenarioTests test``

For testing manually: import Postman collection ./postman/PikIT Camunda Masterclass.postman_collection.json
to Postman (https://www.postman.com/)

### Output
After starting your processes, go to ./executions folder for looking your executed tasks. Foler per process. 
Use folder name (it's business-process-key of your process, 20210313-1829-0001 for example) to find your history instances 
in Excamad (http://localhost:9090/?#/history?baseurl=http%3A%2F%2Flocalhost%3A8080%2Fengine-rest%2F) 
or running instances in Cockpit (http://localhost:8080/camunda/app/cockpit/default/#/processes)