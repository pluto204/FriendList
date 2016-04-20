package com.hai.friendlist;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbFriend")
public class Friend extends Model {

    public static final String NAME = "name";
    public static final String PHONE = "phone";

    @Column(name = NAME)
    private String name;

    @Column(name = PHONE)
    private String phone;


    public String getName() {
        return name;
    }

    public Friend setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Friend setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
