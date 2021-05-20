package app.dao;

import app.entity.User;

public interface UserDao {
    User findByLogin(String login);

    Boolean save(User user);
}
