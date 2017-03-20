package com.example.Controllers;


import com.example.entities.User;
import com.example.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.rmi.server.ExportException;
import java.util.List;

@RestController
public class PeopleGroupsApiController {
    @Autowired
    UserRepository users;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> readUser() {
        return (List<User>) users.findAll();
    }
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public void createUser(@RequestBody User user) {
        users.save(user);
    }
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user) throws Exception{

        if (users.exists(user.getId())){
            users.save(user);
        } else {
            throw new ExportException("User not found. Check user ID.");
        }

    }
    @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id) {
        users.delete(id);
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id) {
        return users.findOne(id);
    }


    @PostConstruct
    public void init(){
        if (users.count() == 0){
            User user = new User();
            user.setAddress("123 Testville");
            user.setEmail("me@you.com");
            user.setUsername("Mike");
            user.setSsn("333-444-5522");
            user.setWorking(true);
            users.save(user);
        }
    }
}
