package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Light;
import be.pxl.smarthome.models.LightSender;
import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
import be.pxl.smarthome.repositories.LightDao;
import be.pxl.smarthome.repositories.RoomDao;
import be.pxl.smarthome.repositories.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RoomServiceTest {
    @Mock
    private LightDao lightDao;

    @Mock
    private RoomDao roomDao;

    @Mock
    private UserDao userDao;

    @Mock
    private LightSender lightSender;

    @InjectMocks
    RoomService roomService;

    @InjectMocks
    LightService lightService;

    @InjectMocks
    UserService userService;

    @Mock
    private LightService lightServiceMock;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CheckIfGetAllRoomsReturnsNull(){
        //Arrange
        when(roomDao.findAll()).thenReturn(null);

        //Act & Assert
        Assertions.assertNull(roomService.getAllRooms());
    }

    @Test
    public void CheckIfGetAllRoomsReturnsRooms(){
        //Arrange
        Room roomMock1 = new Room();
        Room roomMock2 = new Room();

        List<Room> mockRooms = new ArrayList<>();
        mockRooms.add(roomMock1);
        mockRooms.add(roomMock2);

        when(roomDao.findAll()).thenReturn(mockRooms);

        //Act
        List<Room> assertRooms = roomService.getAllRooms();

        //Assert
        Assertions.assertEquals(2, assertRooms.size());
    }

    @Test
    public void CheckIfGetRoomByIdReturnsRoom(){
        //Arrange
        Room mockRoom = new Room();
        mockRoom.setId(3);
        when(roomDao.findById(anyInt())).thenReturn(java.util.Optional.of(mockRoom));

        //Act
        Room resultRoom =  roomService.getRoomById(mockRoom.getId());

        //Arrange
        Assertions.assertEquals(mockRoom.getId(), resultRoom.getId());
        verify(roomDao, times(1)).findById(mockRoom.getId());
        Assertions.assertNotNull(resultRoom);
    }

    @Test
    public void CheckIfRoomIsSaved(){
        //Arrange
        Room room = new Room();

        //Act
        roomService.saveRoom(room);

        //Assert
        verify(roomDao, times(1)).save(room);
    }

    @Test
    public void CheckIfRoomIsDeleted(){
        //Arrange
        Room room = new Room();
        room.setId(2);

        //Act
        roomService.removeRoom(room.getId());

        //Assert
        verify(roomDao, times(1)).deleteById(room.getId());
    }

    @Test
    public void CheckIfRoomIsUpdated(){
        //arrange
        Room oldRoom = new Room();
        oldRoom.setName("old room");
        oldRoom.setId(1);

        Room newRoom = new Room();
        newRoom.setName("new room");

        when(roomDao.getOne(anyInt())).thenReturn(oldRoom);

        //act
        roomService.updateRoom(oldRoom.getId(), newRoom);

        //assert
        Assertions.assertEquals(oldRoom.getName(), newRoom.getName());
        verify(roomDao, times(1)).save(oldRoom);
    }

    @Test
    public void CheckIfLightGetsAddedToRoom(){
        //arrange
        Light light = new Light();
        light.setId(1);
        light.setName("test lamp");

        Room room = new Room();
        room.setId(1);
        room.setName("test room");

        when(lightDao.findById(anyInt())).thenReturn(java.util.Optional.of(light));
        when(roomDao.findById(anyInt())).thenReturn(java.util.Optional.of(room));

        //act
        roomService.addLightToRoom(room.getId(), light.getId());

        //assert
        Assertions.assertNotNull(room.getLights());
        Assertions.assertEquals(room.getLights().size(), 1);
        verify(roomDao, times(1)).save(room);
    }

    @Test
    public void CheckIfRoomStateChanges() throws Exception {
        Room room = new Room();
        room.setId(1);

        Light light1 = new Light();
        Light light2 = new Light();

        light1.setOn(false);
        light2.setOn(false);

        room.addLight(light1);
        room.addLight(light2);

        when(roomDao.findById(anyInt())).thenReturn(java.util.Optional.of(room));

        roomService.setStateForRoom(room.getId(), 1);

        Assertions.assertTrue(light1.isOn());
        Assertions.assertTrue(light1.isOn());
    }

    @Test
    public void UserAddToRoomCheck() throws Exception {
        Room room = new Room();
        room.setId(1);

        User user = new User();
        user.setId(1);

        roomService.setTestBoolean();
        when(roomDao.findById(anyInt())).thenReturn(java.util.Optional.of(room));
        when(userDao.getOne(anyInt())).thenReturn(user);

        roomService.addUserToRoom(room.getId(), user.getId());

        Assertions.assertEquals(room.getAccesUsers().size(), 1);
    }

    @Test
    public void CheckIfUserRemovesFromRoom() throws Exception {
        //arrange
        Room room = new Room();
        room.setId(1);

        User user = new User();
        user.setId(1);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        room.setAccesUsers(userList);

        roomService.setTestBoolean();
        when(roomDao.findById(anyInt())).thenReturn(java.util.Optional.of(room));
        when(userDao.getOne(anyInt())).thenReturn(user);

        //act
        roomService.deleteUserFromRoom(user.getId(), room.getId());

        //assert
        Assertions.assertEquals(room.getAccesUsers().size(), 0);
    }
}
