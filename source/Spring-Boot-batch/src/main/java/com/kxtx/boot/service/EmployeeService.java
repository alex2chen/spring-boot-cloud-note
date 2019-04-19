/**
 *
 */
package com.kxtx.boot.service;

import java.util.List;

import com.kxtx.boot.model.Employee;


public interface EmployeeService {
    void add(Employee employee);

    List<Employee> getAll();
}
