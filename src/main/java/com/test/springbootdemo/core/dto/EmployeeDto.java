package com.test.springbootdemo.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.springbootdemo.core.model.Employee;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String fullname;
    private Integer age;
    private double salary;

    private List<Employee> data;

    public EmployeeDto() {
    }

    public EmployeeDto(List<Employee> employees) {
        this.data = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
