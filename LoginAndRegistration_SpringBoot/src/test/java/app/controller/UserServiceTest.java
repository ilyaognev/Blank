package app.controller;

import app.AbstractSpringTest;
import app.model.User;
import app.repository.UserRepository;
import app.service.UserService;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractSpringTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeClass
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Code executes before each test method");
    }

    @Test
    public void addUser() {
        assertThat(userService.allUsers().size(), is(3));
        userService.saveUser(new User("login", "123"));
        assertThat(userService.allUsers().size(), is(4));
    }

    @Test
    public void deleteUser() throws NullPointerException {
        assertThat(userService.allUsers().size(), is(3));
        userService.deleteUser(1L);
        assertThat(userService.allUsers().size(), is(2));
        assertThrows(NullPointerException.class, () -> userService.findUserById(1L));
    }

    @Test
    public void updateUser() {
        User userFromDB = userRepository.findByLogin("user");
        userFromDB.setName("New Name");
        userFromDB.setSurname("New Surname");
        userService.saveUser(userFromDB);
        assertAll("update user",
                () -> assertEquals(3, userService.allUsers().size()),
                () -> assertEquals("New Name", userRepository.findByLogin("user").getName()),
                () -> assertEquals("New Surname", userRepository.findByLogin("user").getSurname())
        );
    }

    @Test
    public void allUsers() {
        assertThat(userService.allUsers().size(), is(3));
    }

    @Test
    public void findUserById() {
        User user = userService.findUserById(1L);
        assertThat(user.getName(), is("Name"));
    }

    @Test
    public void findUserByIdNotFound() {
        assertThrows(NullPointerException.class, () -> userService.findUserById(10L));
    }

    @Test
    public void deleteUserNotFound() {
        assertThat(userService.deleteUser(10L), is(false));
    }

    @Test
    public void duplicateLoginCreate() {
    }
}