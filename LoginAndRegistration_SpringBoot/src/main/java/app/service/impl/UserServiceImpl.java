package app.service.impl;

import app.dao.impl.UserDaoImpl;
import app.entity.User;
import app.service.UserService;

public class UserServiceImpl implements UserService {
    UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public String login(User user) {
        User findUser = userDao.findByLogin(user.getLogin());
        if (findUser != null) {
            if (user.getPassword().equals(findUser.getPassword())) {
                return "life is beautiful," + "your Id: " + findUser.getId();
            }
        }
        return "do not give up";
    }

    @Override
    public String registration(User user) {
        User findUser = userDao.findByLogin(user.getLogin());
        if (findUser == null) {
            userDao.save(user);
            return "life is beautiful";
        }
        return "this login is not available";
    }
}
