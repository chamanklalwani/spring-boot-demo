package com.test.springbootdemo.core.model;

import java.io.Serializable;

public class Employee implements Serializable, Comparable<Employee> {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String fullname;
    private Integer age;
    private double salary;

    public Employee() {
    }

    public Employee(Integer id, String fullname, Integer age, double salary) {
        this.id = id;
        this.fullname = fullname;
        this.age = age;
        this.salary = salary;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Employee other = (Employee) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", fullname=" + fullname + ", age=" + age
                + ", salary=" + salary + "]";
    }

    @Override
    public int compareTo(Employee e) {
        if (getAge() == null || e.getAge() == null) {
            return 0;
        }
        return getAge().compareTo(e.getAge());
    }

}
