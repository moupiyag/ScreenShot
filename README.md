# ScreenShot

### JDK version used

* JDK version used in this project is **1.7**.

### RDBMS

* **MySQL ver 5.7.19.0** is used in this project.

### KAFKA message queue preliminaries

* **Kafka ver 2.11-0.10.2.1** is used in this project.
* Download Kafka binary from [here](https://kafka.apache.org/downloads). 
* Change `log.dirs=C:\kafka_2.11-0.10.2.1\kafka-logs` in `config\server.properties`
* Change `dataDir=C:\kafka_2.11-0.10.2.1\zookeeper` in `config\zookeeper.properties`
* Create `kafka-logs` and `zookeeper` folders in `C:\kafka_2.11-0.10.2.1\`
* Run the following commands in terminal to start with Kafka and to create topic:
  * `cd C:\kafka_2.11-0.10.2.1\`
  * *Start zookeeper server:* `bin\windows\zookeeper-server-start.batconfig\zookeeper.properties`
  * *Start Kafka server:*`bin\windows\kafka-server-start.batconfig\server.properties`
  * *Create topic:* `bin\windows\kafka-topics.bat--create --zookeeper localhost:2181 --replication-factor 1 --partitions 1--topic screenshot.topic`

**Note:** More details can be found [here](https://kafka.apache.org/quickstart).

### MySQL preliminaries

* To create tables in DB schema *screenshotDB*, run SQL script: `\ScreenShotProject\src\main\resources\db_schema.sql`

### Application context preliminaries

* Change `screenShotTargetDir` to your local path of choice in `\ScreenShotProject\src\main\resources\applicationContext.xml`

  ```java
  <bean id="screenShotService" class="com.detectify.service.ScreenShotServiceImpl"> 
        	<property name="screenShotDao"> 
           	<ref bean="screenShotDao"/> 
        	</property>
        	<property name="screenShotTargetDir"> 
           	<value>C:/Users/Moupiya/Documents/ScreenShotData/</value> 
        	</property>
     	</bean>
  ```

* To start back-end service: `mvn clean jetty:run`

### Commands to call API end points

* To take screenshots (list of urls): 

  ```typescript
  curl-X POST http://localhost:8080/screenShot/takeScreenShot/byUrls  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:725586f6-3faa-bd5e-43a2-47f630842835'  -d'["https://en.wikipedia.org/wiki/URL","https://www.facebook.com/","https://www.key-systems.net/en/blog/list-of-domain-extensions","https://iwantmyname.com/domains/domain-name-registration-list-of-extensions"]'
  ```

* To take screenshots (file with url list):

  ```java
  curl -X POST http://localhost:8080/screenShot/takeScreenShot/byFile  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:725586f6-3faa-bd5e-43a2-47f630842835'  -d'C:/Users/Moupiya/Downloads/urllist.txt'
  ```

* To search (request by url):

  ```java
  curl -X GET 'http://localhost:8080/screenShot/search/byUrl?url=https://www.google.com'  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:8e249d00-6521-da66-3348-12bfd7f26b13'
  ```

* To search (request by date):

  ```java
  curl -X GET  'http://localhost:8080/screenShot/search/byDate?date=20171017&dateFormat=yyyyMMdd'  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:8e249d00-6521-da66-3348-12bfd7f26b13'
  ```

* To search (request by url and date):

  ```java
  curl -X GET  'http://localhost:8080/screenShot/search/byUrlAndDate?url=https://www.google.com&date=20171017&dateFormat=yyyyMMdd'  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:8e249d00-6521-da66-3348-12bfd7f26b13'
  ```

* To search (request by date range):

  ```java
  - curl -X GET  'http://localhost:8080/screenShot/search/byDateRange?startDate=20171015&endDate=20171016&dateFormat=yyyyMMdd'  -H 'cache-control: no-cache'  -H 'content-type: application/json'  -H 'postman-token:8e249d00-6521-da66-3348-12bfd7f26b13'
  ```

  ​

### Scalability

* For this project we are using local server to store the physical images. We can use other third party service to store them like AWS.
* We can add a caching layer on top of our application like varnish, as most likely these data will be static.
* We can use different level of indexing to store files. Or we can use some other hashing such as SHA.
* We can increase number of partitions of our Kafka topic and number of listener threads to handle huge number of screenshot requests. Right now we are having only one thread.
* Problem with kafka is the following: there is no way of nacking a message. We could have used other messaging services like jms. But, for huge number of requests, kafka is a better choice if we can handle retry logic in kafka in a graceful way---this I didn't implement as part of this project. 



