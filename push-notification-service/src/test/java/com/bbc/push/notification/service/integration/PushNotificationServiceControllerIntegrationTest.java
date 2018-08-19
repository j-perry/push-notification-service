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
	
	private String createUserEndpoint;
	
	private User userOne;
	private User userTwo;
	
	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		this.createUserEndpoint = base.toString() + "/create/user";
		
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		this.userOne = new User();
		userOne.setUsername("Jon");
		userOne.setAccessToken("abcd1234");
    	userOne.setCreationTime(now.format(formatter));
    	userOne.setNumOfNotificationsPushed(0);
    	
    	this.userTwo = new User();
    	userTwo.setUsername("Simon");
    	userTwo.setAccessToken("td925nn9a");
    	userTwo.setCreationTime(now.format(formatter));
    	userTwo.setNumOfNotificationsPushed(0);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		String accessToken = "abcd1234";
		String username = "Jon";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> entity = new HttpEntity<User>(userOne, headers);
		
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
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<User> userOneEntity = new HttpEntity<User>(userOne, headers);
		HttpEntity<User> userTwoEntity = new HttpEntity<User>(userTwo, headers);
		
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
