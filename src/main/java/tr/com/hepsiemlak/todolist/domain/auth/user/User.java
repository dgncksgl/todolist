package tr.com.hepsiemlak.todolist.domain.auth.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.UUID;

@Document
public class User {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Size(min = 2, max = 50)
    @Field("username")
    private String username;

    @NotNull
    @Size(min = 2)
    @Field("password")
    private String password;

    @NotNull
    @Field("active")
    private Boolean active;

    @NotNull
    @Size(min = 2, max = 100)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 2, max = 100)
    @Field("surname")
    private String surname;

    @Field("gsm")
    private String gsm;

    @Field("email")
    private String email;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public User setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGsm() {
        return gsm;
    }

    public User setGsm(String gsm) {
        this.gsm = gsm;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
