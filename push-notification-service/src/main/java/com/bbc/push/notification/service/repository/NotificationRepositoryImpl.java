package com.bbc.push.notification.service.repository;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bbc.push.notification.service.model.User;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
	
	private ArrayList<User> users;
	
	public NotificationRepositoryImpl() {
		this.users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

}
