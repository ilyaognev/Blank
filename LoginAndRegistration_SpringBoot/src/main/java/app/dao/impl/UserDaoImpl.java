package app.dao.impl;

import app.connection.ConnectionManager;
import app.dao.UserDao;
import app.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByLogin(String login) {
        ConnectionManager cm = new ConnectionManager();
        Connection con = cm.getConnection();
        User user = null;
        if (con != null) {
            try {
                PreparedStatement pr = con.prepareStatement("SELECT * FROM USERS where LOGIN=?");
                pr.setString(1, login);
                ResultSet resultSet = pr.executeQuery();//return sql result
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("ID"));
                    user.setName(resultSet.getString("NAME"));
                    user.setSurname(resultSet.getString("SURNAME"));
                    user.setLogin(login);
                    user.setPassword(resultSet.getString("PASSWORD"));
                    return user;
                }
                pr.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public Boolean save(User user) {
        ConnectionManager cm = new ConnectionManager();
        Connection con = cm.getConnection();

        if (con != null) {
            try {
                PreparedStatement pr = con.prepareStatement("INSERT INTO USERS(ID, NAME, SURNAME, LOGIN, PASSWORD) VALUES (?, ?, ?, ?, ?)");
                pr.setString(1, null);
                pr.setString(2, user.getName());
                pr.setString(3, user.getSurname());
                pr.setString(4, user.getLogin());
                pr.setString(5, user.getPassword());
                pr.execute();
                pr.close();
                con.close();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
