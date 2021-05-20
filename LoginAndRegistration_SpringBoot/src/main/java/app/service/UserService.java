package app.service;

import app.entity.User;

public interface UserService {
    String login(User user);

    String registration(User user);
}
