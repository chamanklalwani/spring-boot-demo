package com.test.springbootdemo.core.service;

import com.test.springbootdemo.core.dto.EmployeeDto;
import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.repository.EmployeeRepository;
import com.test.springbootdemo.core.util.SearchCriteria;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private ModelMapper modelMapper;

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
    public List<EmployeeDto> getAllEmployees() {

        List<Employee> employees = EmployeeRepository.getInstance().getAllEmployees();

        java.lang.reflect.Type targetListType = new TypeToken<List<EmployeeDto>>() {}.getType();
        List<EmployeeDto> employeeDto = this.modelMapper.map(employees, targetListType);

        return employeeDto;
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
    public List<EmployeeDto> filterEmployeesByAge(SearchCriteria searchCriteria) {

        List<Employee> employees = EmployeeRepository.getInstance().filterEmployeesByAge(searchCriteria);

        java.lang.reflect.Type targetListType = new TypeToken<List<EmployeeDto>>() {}.getType();
        List<EmployeeDto> employeeDto = this.modelMapper.map(employees, targetListType);

        return employeeDto;
    }
}
