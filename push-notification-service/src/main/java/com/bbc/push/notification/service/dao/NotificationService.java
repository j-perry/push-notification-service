package com.bbc.push.notification.service.dao;

import java.util.ArrayList;

import com.bbc.push.notification.service.model.User;

public interface NotificationService {

	public User createUser(User user);

	public ArrayList<User> getAllUsers();
	
}
