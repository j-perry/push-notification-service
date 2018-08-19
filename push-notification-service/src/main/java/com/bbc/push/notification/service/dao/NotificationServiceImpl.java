package com.bbc.push.notification.service.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setAccessToken(user.getAccessToken());
		newUser.setCreationTime(now.format(formatter));
		newUser.setNumOfNotificationsPushed(0);
		
		this.users.add(newUser);
		return newUser;
	}

	public ArrayList<User> getAllUsers() {
		return users;
	}

}
