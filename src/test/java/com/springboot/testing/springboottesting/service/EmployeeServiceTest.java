package com.springboot.testing.springboottesting.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.testing.springboottesting.exception.ResourceNotFoundException;
import com.springboot.testing.springboottesting.model.Employee;
import com.springboot.testing.springboottesting.repository.EmployeeRepository;
import com.springboot.testing.springboottesting.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup() {
        // employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().id(1L).firstName("Siddharth").lastName("Singh")
                .email("siddharth.singh@gmail.com").build();

    }

    // Junit test for saveEmployee
    @DisplayName("Junit test for saveEmployee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given -precondition of setup
        // Employee employee =
        // Employee.builder().id(1L).firstName("Siddharth").lastName("Singh")
        // .email("siddharth.singh@gmail.com").build();
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when -action or the behaviour that we are going test
        Employee saveEmployee = employeeService.saveEmployee(employee);
        System.out.println(saveEmployee);
        // then -verify the output
        Assertions.assertThat(saveEmployee).isNotNull();

    }

    // Junit test for saveEmployee
    @DisplayName("Junit test for saveEmployee which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given -precondition of setup
        // Employee employee =
        // Employee.builder().id(1L).firstName("Siddharth").lastName("Singh")
        // .email("siddharth.singh@gmail.com").build();
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
        // given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when -action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });
        // then -verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
        // Employee saveEmployee = employeeService.saveEmployee(employee);
        // System.out.println(saveEmployee);
        // // then -verify the output
        // Assertions.assertThat(saveEmployee).isNotNull();

    }

    // Junit test for getAllEmployee method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeesList() {
        // given -precondition of setup
        Employee employee1 = Employee.builder().id(2L).firstName("Harsh").lastName("Singh")
                .email("harsh.singh@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        // when -action or the behaviour that we are going test

        List<Employee> employeeList = employeeService.getAllEmployees();
        // then -verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);

    }

    // Junit test for getAllEmployee method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployee_thenReturnEmptyEmployeesList() {
        // given -precondition of setup
        Employee employee1 = Employee.builder().id(2L).firstName("Harsh").lastName("Singh")
                .email("harsh.singh@gmail.com").build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when -action or the behaviour that we are going test

        List<Employee> employeeList = employeeService.getAllEmployees();
        // then -verify the output
        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);

    }

    // junit test for getEmployeeById method
    @DisplayName("junit test for getEmployeesById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // //given -precondition of setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        // //when -action or the behaviour that we are going test
        Employee saveEmployee = employeeService.getEmployeeById(employee.getId()).get();
        // // then -verify the output
        Assertions.assertThat(saveEmployee).isNotNull();

    }
    // Junit test for update employee

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given -precondition of setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmial.com");
        employee.setFirstName("Ram");
        // when -action or the behaviour that we are going test
        Employee updateEmployee = employeeService.saveEmployee(employee);

        // then -verify the output
        Assertions.assertThat(updateEmployee.getEmail()).isEqualTo("ram@gmial.com");
        Assertions.assertThat(updateEmployee.getFirstName()).isEqualTo("Ram");

    }

    // Junit test for delete employee method
    @DisplayName("Junit test for delete employee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        long employeeId = 1L;
        // given -precondition of setup
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employeeId);
        // when -action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);
        // then -verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }

    // //Junit test for

    // @Test
    // public void given_when_then(){
    // //given -precondition of setup
    // //when -action or the behaviour that we are going test
    // // then -verify the output

    // }

}
