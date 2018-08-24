package com.bbc.push.notification.service.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bbc.push.notification.service.model.Note;
import com.bbc.push.notification.service.model.User;
import com.bbc.push.notification.service.repository.NotificationRepositoryImpl;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private NotificationRepositoryImpl notificationRepositoryImpl;
	
	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	public NotificationServiceImpl() {
		this.notificationRepositoryImpl = new NotificationRepositoryImpl();
	}

	public User createUser(User user) {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setAccessToken(user.getAccessToken());
		newUser.setCreationTime(now.format(formatter));
		newUser.setNumOfNotificationsPushed(0);
		
		notificationRepositoryImpl.addUser(newUser);
		return newUser;
	}

	public User createPush(String username, Note note) throws Exception {
		final String pushBulletCreatePostEndpoint = "https://api.pushbullet.com/v2/pushes";
		User user = findUser(username);
		
		if (user == null) {
			return null;
		}
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Token", user.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Note> entity = new HttpEntity<Note>(note, headers);
		log.info("Response Headers: " + entity.getHeaders().toString());		
		log.info("Access-Token: " + user.getAccessToken());
		
		ResponseEntity<User> response = restTemplate.exchange(pushBulletCreatePostEndpoint, HttpMethod.POST, entity, User.class);
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			updateUserNumOfNotificationsPushed(user);
		} else {
			return null;
		}
		
		return response.getBody();
	}

	/**
	 * Returns a found user
	 */
	public User findUser(String username) {
		return getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
	}

	/**
	 * Looks up our user, updates number of notifications pushed, 
	 * then updates list of all users
	 */
	@Override
	public boolean updateUserNumOfNotificationsPushed(User user) {
		boolean updated = false;
		User updateUser = findUser(user.getUsername());
		int noNotificationsPushed = updateUser.getNumOfNotificationsPushed();
		updateUser.setNumOfNotificationsPushed(noNotificationsPushed++);
		
		// update all users including modified user's no. notifications pushed
		ArrayList<User> updatedUsers = new ArrayList<User>(getUsers().stream().map(u -> {
			User newUser = new User();
			newUser.setAccessToken(u.getAccessToken());
			newUser.setCreationTime(u.getCreationTime());
			newUser.setUsername(u.getUsername());
			
			if(u.getUsername().equals(user.getUsername())) {
				newUser.setNumOfNotificationsPushed(updateUser.getNumOfNotificationsPushed());
			} else {
				newUser.setNumOfNotificationsPushed(u.getNumOfNotificationsPushed());
			}
			
			return newUser;
		}).collect(Collectors.toList()));
		
		if (!updatedUsers.isEmpty()) {			
			notificationRepositoryImpl.setUsers(updatedUsers);
			updated = true;
		}
		
		return updated;
	}
	
	public ArrayList<User> getUsers() {
		return notificationRepositoryImpl.getUsers();
	}
	
}
