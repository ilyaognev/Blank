package app.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
}
