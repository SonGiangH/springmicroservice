package com.restaishopme.controller;

import com.restaishopme.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    // create list fix User empty (instead of connecting database)
    List<User> users = new ArrayList<>();

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        users.add(user);
        return user;
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUser() {
        return users;
    }

    // Delete User by Id
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam(name = "id") int id) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId() == id) {
                users.remove(i);
                break;
            }
        }
    }
    // Update User by Id
    @PutMapping("/user")
    public void updateUser(@RequestBody User user) {
        for (int i=0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
    }
}
