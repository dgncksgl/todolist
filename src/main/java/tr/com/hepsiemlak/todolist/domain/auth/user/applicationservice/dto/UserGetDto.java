package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto;

import tr.com.hepsiemlak.todolist.domain.auth.user.User;

public class UserGetDto {

    private String id;

    private String username;

    private String password;

    private Boolean active;

    private String name;

    private String surname;

    private String email;

    private String gsm;

    private UserGetDto() {
    }


    public String getId() {
        return id;
    }

    public UserGetDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserGetDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserGetDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public UserGetDto setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserGetDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserGetDto setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserGetDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGsm() {
        return gsm;
    }

    public UserGetDto setGsm(String gsm) {
        this.gsm = gsm;
        return this;
    }

    public static UserGetDto convertToUserGetDtoFromUser(User user) {
        return new UserGetDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setActive(user.getActive())
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setGsm(user.getGsm())
                .setEmail(user.getEmail());
    }
}
