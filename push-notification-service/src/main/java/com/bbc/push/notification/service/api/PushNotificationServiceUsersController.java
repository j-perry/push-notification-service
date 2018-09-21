package com.bbc.push.notification.service.api;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbc.push.notification.service.model.User;
import com.bbc.push.notification.service.services.NotificationServiceImpl;

@RestController("/users")
public class PushNotificationServiceUsersController {
	
	@Autowired
	private NotificationServiceImpl notificationService;
	
	@RequestMapping(value = "/status",
		method = RequestMethod.GET)
	public String status() {
		return new String("Push Notification Service");
	}
	
	@RequestMapping(value = "/user",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			if (Objects.isNull(notificationService.findUser(user.getUsername()))) {
				notificationService.createUser(user);
				return new ResponseEntity<User>(user, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
			}
		} catch (Exception ex) {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ArrayList<User>> getAllUsers() throws Exception {
		ArrayList<User> users = notificationService.getUsers();
		
		if (users != null) {
			return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<ArrayList<User>>(users, HttpStatus.NOT_FOUND);
		}
	}

}