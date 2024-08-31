package com.example.usersapp.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "email")
    @SerializedName("email")
    String email;
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    String firstName;
    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    String lastName;
    @ColumnInfo(name = "image")
    @SerializedName("avatar")
    String imageUri;

    public User(int id, String email, String firstName, String lastName, String imageUri) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUri = imageUri;
    }

    @Ignore
    public User(String email, String firstName, String lastName, String imageUri) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(firstName).append(" ").append(lastName);

        return nameBuilder.toString();
    }
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


