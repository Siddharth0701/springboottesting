package com.springboot.testing.springboottesting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.springboot.testing.springboottesting.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTestsIT {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("singh@gmail.com")
                .build();

    }

    // junit test for save employee operation
    @DisplayName("junit test for save employee operation")
    @Test
    public void givenEployeeObject_whenSave_thenReturnedSaveEmployee() {

        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("singh@gmail.com")
        //         .build();

        // when -action or the behaviour that we are going test
        Employee savEmployee = employeeRepository.save(employee);

        // verify the output
        System.out.println(savEmployee);
        assertThat(savEmployee).isNotNull();
        assertThat(savEmployee.getId()).isGreaterThan(0);

    }

    // junit test for get all employee
    @DisplayName("junit test for get all employee")
    @Test
    public void givenEmpoyeeList_whenFindAll_thenEmployeeList() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("singh@gmail.com")
        //         .build();
        Employee employee1 = Employee.builder().firstName("Harsh").lastName("Singh").email("harsh@gmail.com").build();
        Employee employee2 = Employee.builder().firstName("Jagan Mohan").lastName("Reddy").email("singh@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when -action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();
        // verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(4);

    }

    // junit test for get employee by id
    @DisplayName("junit test for get employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("singh@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        // when -action or the behaviour that we are going test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        // verify the output
        assertThat(employeeDb).isNotNull();

    }

    // junit test for get employee by email operatio
    @DisplayName("junit test for get employee by email operatio")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        // when -action or the behaviour that we are going test
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();
        // verify the output
        assertThat(employeeDb).isNotNull();

    }

    // junit test for update employee operation
    @DisplayName("junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnedUpdatedEmployee() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);

        // when -action or the behaviour that we are going test
        Employee savedEmploye = employeeRepository.findById(employee.getId()).get();
        savedEmploye.setEmail("ram@gmail.com");
        savedEmploye.setFirstName("Ram");
        Employee updatedEmployee = employeeRepository.save(savedEmploye);
        // verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");

    }

    // Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemovedEmploee() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        // when -action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        // verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // junit test for Custom query using JPQL with index
    @DisplayName("junit test for Custom query using JPQL with index")
    @Test
    public void givenFirstNameAndListName_whenFindByJPQL_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        String firstName = "Siddharth";
        String lastName = "Singh";
        // when -action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        // verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // junit test for Custom query using JPQL with Named Params
    @DisplayName("junit test for Custom query using JPQL with Named Params")
    @Test
    public void givenFirstNameAndListName_whenFindByJPQLNamedParam_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        String firstName = "Siddharth";
        String lastName = "Singh";
        // when -action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
        // verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // junit test for Custom query using natice SQL with index
    @DisplayName("junit test for Custom query using natice SQL with index")
    @Test
    public void givenFirstNameAndListName_whenFindByNativeSQL_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        // String firstName = "Siddharth";
        // String lastName = "Singh";
        // when -action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        // verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // junit test for Custom query using natice SQL with NamedParms
    @DisplayName("junit test for Custom query using natice SQL with NamedParms")
    @Test
    public void givenFirstNameAndListName_whenFindByNativeSQLNamedParms_thenReturnedEmployeeObject() {
        // given -precondition or setup
        // Employee employee = Employee.builder().firstName("Siddharth").lastName("Singh").email("siddharth@gmail.com")
        //         .build();
        employeeRepository.save(employee);
        // String firstName = "Siddharth";
        // String lastName = "Singh";
        // when -action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
        // verify the output
        assertThat(savedEmployee).isNotNull();
    }

}
