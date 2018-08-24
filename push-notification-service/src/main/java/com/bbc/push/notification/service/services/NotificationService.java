package com.bbc.push.notification.service.services;

import java.util.ArrayList;

import com.bbc.push.notification.service.model.Note;
import com.bbc.push.notification.service.model.User;

public interface NotificationService {

	public User createUser(User user);
	public ArrayList<User> getUsers();
	public User createPush(String username, Note note) throws Exception;
	public User findUser(String username);
	public boolean updateUserNumOfNotificationsPushed(User user);
	
}
