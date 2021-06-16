package app.controller;

import app.AbstractSpringTest;
import app.model.User;
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

    @BeforeClass //@BeforeAll for Junit5. @BeforeClass for Junit4
    public static void globalSetUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only once");
    }

    @BeforeEach //@BeforeEach for Junit5. @Before for Junit4
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
        //ожидаем что после удаления одного юзера, их становится на одного меньше
        assertThrows(NullPointerException.class, () -> userService.findUserById(1L));
        //ожидаем что при обращении к удалённому id пользователя будет исключение. Это прописано в методе findUserById
    }
}