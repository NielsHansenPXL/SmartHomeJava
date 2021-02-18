package be.pxl.smarthome.controllers;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.services.LightService;
import be.pxl.smarthome.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "/rooms")
@Secured({"ROLE_ADULT"})
public class RoomRestController {
    @Autowired
    RoomService roomService;

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @GetMapping
    public ResponseEntity getRooms() {
        List<Room> allRooms = roomService.getAllRooms();
        return new ResponseEntity(allRooms, HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable int roomId){
        Room room = roomService.getRoomById(roomId);
        System.out.println(room);
        return new ResponseEntity(room, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity postRoom(@RequestBody Room room){
        if (room.getName() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            roomService.saveRoom(room);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity deleteRoom(@PathVariable int roomId){
        roomService.removeRoom(roomId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity updateRoom(@RequestBody Room room, @PathVariable int roomId){
        roomService.updateRoom(roomId, room);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @PutMapping("/{roomId}/{lightId}")
    public ResponseEntity setLightForRoom(@PathVariable int roomId, @PathVariable int lightId){
        try {
            roomService.addLightToRoom(roomId, lightId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @PutMapping("/alllights/{roomId}/{onOff}")
    public ResponseEntity setAllLightsForRoom(@PathVariable int roomId, @PathVariable int onOff) throws Exception {
        try{
            roomService.setStateForRoom(roomId, onOff);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/adduser/{roomId}/{userId}")
    public ResponseEntity addUserToRoom(@PathVariable int roomId, @PathVariable int userId){
        try{
            roomService.addUserToRoom(userId, roomId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/removeuser/{roomId}/{userId}")
    public ResponseEntity deleteUserFromRoom(@PathVariable int roomId, @PathVariable int userId){
        try{
            roomService.deleteUserFromRoom(userId, roomId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
