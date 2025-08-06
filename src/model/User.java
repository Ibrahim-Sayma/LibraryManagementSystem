package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class User {

    private StringProperty fullName = new SimpleStringProperty();
    private StringProperty profileImagePath = new SimpleStringProperty();
    private String userName, password, email, phone, role;
    private int id;
 


    @Override
    public String toString() {
        return "User{" + "fullName=" + fullName + ", userName=" + userName + ", password=" + password + ", email=" + email + ", phone=" + phone + ", role=" + role + ", img=" + profileImagePath + '}';
    }

    public User(String fullName, String userName, String password, String email, String phone, String role, String profileImagePath) {
        this.fullName.set(fullName);;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.profileImagePath.set(profileImagePath);
    }

    public User(int id, String fullName, String userName, String password, String email, String phone, String role, String profileImagePath) {
        this.id = id;
        this.fullName.set(fullName);;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.profileImagePath.set(profileImagePath);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public StringProperty FullNameProperty() {
        return this.fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImagePath() {
        return profileImagePath.get();
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath.set(profileImagePath);
    }

    public StringProperty profileImagePathProperty() {
        return this.profileImagePath;
    }

}
