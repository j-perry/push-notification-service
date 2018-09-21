package com.bbc.push.notification.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbc.push.notification.service.model.Note;
import com.bbc.push.notification.service.model.User;
import com.bbc.push.notification.service.services.NotificationServiceImpl;

@RestController("/notifications")
public class PushNotificationServiceNotificationsController {
	
	@Autowired
	private NotificationServiceImpl notificationService;

	@RequestMapping(value = "/create/push",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<User> createPost(@RequestParam("username") String username,
		@RequestBody Note note) throws Exception {
		return notificationService.createPush(username, note);
	}
	
}
