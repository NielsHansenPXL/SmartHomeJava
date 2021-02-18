package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import be.pxl.smarthome.repositories.LightDao;
import be.pxl.smarthome.repositories.RoomDao;
import be.pxl.smarthome.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class RoomService {
    @Autowired
    private RoomDao roomDao;

    @Autowired
    private LightDao lightDao;

    @Autowired
    private UserDao userDao;

     @Autowired
     UserService userService;

    private boolean testBool = false;

    public List<Room> getAllRooms(){
        return roomDao.findAll();
    }

    public Room getRoomById( int id){
        return roomDao.findById(id).get();
    }

    public void saveRoom(Room room){
        roomDao.save(room);
    }

    public void removeRoom(int id){
        roomDao.deleteById(id);
    }

    public void updateRoom(int id, Room room){
        Room changedRoom = roomDao.getOne(id);
        changedRoom.setName(room.getName());
        roomDao.save(changedRoom);
    }

    public void addLightToRoom(int roomId, int lightId) {
        Light light = lightDao.findById(lightId).get();
        Room room = getRoomById(roomId);
        room.addLight(light);
        roomDao.save(room);
    }

    public void setStateForRoom(int roomId, int onOff) throws Exception {
        if (onOff != 0 && onOff != 1){
            throw new Exception("State should be 0 or 1");
        } else {
            Room room = getRoomById(roomId);
            for (Light light: room.getLights()){
                if(onOff == 0){
                    light.setOn(false);
                } else {
                    light.setOn(true);
                }
            }
            roomDao.save(room);
        }
    }

    public void addUserToRoom(int userId, int roomId) throws Exception {
        if(checkUserAccess()){
            Room room = getRoomById(roomId);
            User user = userDao.getOne(userId);
            room.getAccesUsers().add(user);
            roomDao.save(room);
        } else {
            throw new Exception("No authenticated user");
        }
    }

    public void deleteUserFromRoom(int userId, int roomId) throws Exception {
        if(checkUserAccess()){
            Room room = getRoomById(roomId);
            User user = userDao.getOne(userId);
            room.getAccesUsers().remove(user);
            roomDao.save(room);
        } else {
            throw new Exception("No authenticated user");
        }
    }


    public boolean checkUserAccess(){
        if(testBool){
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        List<User> allUsers = userService.getAllUsers();

        for (User user : allUsers){
            if(user.getName().equals(currentUserName)){
                if(user.getRole().equals("ROLE_ADULT")){
                    return true;
                }
            }
        }
        return false;
    }

    public void setTestBoolean(){
        this.testBool = true;
    }

}
