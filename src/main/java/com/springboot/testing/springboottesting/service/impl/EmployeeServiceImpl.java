package com.springboot.testing.springboottesting.service.impl;

import java.lang.module.ResolutionException;
import java.util.List;
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

    // public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    // this.employeeRepository = employeeRepository;
    // }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> svaeEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (svaeEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee aready exists with given email:" + employee.getEmail());

        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        
        return employeeRepository.findById(id);
    }

    @Override
    public Employee update(Employee updatEmployee) {
        
        return employeeRepository.save(updatEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

}
