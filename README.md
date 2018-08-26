# push-notification-service
Push Notification Service written in Java 8, Spring Boot, Maven, Tomcat and TDD/BDD using JUnit 4 and Hamcrest for integration tests.

## Installation

To run the application, please use Eclipse. It may be started using ```mvn install clean spring-boot:run``` from a terminal inside it's project directory, or ```install clean spring-boot:run``` from inside Eclipse.

Before running the application, the integration tests will require a valid [Pushbullet](https://www.pushbullet.com/) Access Token. Replace ```.setAccessToken("accessToken")``` on line 61 in ```PushNotificationServiceControllerIntegrationTest.java``` with the Access Token sent, or one created inside Pushbullet itself.

There are no databases used for this application - data is created and read locally throughout the lifespan of the server.

## Usage

There are three RESTful API endpoints. They may be accessed as follows:

- http://localhost:8080//PushNotificationService/create/user
- http://localhost:8080//PushNotificationService/users/all
- http://localhost:8080//PushNotificationService/create/push?username

Before use, you will need to create a [Pushbullet](https://www.pushbullet.com/) account and generate an Access Token. Once registered, you will need to install either the [Chrome extension](https://chrome.google.com/webstore/detail/pushbullet/chlffgpmiacpedhhbkiomidkjlcfhogd?hl=en) or install the iOS or Android app to view push notifications.

## POST */create/user*

This endpoint creates a new user, passing into the body:

```
{
    "username": "<user>",
    "accessToken": "<access-token>"
}
```

This will return the following response:

```
{
    "username": "<user>", 
    "accessToken": "<access-token>",
    “creationTime”: “<YYYY-mm-ddTHH:mm:ss>",
    “numOfNotificationsPushed”: 0
} 
```

Duplicate entries cannot be made. If you try to create a duplicate user entry, you will receive the following ```409 - Conflict``` response:

```
{
    "username": "<user>", 
    "accessToken": "<access-token>",
    “creationTime”: null,
    “numOfNotificationsPushed”: 0
} 
```

## GET */users/all*

This endpoint returns a list of all users registered to the service.

This will return the following response:

```
[
    {
        "username": "<user>",
        "accessToken": “<access-token>",
        "creationTime": "<YYYY-mm-ddTHH:mm:ss>",
        "numOfNotificationsPushed": 0
    },
    {
        "username": "<user>",
        "accessToken": “<access-token>",
        "creationTime": "<YYYY-mm-ddTHH:mm:ss>",
        "numOfNotificationsPushed": 0
    }
]
```

## POST */create/push?username=value*

This endpoint creates a new push notification request to Pushbullet, passing in as a request parameter ```username```.

Before creating a new push notification to Pushbullet, it will look up an existing user on the service and then retrieve the corresponding access token. 

You will need to specify ```Content-Type``` of ```application/json``` in the Headers section, if testing using [Postman](https://www.getpostman.com/apps).

To create a new push notification to Pushbullet, specify the following body:

```
{
    "body": "<body of text>",
    "title": "<title>",
    "type": "<type>"
}
```

Where it specifies the ```"type"```, you will want to specify ```"note"```.

This will return the following response:

```
{
    "username": "<user>",
    "accessToken": "<access-token>",
    "creationTime": "<YYYY-mm-ddTHH:mm:ss>",
    "numOfNotificationsPushed": 1
}
```

Where ```numOfNotificationsPushed``` increments after the push notification request has been made to Pushbullet, updating the corresponding user in the users list.

## Testing

The application may be tested both within Eclipse and using a third party API environment tool such as [Postman](https://www.getpostman.com/apps).

The application has a number of unit and integration tests that may be executed within the application that have been written using TDD/BDD in JUnit 4 and Hamcrest.