Para ejecutar: 

1.- Inicia un servidor de Temporal local:

temporal server start-dev

2.- Compila con Maven:

mvn clean compile

3.- Ejecuta el main:

mvn exec:java -Dexec.mainClass="practiceio.demo.Starter"
