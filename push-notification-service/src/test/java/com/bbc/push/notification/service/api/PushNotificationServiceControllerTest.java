package com.bbc.push.notification.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bbc.push.notification.service.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * PushNotificationServiceControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PushNotificationServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private User user;
	
    @Before
    public void setUp() {
    	user = new User();
    }
    
    @After
    public void tearDown() {
    	
    }

    @Test
    public void testStatusEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
        		.accept(MediaType.APPLICATION_JSON_VALUE))
        		.andExpect(status().isOk())
        		.andExpect(content().string(equalTo("Push Notification Service")));
    }
    
    @Test
    public void testCreateUser() throws Exception {
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    	
    	user.setAccessToken("sdfdsfsd");
    	user.setCreationTime(now.format(formatter));
    	user.setUsername("Jon");
    	user.setNumOfNotificationsPushed(0);
    	
    	String json = mapper.writeValueAsString(user);
    	
    	mockMvc.perform(MockMvcRequestBuilders.post("/create/user")
    			.contentType(MediaType.APPLICATION_JSON_VALUE)
    			.content(json)
    			.accept(MediaType.APPLICATION_JSON_VALUE))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(json));
    }
    
}
