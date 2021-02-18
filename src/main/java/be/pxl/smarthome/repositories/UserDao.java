package be.pxl.smarthome.repositories;

import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
}
