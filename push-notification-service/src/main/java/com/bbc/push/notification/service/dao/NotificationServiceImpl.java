package com.bbc.push.notification.service.dao;

import java.util.ArrayList;
import java.util.Date;

import com.bbc.push.notification.service.model.User;

public class NotificationServiceImpl implements NotificationService {
	
	private ArrayList<User> users;
	
	public NotificationServiceImpl() {
		this.users = new ArrayList<User>();
	}

	public User createUser(User user) {
		User user2 = new User();
		user2.setUsername(user.getUsername());
		user2.setAccessToken(user.getAccessToken());
		user2.setCreationTime(new Date());
		user2.setNumOfNotificationsPushed(0);
		
		this.users.add(user2);
		return user2;
	}

}
