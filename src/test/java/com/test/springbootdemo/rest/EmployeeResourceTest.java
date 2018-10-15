package com.test.springbootdemo.rest;


import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.service.EmployeeService;
import com.test.springbootdemo.init.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes={Main.class})
public class EmployeeResourceTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeResource employeeResource;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(employeeResource).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {

        Employee employee = new Employee();
        employee.setFullname("Employee A");
        List<Employee> employees = singletonList(employee);
        doReturn(employees).when(employeeService).getAllEmployees();

        mvc.perform(get("/api/v1/employee")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fullname", is(employee.getFullname())));

        verify(employeeService).getAllEmployees();
    }

    @Test
    public void testEmployeeById() throws Exception {

        Employee employee = new Employee();
        employee.setFullname("Employee A");
        employee.setId(1);
        doReturn(employee).when(employeeService).getById(1);

        mvc.perform(get("/api/v1//employee/"+ employee.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employee.getId())))
                .andExpect(jsonPath("$.*", hasSize(4)));

        verify(employeeService).getById(1);
    }

}
