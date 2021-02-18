package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.Lightgroup;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.repositories.LightDao;
import be.pxl.smarthome.repositories.LightgroupDao;
import be.pxl.smarthome.repositories.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private LightgroupDao lightgroupDao;

    @Autowired
    private LightDao lightDao;

    public List<Lightgroup> getAllGroups(){
        return lightgroupDao.findAll();
    }

    public Lightgroup getGroupById( int id){
        return lightgroupDao.findById(id).get();
    }

    public void saveRoom(Lightgroup group){
        lightgroupDao.save(group);
    }

    public void removeGroup(int id){
        lightgroupDao.deleteById(id);
    }

    public void updateGroup(int id, Lightgroup group){
        Lightgroup changedGroup = lightgroupDao.getOne(id);
        changedGroup.setName(group.getName());
        lightgroupDao.save(changedGroup);
    }

    public void addLightToGroup(int groupId, int lightId) {
        Light light = lightDao.findById(lightId).get();
        Lightgroup group = getGroupById(groupId);
        group.addLight(light);
        lightgroupDao.save(group);
    }
}
