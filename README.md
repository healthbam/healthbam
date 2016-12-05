[![Stories in Ready](https://badge.waffle.io/healthbam/healthbam.png?label=ready&title=Ready)](https://waffle.io/healthbam/healthbam)

# healthBAM
Making an **impact** on the health of Babies And Mothers

## How To Contribute

[See Contributing Guidelines](CONTRIBUTING.md).

## Getting Started

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

That local DBMS must have A healthbam_sql user:

~~~~sql
CREATE USER healthbam_sql PASSWORD 'changeit';
~~~~

And A hmhb_db database:

~~~~sql
CREATE DATABASE hmhb_db WITH OWNER healthbam_sql;
~~~~

Create a .env file to the top level directory of this project with the PORT to serve and JDBC_DATABASE_URL:

~~~~shell
PORT=8080
JDBC_DATABASE_URL="jdbc:postgresql://localhost:5432/hmhb_db"
GOOGLE_OAUTH_CLIENT_ID="259324353484-f8u4ltb5qko7fltub68dguhs16ae93nr.apps.googleusercontent.com"
GOOGLE_OAUTH_CLIENT_SECRET="<secret>"
HMHB_JWT_SECRET="<also secret>"
~~~~

Now you can run the latest code you have built locally with:

~~~~shell
heroku local
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
