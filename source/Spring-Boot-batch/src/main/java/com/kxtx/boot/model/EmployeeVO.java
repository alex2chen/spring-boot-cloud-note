package com.kxtx.boot.model;

import com.kxtx.boot.client.BaseVO;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/19
 */
public class EmployeeVO extends BaseVO {
    private int id;
    private String name;
    private String gender;
    private String email;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
