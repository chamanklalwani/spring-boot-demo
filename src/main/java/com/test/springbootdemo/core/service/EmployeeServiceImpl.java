package com.test.springbootdemo.core.service;

import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.repository.EmployeeRepository;
import com.test.springbootdemo.core.util.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee getById(Integer id) {
        return EmployeeRepository.getInstance().getEmployeeById(id);
    }

    @Override
    public List<Employee> getByName(String name) {
        return EmployeeRepository.getInstance().getEmployeeByName(name);
    }

    @Override
    public void saveEmployee(Employee employee) {
        EmployeeRepository.getInstance().saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        EmployeeRepository.getInstance().updateEmployee(employee);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        EmployeeRepository.getInstance().deleteEmployee(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return EmployeeRepository.getInstance().getAllEmployees();
    }

    @Override
    public void deleteAllEmployees() {

    }

    @Override
    public boolean isEmployeeExist(Employee employee) {
        return false;
    }

    @Override
    public void saveEmployees(List<Employee> employees) {
        EmployeeRepository.getInstance().saveEmployees(employees);
    }

    @Override
    public List<Employee> filterEmployeesByAge(SearchCriteria searchCriteria) {
        return EmployeeRepository.getInstance().filterEmployeesByAge(searchCriteria);
    }
}
