package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import be.pxl.smarthome.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public User getUserById( int id){
        return userDao.findById(id).get();
    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public void removeUser(int id) {userDao.deleteById(id);}

    public void updateUser(int id, User user){
        User changedUser = userDao.getOne(id);
        changedUser.setName(user.getName());
        userDao.save(changedUser);
    }
}
