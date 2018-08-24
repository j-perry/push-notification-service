# push-notification-service
Push Notification Service written in Java 8 and Spring Boot with Maven, and TDD/BDD for integration tests.

## Installation

To run the application, please use Eclipse. It may be started using ```mvn install clean spring-boot:run``` from a terminal inside it's project directory, or ```install clean spring-boot:run``` from inside Eclipse.

There are no databases used for this application - data is persisted locally throughout the lifespan of the application.

## Usage

There are three RESTful API endpoints. They may be accessed as follows:

- http://localhost:8080//PushNotificationService/create/user
- http://localhost:8080//PushNotificationService/users/all
- http://localhost:8080//PushNotificationService/create/push?username

## POST */create/user*

This endpoint creates a new user, passing into the body:

```
{
    "username": "<name>",
    "accessToken": "<access-token>"
}
```

This will return the following response:

```
{
    "username": "user", 
    "accessToken": "<access-token>",
    “creationTime”: “<YYYY-mm-dd HH:mm:ss>",
    “numOfNotificationsPushed”: 0
} 
```

## GET */users/all*

This endpoint returns a list of all users registered to the service.

This will return the following response:

```
[
    {
        "username": "name",
        "accessToken": “access-token",
        "creationTime": "24-08-2018 16:56:10",
        "numOfNotificationsPushed": 0
    },
    {
        "username": "name",
        "accessToken": “access-token",
        "creationTime": "24-08-2018 16:57:41",
        "numOfNotificationsPushed": 0
    }
]
```

## POST */create/push?username=value*

This endpoint creates a new push notification request to Pushbullet, passing in as a request parameter ```username```.

Before creating a new push notification to Pushbullet, it will look up an existing user on the service and then retrieve the corresponding access token. 

In addition to specifying a ```Content-Type``` of ```application/json``` in the Header, you will also need to add a header key/value of:

```
Access-Token: <access-token>
```

Where ```<access-token>``` corresponds with the access token sent with the submission of this exercise, or one you have created personally.

To create a new push notification to Pushbullet, specify the following body:

```
{
    "body": "<body of text>",
    "title": "<title>",
    "type": "<type>"
}
```

This will return the following response:

```
{
    "username": "name",
    "accessToken": "access-token",
    "creationTime": "24-08-2018 17:38:41",
    "numOfNotificationsPushed": 1
}
```

Where ```numOfNotificationsPushed``` increments after the push notification request has been made to Pushbullet, updating the corresponding user in the users list.

## Testing

The application may be tested both within Eclipse and using a third party API environment tool such as [Postman](https://www.getpostman.com/apps).

The application has a number of integration tests that may be executed within the application.