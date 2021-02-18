package be.pxl.smarthome.repositories;

import be.pxl.smarthome.models.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightDao extends JpaRepository<Light, Integer> {

}