package be.pxl.smarthome.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class roomModelAddLightTest {
    @Test
    public void CheckIfLightGetsAdded(){
        //Arrange
        Room room = new Room();
        Light light = new Light();

        //Act
        room.addLight(light);

        //Assert
        Assertions.assertNotNull(room.getLights());
    }
}
