package xyz.thelostsoul.bean;

/**
 * Created by jamie on 17-2-13.
 */
public class User {
    private Integer id;
    private String name;
    private String password;

    public User(Integer id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
