Rundeck API dumper

HOW TO DEPLOY
Requirements
* docker latest version

Local deploy
+ docker-compose.yml with next services:
  - mongo (latest version)
  - rundeck-api-dumper
check browser:
http://localhost:8081/swagger-ui.html

version: 0.0.1
+ endpoints to:
  - get projects
  - get jobs from a project
  - get running jobs from a project
