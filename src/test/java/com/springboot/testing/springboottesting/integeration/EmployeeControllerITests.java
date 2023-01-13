package com.springboot.testing.springboottesting.integeration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testing.springboottesting.model.Employee;
import com.springboot.testing.springboottesting.repository.EmployeeRepository;
import static org.mockito.BDDMockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private EmployeeRepository employeeRepository;
        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach

        void setup() {
                employeeRepository.deleteAll();

        }

        @Test
        public void givenEployeeObject_whenCreateEmployee_thenReturnedSavedEmployee() throws Exception {
                // given -precondition or setup
                Employee employee = Employee.builder()
                                .firstName("siddharth")
                                .lastName("Singh")
                                .email("siddharth.singh0701@gmil.com")
                                .build();

                // when -action or behviour that are going to test
                ResultActions response = mockMvc.perform(post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
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
                listOfEmployee.add(Employee.builder().firstName("siddhartha").lastName("Singham")
                                .email("siddharth.singh0701@gmil.com").build());
                listOfEmployee.add(Employee.builder().firstName("harsh").lastName("Singh")
                                .email("harsh.singh0701@gmil.com").build());
                employeeRepository.saveAll(listOfEmployee);
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

                Employee employee = Employee.builder().firstName("siddharth").lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                employeeRepository.save(employee);
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
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
                employeeRepository.save(employee);
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

                Employee savedEmployee = Employee.builder()
                                .firstName("siddharth")
                                .lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                employeeRepository.save(savedEmployee);

                Employee upadtedEmployee = Employee.builder().firstName("siddharth").lastName("Singham")
                                .email("siddharth.singham0701@gmil.com").build();

                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc
                                .perform(put("/api/employees/{id}", savedEmployee.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
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
                employeeRepository.save(savedEmployee);
                Employee upadtedEmployee = Employee.builder().firstName("siddharth").lastName("Singham")
                                .email("siddharth.singham0701@gmil.com").build();

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

                Employee savedEmployee = Employee.builder()
                                .firstName("siddharth")
                                .lastName("Singh")
                                .email("siddharth.singh0701@gmil.com").build();
                employeeRepository.save(savedEmployee);
                // when -action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));
                // then -verify the output
                response.andExpect(status().isOk()).andDo(print());

        }

}
