package be.pxl.smarthome.controllers;

import be.pxl.smarthome.models.Lightgroup;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.services.GroupService;
import be.pxl.smarthome.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/lightgroup")
@Secured({"ROLE_ADULT"})
public class LightgroupRestController {
    @Autowired
    GroupService groupService;

    @Secured({"ROLE_MINOR"})
    @GetMapping
    public ResponseEntity getGroups() {
        List<Lightgroup> allGroups = groupService.getAllGroups();

        return new ResponseEntity(allGroups, HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR"})
    @GetMapping("/{groupId}")
    public ResponseEntity<Lightgroup> getGroupById(@PathVariable int groupId) {
        Lightgroup group = groupService.getGroupById(groupId);
        System.out.println(group);
        return new ResponseEntity(group, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity postGroup(@RequestBody Lightgroup group) {
        if (group.getName() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            groupService.saveRoom(group);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity deleteGroup(@PathVariable int groupId) {
        groupService.removeGroup(groupId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity updateGroup(@RequestBody Lightgroup group, @PathVariable int groupId) {
        groupService.updateGroup(groupId, group);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{groupId}/{lightId}")
    public ResponseEntity setLightForGroup(@PathVariable int groupId, @PathVariable int lightId) {
        try {
            groupService.addLightToGroup(groupId, groupId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}