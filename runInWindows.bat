set JAVA_OPTS=-Dhmhb.url.prefix=https://hmhb.herokuapp.com -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
set PORT=8080
set JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/hmhb_db

java %JAVA_OPTS% -Dserver.port=%PORT% -jar build/libs/hmhb-server-1.0-SNAPSHOT.jar
