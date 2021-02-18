package be.pxl.smarthome.controllers;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.services.LightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/lights")
@Secured({"ROLE_ADULT"})
public class LightRestController {

    @Autowired
    LightService lightService;

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @GetMapping
    public ResponseEntity getLights() {
        List<Light> allLights = lightService.getAllLights();

        return new ResponseEntity(allLights, HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @GetMapping("/{lightId}")
    public ResponseEntity<Light> getLightById(@PathVariable int lightId){
        Light light = lightService.getLightById(lightId);
        return  new ResponseEntity(light, HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @PostMapping()
    public ResponseEntity postLight(@RequestBody Light light){
        if (light.getName() == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            lightService.saveLight(light);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{lightId}")
    public ResponseEntity deleteLight(@PathVariable int lightId){
        lightService.removeLight(lightId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured({"ROLE_MINOR", "ROLE_ADULT"})
    @PutMapping("/{lightId}")
    public ResponseEntity updateLight(@RequestBody Light light, @PathVariable int lightId) throws Exception {
        try{
            lightService.updateLight(lightId, light);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
