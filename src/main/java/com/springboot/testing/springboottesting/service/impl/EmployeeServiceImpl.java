package com.springboot.testing.springboottesting.service.impl;

import java.lang.module.ResolutionException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.testing.springboottesting.exception.ResourceNotFoundException;
import com.springboot.testing.springboottesting.model.Employee;
import com.springboot.testing.springboottesting.repository.EmployeeRepository;
import com.springboot.testing.springboottesting.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> svaeEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (svaeEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee aready exists with given email:" + employee.getEmail());

        }

        return employeeRepository.save(employee);
    }

}
