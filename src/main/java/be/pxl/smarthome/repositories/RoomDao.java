package be.pxl.smarthome.repositories;

import be.pxl.smarthome.models.Lightgroup;
import be.pxl.smarthome.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room, Integer> {
}
