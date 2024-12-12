# Introduction

Le projet est composé deux Microservices : Module CommandeService et Module PaimentService
En plus un module SharedDTO qui contient les DTO cumun utilisé dans l'application.

### Technologies utilisées

 * Java 21
 * Spring Boot 3.3.4
 * Maven
 * Spring Data JPA
 * spring cloud stream kafka
 * spring cloud version 2024.0.0
 * H2 pour la base de données
 * JUnit et MockMvc pour les tests


### Prérequis

Java 21 ou supérieur
Maven 3.8+
Apache Kafka
H2 in-memory DB

### Démarrage de Kafka
Kafka doit être pré-installé est configuré sur votre machine.

* Windows <br>
Démarrer Zookeeper : Kafka dépend de Zookeeper pour la gestion de ses services. Il faut donc démarrer Zookeeper avant de démarrer le broker Kafka.


<pre>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties</pre>

Démarrer le broker Kafka :<br>

Une fois Zookeeper démarré, vous pouvez démarrer le broker Kafka.


<pre>.\bin\windows\kafka-server-start.bat .\config\server.properties</pre>


Créer les deux topics utilisé dans le projet :<br>

<pre>.\bin\windows\kafka-topics.bat --create --topic commande-topics --bootstrap-server localhost:9092</pre>

<pre>.\bin\windows\kafka-topics.bat --create --topic paiement-topics --bootstrap-server localhost:9092</pre>


Lister les topics :


<pre>.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092</pre>

Lire des messages du topic (Consumer) :

<pre>.\bin\windows\kafka-console-consumer.bat --topic commande-topics --from-beginning --bootstrap-server localhost:9092</pre>

<pre>.\bin\windows\kafka-console-consumer.bat --topic paiement-topics --from-beginning --bootstrap-server localhost:9092</pre>




Linux/Unix


<pre>./bin/zookeeper-server-start.sh ./config/zookeeper.properties</pre>

Démarrer le broker Kafka : Après avoir démarré Zookeeper, lancez le broker Kafka.


<pre>./bin/kafka-server-start.sh ./config/server.properties</pre>


Créer un topic :


<pre>./bin/kafka-topics.sh --create --topic commande-topics --bootstrap-server localhost:9092</pre>

<pre>./bin/kafka-topics.sh --create --topic paiement-topics --bootstrap-server localhost:9092</pre>

Lister les topics :

<pre>./bin/kafka-topics.sh --list --bootstrap-server localhost:9092</pre>

Lire des messages du topic (Consumer) :

<pre>./bin/kafka-console-consumer.sh --topic commande-topics --from-beginning --bootstrap-server localhost:9092</pre>

<pre>./bin/kafka-console-consumer.sh --topic paiement-topics --from-beginning --bootstrap-server localhost:9092</pre>

### Configuration de l'environnement
Assurez-vous que Java et Maven sont installés:

<pre>java -version</pre>
<pre>mvn -version</pre>



### Installer les dépendances:


<pre>mvn clean install</pre>



Exécution de l'application
Pour exécuter l'application, utilisez la commande suivante pour chaque microservice :

<pre>mvn spring-boot:run</pre>
