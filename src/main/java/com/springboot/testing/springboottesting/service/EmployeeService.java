package com.springboot.testing.springboottesting.service;

import java.util.List;
import java.util.Optional;

import com.springboot.testing.springboottesting.model.Employee;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    Employee update(Employee updatEmployee);
    void deleteEmployee(long id);

}
