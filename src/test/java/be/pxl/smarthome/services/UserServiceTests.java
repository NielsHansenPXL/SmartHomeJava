package be.pxl.smarthome.services;

import be.pxl.smarthome.models.Room;
import be.pxl.smarthome.models.User;
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

public class UserServiceTests {
    @Mock
    private UserDao userDao;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void init() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CheckIfGetAllUsersReturnsNull(){
        //Arrange
        when(userDao.findAll()).thenReturn(null);

        //Act & Assert
        Assertions.assertNull(userService.getAllUsers());
    }

    @Test
    public void CheckIfGetAllUsersReturnsUsers(){
        //Arrange
        User userMock1 = new User();
        User userMock2 = new User();

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(userMock1);
        mockUsers.add(userMock2);

        when(userDao.findAll()).thenReturn(mockUsers);

        //Act
        List<User> assertUsers = userService.getAllUsers();

        //Assert
        Assertions.assertEquals(2, assertUsers.size());
    }

    @Test
    public void CheckIfGetUserByIdReturnsUser(){
        //Arrange
        User mockUser = new User();
        mockUser.setId(3);
        when(userDao.findById(anyInt())).thenReturn(java.util.Optional.of(mockUser));

        //Act
        User resultUser =  userService.getUserById(mockUser.getId());

        //Arrange
        Assertions.assertEquals(mockUser.getId(), resultUser.getId());
        verify(userDao, times(1)).findById(mockUser.getId());
        Assertions.assertNotNull(resultUser);
    }

    @Test
    public void CheckIfUserIsSaved(){
        //Arrange
        User user = new User();

        //Act
        userService.saveUser(user);

        //Assert
        verify(userDao, times(1)).save(user);
    }

    @Test
    public void CheckIfUserIsDeleted(){
        //Arrange
        User user = new User();
        user.setId(2);

        //Act
        userService.removeUser(user.getId());

        //Assert
        verify(userDao, times(1)).deleteById(user.getId());
    }

    @Test
    public void CheckIfUserIsUpdated(){
        //arrange
        User oldUser = new User();
        oldUser.setName("old user");
        oldUser.setId(1);

        User newUser = new User();
        newUser.setName("new user");

        when(userDao.getOne(anyInt())).thenReturn(oldUser);

        //act
        userService.updateUser(oldUser.getId(), newUser);

        //assert
        Assertions.assertEquals(oldUser.getName(), newUser.getName());
        verify(userDao, times(1)).save(oldUser);
    }
}
