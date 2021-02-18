package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.LightSender;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.repositories.LightDao;
import be.pxl.smarthome.repositories.RoomDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class LightServiceTest {

    @Mock
    private LightDao lightDao;

    @Mock
    private RoomDao roomDao;

    @Mock
    private LightSender lightSender;

    @InjectMocks
    RoomService roomService;

    @InjectMocks
    LightService lightService;

    @Mock
    private LightService lightServiceMock;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CheckIfGetAllLightsReturnsNull(){
        //Arrange
        when(lightDao.findAll()).thenReturn(null);

        //Act & Assert
        Assertions.assertNull(lightService.getAllLights());
    }

    @Test
    public void CheckIfGetAllLightsReturnsLights(){
        //Arrange
        Light lightMock1 = new Light();
        Light lightMock2 = new Light();
        //lightMock1.setName("mock1");
        //lightMock2.setName("mock2");

        List<Light> mockLights = new ArrayList<>();
        mockLights.add(lightMock1);
        mockLights.add(lightMock2);

        when(lightDao.findAll()).thenReturn(mockLights);

        //Act
        List<Light> assertLigths = lightService.getAllLights();

        //Assert
        Assertions.assertEquals(2, assertLigths.size());
    }

    @Test
    public void CheckIfGetLightByIdReturnsLight(){
        //Arrange
        Light mockLight = new Light();
        mockLight.setId(2);
        when(lightDao.findById(anyInt())).thenReturn(java.util.Optional.of(mockLight));

        //Act
        Light resultLight =  lightService.getLightById(2);

        //Arrange
        Assertions.assertEquals(mockLight.getId(), resultLight.getId());
    }

    @Test
    public void GetLightByIdIsNull(){
        //Arrange
        when(lightDao.findById(anyInt())).thenReturn(null);
        int id = 50;

        //Act & Assert
        Assertions.assertThrows(Exception.class, () -> lightService.getLightById(id));
    }

    @Test
    public void CheckIfLightIsSaved(){
        //Arrange
        Light light = new Light();

        //Act
        lightService.saveLight(light);

        //Assert
        verify(lightDao, times(1)).save(light);
    }

    @Test
    public void CheckIfLightIsDeleted(){
        //Arrange
        Light light = new Light();
        light.setId(2);

        //Act
        lightService.removeLight(light.getId());

        //Assert
        verify(lightDao, times(1)).deleteById(light.getId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADULT")
    public void CheckIfLightUpdates() throws Exception {
        //arrange
        Light light =new Light();
        light.setName("original");


        Light newLight = new Light();
        newLight.setName("new");
        newLight.setId(1);

        List<Light> lights = new ArrayList<>();
        lights.add(light);
        lights.add(newLight);

        List<Room> rooms = new ArrayList<>();

        Room room = new Room();
        room.setLights(lights);

        rooms.add(room);

        lightService.setTestBoolean();


        when(lightDao.findById(anyInt())).thenReturn(java.util.Optional.of(light));
        when(lightDao.getOne(anyInt())).thenReturn(light);
        when(roomService.getAllRooms()).thenReturn(rooms);
        when(roomDao.findAll()).thenReturn(rooms);

        //act
        lightService.updateLight(newLight.getId(), newLight);

        //assert
        Assertions.assertEquals(light.getName(), newLight.getName());
    }
}
