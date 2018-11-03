package com.test.springbootdemo.core.repository;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.util.SearchCriteria;
import com.test.springbootdemo.core.util.Util;
import com.test.springbootdemo.rest.EmployeeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton employee repository, responsible for all CRUD operations
 */
public class EmployeeRepository {

    private static volatile EmployeeRepository singleton = null;
    static Map<Integer, Employee> employeesMap = new HashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    public static final Logger logger = LoggerFactory.getLogger(EmployeeResource.class);

    //making constructor as private to prevent access to outsiders
    private EmployeeRepository() {
    }

    public static EmployeeRepository getInstance() {
        if (singleton == null) {
            synchronized (EmployeeRepository.class) {
                singleton = new EmployeeRepository();
            }
        }
        return singleton;
    }


    /**
     * saves list of provided employees to in memory map
     *
     * @param employees employees list to save
     */
    public void saveEmployees(List<Employee> employees) {
        for (Employee emp : employees) {
            employeesMap.put(emp.getId(), emp);
        }
    }

    /**
     * returns all employees
     *
     * @return list of employees
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeesMap.values());
    }

    /**
     * returns employee by provided id
     *
     * @param employeeId specifies employee id to fetch
     * @return
     */
    public Employee getEmployeeById(Integer employeeId) {
        return employeesMap.get(employeeId);
    }

    /**
     * returns list of employees by provided name
     *
     * @param name specifies the employee name to search
     * @return
     */
    public List<Employee> getEmployeeByName(String name) {
        return employeesMap.values().stream()
                .filter(emp -> emp.getFullname().contains(name))
                .collect(Collectors.toList());
    }

    /**
     * This method validates, if the given employee already exists
     * in the datasource. It validates fullname, age, and salary
     *
     * @param employee employee to validate
     * @return returns true, if employee found with the same
     * fullname, age, and salary. otherwise, false
     */
    boolean isEmployeeExist(Employee employee) {
        return employeesMap.values().stream().
                anyMatch(emp -> emp.getFullname().equalsIgnoreCase(employee.getFullname())
                        && emp.getAge() == employee.getAge()
                        && emp.getSalary() == employee.getSalary());
    }

    /**
     * This method will save the given employee to the
     * Json and In-memory datasources.
     *
     * @param employee employee to save
     */
    public void saveEmployee(Employee employee) {
        // Convert object to JSON string and save into a json file
        try {
            if (Util.isNotNull(employee)) {
                employee.setId(getMaxId() + 1);
                employeesMap.put(employee.getId(), employee);
                synchronizeJsonDatasource();
                logger.info("Employees Saved!");
            }
        } catch (Exception e) {
            logger.error("Unable to save Employee: " + e.getMessage());
        }
    }

    /**
     * returns Max saved employee Id
     *
     * @return
     */
    private Integer getMaxId() {
        if (!employeesMap.isEmpty()) {
            return new ArrayList<>(employeesMap.keySet()).stream()
                    .mapToInt(v -> v)
                    .max().orElseThrow(NoSuchElementException::new);
        }
        return 0;
    }

    /**
     * This method synchronize/write the in-memory employees hashmap
     * to json datasource
     */
    private void synchronizeJsonDatasource() {
        try {
            File file = new File("/datasource/db.json");
            OutputStream output = new FileOutputStream(file);
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(output, getAllEmployees());
            logger.info("Data Synchronized!");
        } catch (IOException e) {
            logger.error("Unable to save Employee: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unable to save Employee: " + e.getMessage());
        }
    }

    public void updateEmployee(Employee employee) {
        if (Util.isNotNull(employee)) {
            employeesMap.put(employee.getId(), employee);
            synchronizeJsonDatasource();
        }
    }

    public void deleteEmployee(Integer id) {
        if (!employeesMap.isEmpty()) {
            employeesMap.remove(id);
            synchronizeJsonDatasource();
        }
    }

    public List<Employee> filterEmployeesByAge(SearchCriteria searchCriteria) {
        try {
            String operator = searchCriteria.getOperator();
            List<Employee> employees;

            switch (operator) {
                case "lt": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() < searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                case "lte": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() <= searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                case "gt": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() > searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                case "gte": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() >= searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                case "eq": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() == searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                case "ne": {
                    employees = employeesMap.values().stream().
                            filter(emp -> (emp.getAge() != searchCriteria.getValue())).collect(Collectors.toList());
                    break;
                }
                default: {
                    employees = getAllEmployees();
                }
            }

            String sortOrder = searchCriteria.getSortOrder();
            if (employees.size() > 0 && !Util.isNullOrEmpty(sortOrder) && sortOrder.equalsIgnoreCase("desc")) {
                employees.stream()
                        .sorted(Comparator.comparing(Employee::getAge).reversed())
                        .collect(Collectors.toList());
            } else {
                // default sort oder is ASC
                employees.stream()
                        .sorted(Comparator.comparing(Employee::getAge))
                        .collect(Collectors.toList());
            }

            return employees;
        } catch (Exception e) {
            logger.error(e.toString());
            return new ArrayList<>();
        }
    }
}
