[![Stories in Ready](https://badge.waffle.io/healthbam/healthbam.png?label=ready&title=Ready)](https://waffle.io/healthbam/healthbam)

# healthBAM
Making an **impact** on the health of Babies And Mothers

## How To Contribute

TODO - describe various ways to help out the non-profit or maybe link the main site

## Getting Started

TODO - put the rest of this README in a development section?

### Setting Up IDE

If using IntelliJ, "Import Project", and select the build.gradle file.
This will build and set up IntelliJ for the project.

Alternative method: you can run this command to generate an "ipr" file that can be opened:

~~~~shell
./gradlew idea
~~~~

### Building

~~~~shell
./gradlew build
~~~~

### Running Locally

You must have a postgres DB to use when running locally.

Create a .env file to the top level directory of this project with the PORT to serve and JDBC_DATABASE_URL:

~~~~shell
PORT=8080
JDBC_DATABASE_URL='jdbc:postgresql://localhost:5432/hmhb_db'
~~~~

Now you can run the latest code you have built locally with:

~~~~shell
heroku local
~~~~

### Testing In The Console

#### Organization REST Calls

##### Get All Example

~~~~shell
curl -v \
     -X GET \
     'http://localhost:8080/api/organizations'
~~~~

##### Get Single Example

~~~~shell
curl -v \
     -X GET \
     'http://localhost:8080/api/organizations/1001'
~~~~

##### Create Example

~~~~shell
curl -v \
     -X POST \
     -H 'Content-Type: application/json' \
     -d '{ "name": "Test Org", "contactPhone": "800-555-1234", "contactEmail": "test.org@mailinator.com", "websiteUrl": "http://www.test-org.com", "facebookUrl": "http://www.facebook.com/TestOrg" }' \
     'http://localhost:8080/api/organizations'
~~~~

##### Update Example

~~~~shell
curl -v \
     -X POST \
     -H 'Content-Type: application/json' \
     -d '{ "id": 1001, "name": "Updated Test Org", "contactPhone": "800-555-1234", "contactEmail": "test.org@mailinator.com", "websiteUrl": "http://www.test-org.com", "facebookUrl": "http://www.facebook.com/TestOrg" }' \
     'http://localhost:8080/api/organizations/1001'
~~~~

##### Delete Example

~~~~shell
curl -v \
     -X DELETE \
     'http://localhost:8080/api/organizations/1001'
~~~~

#### Program REST Calls

##### Get All Example

~~~~shell
curl -v \
     -X GET \
     'http://localhost:8080/api/programs'
~~~~

##### Get Single Example

~~~~shell
curl -v \
     -X GET \
     'http://localhost:8080/api/programs/1001'
~~~~

##### Create Example

~~~~shell
curl -v \
     -X POST \
     -H 'Content-Type: application/json' \
     -d '{ "name": "Test Program", "organization": { "id": 1 }, "startYear": 2005, "streetAddress": "456 Peachtree St.", "state": "GA", "zipCode": "30332" }' \
     'http://localhost:8080/api/programs'
~~~~

##### Update Example

~~~~shell
curl -v \
     -X POST \
     -H 'Content-Type: application/json' \
     -d '{ "id": 1001, "name": "Updated Test Program", "organization": { "id": 1 }, "startYear": 2005, "streetAddress": "456 Peachtree St.", "state": "GA", "zipCode": "30332" }' \
     'http://localhost:8080/api/programs/1001'
~~~~

##### Delete Example

~~~~shell
curl -v \
     -X DELETE \
     'http://localhost:8080/api/programs/1001'
~~~~

### Perks Offered By Spring Boot

Endpoints providing information on:

* Health Check: <a target="_blank" href="http://localhost:8080/health">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/health">Heroku</a>
* Logs: <a target="_blank" href="http://localhost:8080/logfile">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/logfile">Heroku</a>
* Java Heap Dump: <a target="_blank" href="http://localhost:8080/heapdump">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/heapdump">Heroku</a>
* Java Thread Dump: <a target="_blank" href="http://localhost:8080/dump">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/dump">Heroku</a>
* Environment Variables: <a target="_blank" href="http://localhost:8080/env">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/env">Heroku</a>
* Metrics <a target="_blank" href="http://localhost:8080/metrics">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/metrics">Heroku</a>
* Application Info: <a target="_blank" href="http://localhost:8080/info">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/info">Heroku</a>
* Trace Info Of The Last 100 Requests: <a target="_blank" href="http://localhost:8080/trace">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/trace">Heroku</a>
* Spring Config Beans: <a target="_blank" href="http://localhost:8080/configprops">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/configprops">Heroku</a>
* Spring Beans: <a target="_blank" href="http://localhost:8080/beans">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/beans">Heroku</a>
* Spring MVC Request Mappings: <a target="_blank" href="http://localhost:8080/mappings">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/mappings">Heroku</a>
* Spring Boot Autoconfig Info: <a target="_blank" href="http://localhost:8080/autoconfig">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/autoconfig">Heroku</a>
* Documenation For These Endpoints: <a target="_blank" href="http://localhost:8080/docs">Local</a> / <a target="_blank" href="https://hmhb.herokuapp.com/docs">Heroku</a>
