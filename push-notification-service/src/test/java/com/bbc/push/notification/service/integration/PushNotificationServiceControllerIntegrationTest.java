package com.bbc.push.notification.service.integration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbc.push.notification.service.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PushNotificationServiceControllerIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private URL base;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}
	
	@Test
	public void testCreateUser() throws Exception {
		String accessToken = "abcd1234";
		String username = "Jon";
		
		User user = new User();
		user.setUsername(username);
		user.setAccessToken(accessToken);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		
		String createUserEndpoint = base.toString() + "/create/user";
		ResponseEntity<User> response = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, entity, User.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getAccessToken(), equalTo(accessToken));
		assertThat(response.getBody().getUsername(), equalTo(username));
	}
	
	@Test
	public void testGetAllUsers() throws Exception {
		final int length = 2;
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        User user1 = new User();
    	user1.setUsername("Alfie");
    	user1.setAccessToken("s235svvf7");
    	user1.setCreationTime(now.format(formatter));
    	user1.setNumOfNotificationsPushed(0);
    	
    	User user2 = new User();
    	user2.setUsername("Simon");
    	user2.setAccessToken("td925nn9a");
    	user2.setCreationTime(now.format(formatter));
    	user2.setNumOfNotificationsPushed(0);
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> userOneEntity = new HttpEntity<User>(user1, headers);
		HttpEntity<User> userTwoEntity = new HttpEntity<User>(user2, headers);
		
    	String createUserEndpoint = base.toString() + "/create/user";
    	ResponseEntity<User> postResponseOne = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, userOneEntity, User.class);
		assertThat(postResponseOne.getStatusCode(), equalTo(HttpStatus.CREATED));

    	ResponseEntity<User> postResponseTwo = restTemplate.exchange(createUserEndpoint, HttpMethod.POST, userTwoEntity, User.class);
		assertThat(postResponseTwo.getStatusCode(), equalTo(HttpStatus.CREATED));
    	
		headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> allUsersEntity = new HttpEntity<String>("parameters", headers);
		
		String getAllUsersEndpoint = base.toString() + "/users/all";
		ResponseEntity<User[]> response = restTemplate.exchange(getAllUsersEndpoint, HttpMethod.GET, allUsersEntity, User[].class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().length, equalTo(length));
	}

}
