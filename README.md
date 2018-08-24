# push-notification-service
Push Notification Service written in Java 8 and Spring Boot with Maven

## Installation

To run the application, please use Eclipse IDE. It may be started using **mvn install clean spring-boot:run** from a terminal or **install clean spring-boot:run** from inside Eclipse.

## Usage

There are three RESTful API endpoints. They may be accessed as follows:

- http://localhost:<port>//PushNotificationService/create/user
- http://localhost:<port>//PushNotificationService/users/all
- http://localhost:<port>//PushNotificationService/create/push?username

## POST */create/user*

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
    "accessToken": "<access-token>",
    “creationTime”: “<YYYY-mm-ddTHH:mm:ss>",
    “numOfNotificationsPushed”: 0
} 
```

## GET */users/all*

This endpoint returns a list of all users registered to the service

## POST */create/push?username=value*

This endpoint creates a new push request to Pushbullet, passing in as a request parameter 'username'.

In addition to specifying a ```Content-Type``` of ```'application/json'``` in the Header, you will also need to add a header key/value of:

```
Access-Token: <accessToken>
```

Where ```<accessToken>``` corresponds with the access token sent with the submission of this exercise, or one you have created personally.

To create a new push notification to Pushbullet, specify in the body the following body:

```
{
    "body": "<body of text>",
    "type": "<type>",
    "title": "<title>"
}
```