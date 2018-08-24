package com.bbc.push.notification.service.repository;

import java.util.ArrayList;

import com.bbc.push.notification.service.model.User;

public interface NotificationRepository {
	
	public ArrayList<User> getUsers();
	public void addUser(User user);
	public void setUsers(ArrayList<User> users);

}
