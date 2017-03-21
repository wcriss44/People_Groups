package com.example;

import com.example.entities.User;
import com.example.services.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleGroupsApiApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	UserRepository users;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void testaddUser() throws Exception {
		System.out.println(users.count());
		Assert.assertTrue(users.count() == 1);
		User user = new User();
		user.setUsername("Carl");
		user.setAddress("321 New Hampton");
		user.setEmail("carl@gmail.com");
		user.setSsn("hi");
		user.setWorking(false);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/user")
						.content(json)
						.contentType("application/json")
		);

		Assert.assertTrue(users.count() == 2);
	}

	@Test
	public void testGetUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(users.findAll());
		User user = new User();
		user.setUsername("Carl");
		user.setAddress("321 New Hampton");
		user.setEmail("carl@gmail.com");
		user.setSsn("hi");
		user.setWorking(false);
		ResultActions ra =  mockMvc.perform(
				MockMvcRequestBuilders.get("/user"))
				.andExpect(status().isOk())
				.andExpect(content().json(json));
	}

	@Test
	public void testUpdateUser() throws Exception {
		Assert.assertTrue(users.findOne(1).getUsername().equals("Mike"));
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setUsername("billy-bob");
		user.setAddress("321 New Hampton");
		user.setEmail("carl@gmail.com");
		user.setSsn("hi");
		user.setWorking(false);
		user.setId(1);
		String json = mapper.writeValueAsString(user);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/user")
						.content(json)
						.contentType("application/json")
		);

		Assert.assertTrue(users.findOne(1).getUsername().equals("billy-bob"));

	}

	@Test
	public void testdeleteUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(users.findAll());
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/user")
		);
		Assert.assertTrue(users.count() == 1);
	}

//	@Test
//	public void testGetUserId() throws Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(users.findAll());
//	}


	@Test
	public void contextLoads() {
	}

}
