package suren.campusmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import suren.campusmate.exception.UserNotFoundException;
import suren.campusmate.model.User;
import suren.campusmate.repository.UserRepo;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController
{
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/user")
    public User newUser(@RequestBody User newUser){
        System.out.println(newUser.getName());
        User savedUser = userRepo.save(newUser);
        if(savedUser != null) {
            System.out.println("User saved successfully: " + savedUser);
        } else {
            System.err.println("Failed to save user: " + newUser);
        }
        return savedUser;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/user/{id}"    )
    public User getUserById(@PathVariable Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful"+"["+user.getName()+","+user.getId()+"]");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


}
