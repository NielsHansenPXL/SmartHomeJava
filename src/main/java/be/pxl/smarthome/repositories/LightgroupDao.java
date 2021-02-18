package be.pxl.smarthome.repositories;

import be.pxl.smarthome.models.Lightgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightgroupDao extends JpaRepository<Lightgroup, Integer> {


}
