package com.bbc.push.notification.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbc.push.notification.service.dao.NotificationServiceImpl;
import com.bbc.push.notification.service.model.User;

@RestController
public class PushNotificationServiceController {
	
	@Autowired
	private NotificationServiceImpl notificationService;
	
	@RequestMapping(value = "/",
		method = RequestMethod.GET)
	public String status() {
		return new String("Push Notification Service");
	}
	
	@RequestMapping(value = "/create/user",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
		User responseUser = notificationService.createUser(user);
		
		if (responseUser != null) {
			return new ResponseEntity<User>(responseUser, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(responseUser, HttpStatus.NOT_FOUND);
		}
	}

}
