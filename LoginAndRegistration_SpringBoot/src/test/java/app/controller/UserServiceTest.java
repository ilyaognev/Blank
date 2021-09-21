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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThat(userService.allUsers().size(), is(3));
        assertThat(userRepository.findByLogin("user").getName(), is("New Name"));
        assertThat(userRepository.findByLogin("user").getSurname(), is("New Surname"));
    }

    @Test
    public void allUsers() {
    }

    @Test
    public void findUserById() {
    }

    @Test
    public void findUserByIdNotFound() {
    }

    @Test
    public void deleteUserNotFound() {
    }

    @Test
    public void duplicateLoginCreate() {
    }
}