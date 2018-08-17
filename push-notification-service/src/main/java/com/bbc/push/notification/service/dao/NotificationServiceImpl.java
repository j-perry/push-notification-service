package com.bbc.push.notification.service.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bbc.push.notification.service.model.User;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private ArrayList<User> users;
	
	public NotificationServiceImpl() {
		this.users = new ArrayList<User>();
	}

	public User createUser(User user) {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		User user2 = new User();
		user2.setUsername(user.getUsername());
		user2.setAccessToken(user.getAccessToken());
		user2.setCreationTime(now.format(formatter));
		user2.setNumOfNotificationsPushed(0);
		
		this.users.add(user2);
		return user2;
	}

}
