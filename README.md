# spring-boot-zuul-math-app

It is composed of two projects
1. api-gateway is the api gateway project will is client facing and acting as a load balancer as well
2. math-app is the math service application, a backend project, will be accessd by api-gateway

The request will first hit api-gateway, and will be dispatched to one of the live node of math-app
Kafka is added into this project to act as the message broker to synchronize the cache of both nodes. 
To avoid distributed cache consistency issue, I programmed in the way that the cache will be updated only by Kafka topic update, to archieve an eventual consistency. 

The flow is :  

```
client make a request 
-> math calculation resource created 
-> publish the resource to topic
-> all nodes subscribe to the topic will have its own cache updated
```

## Getting Started


### Prerequisites

Before running both project, make sure you have a kafka up and running

```
by default kafka is running with localhost:9092

```

### Installing


```
zookeeper-server-start.bat ../../config/zookeeper.properties
kafka-server-start.bat ../../config/server.properties
```

At this step you have Kafka up and running, then you can first spin up api-gateway, the port is 8080

```
cd api-gateway
gradle bootRun
```

Now you can spin up two instances of math-app, but make sure you are running at different port and groupId, the configuration is in application.porperties file
The default two ports that the api-gateway will be looking at are 8090, 8091

1st instance
```
server.port=8090
app.topic.calculation.groupId = group0

cd math-app
gradle bootRun
```

2nd instance
```
server.port=8091
app.topic.calculation.groupId = group1

cd math-app
gradle bootRun
```

Now by using at REST API client, you will be able to test the whole application

1. creat a math calculation 

```
HTTP: POST
URL: localhost:8080/math/calculations
Body: 
{
	"firstNumber" : 12222,
	"secondNumber" : 45,
	"action" : "ADD"
}
Headers: 
Accept: application/json
Content-Type: application/json
```

2. fetch the previously created math calculation resource

```
HTTP: GET
URL: localhost:8080/math/calculations/1
Headers: Accept: application/json
```

## Running the tests

To test the load balancer of api-gateway, kill one instance of math-app, say 8090, and repeat the previous test.
The request will be dispatched to the live node. 
Attention: the fist try of GET or POST might fail, second try will be ok.

Then to bring back the failed node to the current status as the live one, one thing needs to be done, change the groupid 

```
server.port=8090
app.topic.calculation.groupId = group2

cd math-app
gradle bootRun
```

At the spin up phase, you will see it recieves all the messages from topic: 

Examples:

```
2018-06-25 09:28:34.293  INFO 4664 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka version : 1.0.1
2018-06-25 09:28:34.293  INFO 4664 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId : c0518aa65f25317e
2018-06-25 09:28:34.308  INFO 4664 --- [           main] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService
2018-06-25 09:28:34.477  INFO 4664 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8090 (http) with context path ''
2018-06-25 09:28:34.488  INFO 4664 --- [           main] c.s.www.mathapp.MathAppApplication       : Started MathAppApplication in 8.535 seconds (JVM running for 11.275)
2018-06-25 09:28:34.749  INFO 4664 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-1, groupId=group0] Discovered group coordinator DUB-E7450-30.Datalex.com:9092 (id: 2147483647 rack: null)
2018-06-25 09:28:34.757  INFO 4664 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-1, groupId=group0] Revoking previously assigned partitions []
2018-06-25 09:28:34.757  INFO 4664 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions revoked: []
2018-06-25 09:28:34.758  INFO 4664 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-1, groupId=group0] (Re-)joining group
2018-06-25 09:28:34.804  INFO 4664 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-1, groupId=group0] Successfully joined group with generation 1
2018-06-25 09:28:34.806  INFO 4664 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-1, groupId=group0] Setting newly assigned partitions [calculation1.t-0]
2018-06-25 09:28:34.844  INFO 4664 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : partitions assigned: [calculation1.t-0]
2018-06-25 09:28:34.960  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":0,"firstNumber":45,"secondNumber":45,"result":90,"action":"ADD"}'
2018-06-25 09:28:35.107  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":1,"firstNumber":45,"secondNumber":45,"result":2025,"action":"MULTIPLY"}'
2018-06-25 09:28:35.109  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":2,"firstNumber":45,"secondNumber":450,"result":-405,"action":"SUBTRACT"}'
2018-06-25 09:28:35.112  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":3,"firstNumber":12,"secondNumber":112,"result":0.11,"action":"DIVIDE"}'
2018-06-25 09:28:35.115  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":4,"firstNumber":12222,"secondNumber":112,"result":109.12,"action":"DIVIDE"}'
2018-06-25 09:28:35.119  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":5,"firstNumber":12222,"secondNumber":12,"result":1018.50,"action":"DIVIDE"}'
2018-06-25 09:28:35.121  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":6,"firstNumber":12222,"secondNumber":1212,"result":10.08,"action":"DIVIDE"}'
2018-06-25 09:28:35.124  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":7,"firstNumber":12222,"secondNumber":1212,"result":13434,"action":"ADD"}'
2018-06-25 09:28:35.126  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":8,"firstNumber":12222,"secondNumber":1212,"result":11010,"action":"SUBTRACT"}'
2018-06-25 09:28:35.129  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":9,"firstNumber":12222,"secondNumber":1212,"result":11010,"action":"SUBTRACT"}'
2018-06-25 09:28:35.132  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":10,"firstNumber":12222,"secondNumber":1212,"result":14813064,"action":"MULTIPLY"}'
2018-06-25 09:28:35.134  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":11,"firstNumber":12222,"secondNumber":12122,"result":24344,"action":"ADD"}'
2018-06-25 09:28:35.137  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":0,"firstNumber":1,"secondNumber":2,"result":3,"action":"ADD"}'
2018-06-25 09:28:35.140  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":13,"firstNumber":12222,"secondNumber":12122,"result":24344,"action":"ADD"}'
2018-06-25 09:28:35.143  INFO 4664 --- [ntainer#0-0-C-1] c.s.w.m.service.MathServiceManager       : received message='{"id":14,"firstNumber":12222,"secondNumber":45,"result":12267,"action":"ADD"}'
```




