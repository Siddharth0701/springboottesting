package com.springboot.testing.springboottesting.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testing.springboottesting.model.Employee;
import com.springboot.testing.springboottesting.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private EmployeeService employeeService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void givenEployeeObject_whenCreateEmployee_thenReturnedSavedEmployee() throws Exception {
                // given -precondition or setup
                Employee employee = Employee.builder().firstName("siddharth").lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                given(employeeService.saveEmployee(any(Employee.class)))
                                .willAnswer((invocation) -> invocation.getArgument(0));
                // when -action or behviour that are going to test
                ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employee)));
                // then -verify the result or output using assert statement
                response.andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.firstName",
                                                is(employee.getFirstName())))
                                .andExpect(jsonPath("$.lastName",
                                                is(employee.getLastName())))
                                .andExpect(jsonPath("$.email",
                                                is(employee.getEmail())));

        }

        // Junit test for get All employee rest api

        @Test
        public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
                // given -precondition of setup
                List<Employee> listOfEmployee = new ArrayList<>();
                listOfEmployee.add(Employee.builder().firstName("siddharth").lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build());
                listOfEmployee.add(Employee.builder().firstName("harsh").lastName("Singh")
                                .email("harsh.singh0701@gmil.com").build());
                given(employeeService.getAllEmployees()).willReturn(listOfEmployee);
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(get("/api/employees"));
                // then -verify the output
                response.andExpect(status().isOk()).andDo(print())
                                .andExpect(jsonPath("$.size()",
                                                is(listOfEmployee.size())));

        }

        // positive scenario -valid employee id
        // Junit test for getEmployee by id rest api

        @Test
        public void givenEmployeeId_whenGetEmployeeById_thenEmployeeObject() throws Exception {
                // given -precondition of setup
                long employeeId = 1L;
                Employee employee = Employee.builder().firstName("siddharth").lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
                // then -verify the output
                response.andExpect(status().isOk()).andDo(print())
                                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                                .andExpect(jsonPath("$.email", is(employee.getEmail())));

        }
        // Negative scenario -valid employee id
        // Junit test for getEmployee by id rest api

        @Test
        public void givenInValidEmployeeId_whenGetEmployeeById_thenReturnEmptyResponce() throws Exception {
                // given -precondition of setup
                long employeeId = 1L;
                Employee employee = Employee.builder().firstName("siddharth").lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
                // then -verify the output
                response.andExpect(status().isNotFound())
                                .andDo(print());
                // .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                // .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                // .andExpect(jsonPath("$.email", is(employee.getEmail())));
        }

        // Junit test for update employee REST API-positive scenario

        @Test
        public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnedUpdatedEmployee() throws Exception {
                // given -precondition of setup
                long employeeId = 1L;
                Employee savedEmployee = Employee.builder()
                                .firstName("siddharth")
                                .lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();

                Employee upadtedEmployee = Employee.builder().firstName("siddharth").lastName("Singham")
                                .email("siddharth.singham0701@gmil.com").build();
                given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
                given(employeeService.update(any(Employee.class)))
                                .willAnswer((invocation) -> invocation.getArgument(0));
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc
                                .perform(put("/api/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(upadtedEmployee)));

                // then -verify the output
                response.andExpect(status().isOk()).andDo(print())
                                .andExpect(jsonPath("$.firstName", is(upadtedEmployee.getFirstName())))
                                .andExpect(jsonPath("$.lastName", is(upadtedEmployee.getLastName())))
                                .andExpect(jsonPath("$.email", is(upadtedEmployee.getEmail())));

        }
        // Junit test for update employee REST API-negative scenario

        @Test
        public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
                // given -precondition of setup
                long employeeId = 1L;
                Employee savedEmployee = Employee.builder()
                                .firstName("siddharth")
                                .lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();

                Employee upadtedEmployee = Employee.builder().firstName("siddharth").lastName("Singham")
                                .email("siddharth.singham0701@gmil.com").build();
                given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
                given(employeeService.update(any(Employee.class)))
                                .willAnswer((invocation) -> invocation.getArgument(0));
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc
                                .perform(put("/api/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(upadtedEmployee)));

                // then -verify the output
                response.andExpect(status().isNotFound()).andDo(print());
                // .andExpect(jsonPath("$.firstName", is(upadtedEmployee.getFirstName())))
                // .andExpect(jsonPath("$.lastName", is(upadtedEmployee.getLastName())))
                // .andExpect(jsonPath("$.email", is(upadtedEmployee.getEmail())));

        }
        // Junit test for delete employee Rest Api

        @Test
        public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
                // given -precondition of setup
                long id=1L;
                willDoNothing().given(employeeService).deleteEmployee(id);
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(delete("/api/employees/{id}", id));
                // then -verify the output
                response.andExpect(status().isOk()).andDo(print());

        }

}
