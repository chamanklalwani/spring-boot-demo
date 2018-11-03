package com.test.springbootdemo.core.service;

import com.test.springbootdemo.core.dto.EmployeeDto;
import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.util.SearchCriteria;

import java.util.List;

public interface EmployeeService {

    Employee getById(Integer id);

    List<Employee> getByName(String name);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    List<EmployeeDto> getAllEmployees();

    void deleteAllEmployees();

    boolean isEmployeeExist(Employee employee);

    void saveEmployees(List<Employee> employees);

    List<EmployeeDto> filterEmployeesByAge(SearchCriteria searchCriteria);
}
