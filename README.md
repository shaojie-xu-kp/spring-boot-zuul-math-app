# spring-boot-zuul-math-app

It is composed of two projects
1. api-gateway is the api gateway project will is client facing and acting as a load balancer as well
2. math-app is the math service application, a backend project, will be accessd by api-gateway

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

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system



