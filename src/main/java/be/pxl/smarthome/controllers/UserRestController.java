package be.pxl.smarthome.controllers;

import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import be.pxl.smarthome.services.GroupService;
import be.pxl.smarthome.services.RoomService;
import be.pxl.smarthome.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Secured({"ROLE_ADULT"})
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    GroupService groupService;

    @GetMapping
    public ResponseEntity getUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUseryId(@PathVariable int userId){
        User user = userService.getUserById(userId);
        System.out.println(user);
        return new ResponseEntity(user, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity postUser(@RequestBody User user){
        if (user.getName() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            userService.saveUser(user);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId){
        userService.removeUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateRoom(@RequestBody User user, @PathVariable int userId){
        userService.updateUser(userId, user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
