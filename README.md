# push-notification-service
Push Notification Service written in Java 8 and Spring Boot with Maven

## Installation

To run the application, please use Eclipse IDE. It may be started using **mvn install clean spring-boot:run** from a terminal or **install clean spring-boot:run** from inside Eclipse.

## Usage

There are three RESTful API endpoints. They may be accessed as follows:

- http://localhost:<port>//PushNotificationService/create/user
- http://localhost:<port>//PushNotificationService/users/all
- http://localhost:<port>//PushNotificationService/create/push?username

## Endpoint */create/user*

This endpoint creates a new user, passing into the body:

```
{
    "username": "<name>",
    "accessToken": "<access-token>"
}
```

This will return the following response and format:

```
{
    "username": "user", 
    "accessToken": "<access token>",
    “creationTime”: “<YYYY-mm-dd T HH:mm:ss>",
    “numOfNotificationsPushed”: 0
} 
```

## Endpoint */users/all*

This endpoint returns a list of all users registered to the service

## Endpoint */create/push?username*

This endpoint creates a new push request to Pushbullet, passing in as a request parameter 'username'.

To create a new push to Pushbullet, specify in the body the following parameters:

```
{
    "body": "<body of text>",
    "type": "<type>",
    "title": "<title>"
}
```