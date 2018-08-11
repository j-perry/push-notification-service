package com.bbc.push.notification.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushNotificationServiceController {
	
	@RequestMapping(value = "/",
		method = RequestMethod.GET)
	public String status() {
		return new String("Push Notification Service");
	}

}
