package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.LightSender;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import be.pxl.smarthome.repositories.LightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LightService {
    @Autowired
    private LightDao lightDao;

    @Autowired
    RoomService roomService;

    @Autowired
    LightSender lightSender;

    private boolean testBool = false;

    @LogExecutionTime
    public List<Light> getAllLights(){
        return lightDao.findAll();
    }

    public Light getLightById( int id){
        return lightDao.findById(id).get();
    }

    public void saveLight(Light light) {
        lightDao.save(light);
    }

    public void removeLight(int id){
        lightDao.deleteById(id);
    }

    public void updateLight(int lightId, Light light) throws Exception {
        if(checkUserAccess(lightId)){
            Light changedLight = lightDao.getOne(lightId);
            if (light.getName() == null){
                changedLight.setOn(light.isOn());
                lightDao.save(changedLight);
                if(changedLight.isOn()){
                    lightSender.sendLight(light.getName() + " is on");
                } else{
                    lightSender.sendLight(light.getName() + " is off");
                }
            } else {
                changedLight.setName(light.getName());
                changedLight.setOn(light.isOn());
                lightDao.save(changedLight);
                if(changedLight.isOn()){
                    lightSender.sendLight(light.getName() + " is on");
                } else{
                    lightSender.sendLight(light.getName() + " is off");
                }
            }
        } else {
            throw new Exception("User not authorized");
        }
    }

    public boolean checkUserAccess(int lightId) {

        if(testBool){
            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        List<Room> allRooms = roomService.getAllRooms();
        for (Room room: allRooms) {
            for (Light searchLight: room.getLights()) {
                if(searchLight.getId() == lightId){
                    for(User user: room.getAccesUsers()){
                        if(user.getName().equals(currentUserName) || currentUserName.equals("admin")){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setTestBoolean(){
        this.testBool = true;
    }
}
