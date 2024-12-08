package com.example.springemailexample.controller;

import com.example.springemailexample.entity.UserAdmin;
import com.example.springemailexample.services.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAdminController {

    @Autowired
    UserAdminService userAdminService;

    @PostMapping
    public ResponseEntity<UserAdmin> createUser(@RequestBody UserAdmin user) {
        UserAdmin createdUser = userAdminService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserAdmin>> getAllUsers() {
        return ResponseEntity.ok(userAdminService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAdmin> getUserById(@PathVariable Long id) {
        return userAdminService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAdmin> updateUser(@PathVariable Long id, @RequestBody UserAdmin user) {
        return ResponseEntity.ok(userAdminService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userAdminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }




}

