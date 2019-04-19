package com.kxtx.boot.model;

import com.kxtx.boot.client.BaseSearch;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/9/25
 */
public class EmployeeSearch extends BaseSearch {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmployeeSearch{" +
                "id=" + id +
                '}';
    }
}
