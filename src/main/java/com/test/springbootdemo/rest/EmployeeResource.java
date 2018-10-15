package com.test.springbootdemo.rest;


import com.test.springbootdemo.core.util.SearchCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.service.EmployeeService;
import com.test.springbootdemo.core.util.AppUtil;
import com.test.springbootdemo.core.util.ApiResponse;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Employee Rest API", description = "Defines endpoints for the employee resource.")
public class EmployeeResource {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeResource.class);

    @Autowired
    EmployeeService employeeService; // Service which will do all data retrieval/manipulation work
    @Autowired
    private AppUtil appUtil;



    /**
     * Retrieve All Employees
     * @return
     */
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of all employees.")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity(new ApiResponse("No employees found.", true),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Retrieve an Employee
     * @param id
     *      Specifies employee id to fetch
     * @return
     */
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the employee with given id.")
    public ResponseEntity<?> getEmployee(@PathVariable("id") Integer id) {
        logger.info("Fetching Employee with id {}", id);
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            logger.error("Employee with id {} not found.", id);
            return new ResponseEntity(new ApiResponse("Employee with id <" + id
                    + "> not found", true), HttpStatus.OK);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    /**
     * Create a Employee
     * @param employee
     *      Employee object to create
     * @return
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @ApiOperation(value = "Creates an new employee with the given body.")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        logger.info("Creating Employee : {}", employee);
        if (employeeService.isEmployeeExist(employee)) {
            logger.error("Unable to create. A Employee with name {} already exist", employee.getFullname());
            return new ResponseEntity(new ApiResponse("Unable to create. A Employee with name <" +
                    employee.getFullname() + "> already exist.", false),HttpStatus.OK);
        }
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    /**
     * Updates an Employee
     * @param id
     *      Specifies employee id to update
     * @param employee
     *      Employee object to update
     * @return
     */
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates the employee with the given id by the given request.")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        logger.info("Updating Employee with id {}", id);

        Employee currentEmployee = employeeService.getById(id);

        if (currentEmployee == null) {
            logger.error("Unable to update. Employee with id {} not found.", id);
            return new ResponseEntity(new ApiResponse("Unable to update. Employee with id <" + id + "> not found.", false),
                    HttpStatus.OK);
        }

        currentEmployee.setFullname(employee.getFullname());
        currentEmployee.setAge(employee.getAge());
        currentEmployee.setSalary(employee.getSalary());

        employeeService.updateEmployee(currentEmployee);
        return new ResponseEntity<>(currentEmployee, HttpStatus.OK);
    }

    /**
     * Deletes an Employee
     * @param id
     *      Specifies employee id to delete
     * @return
     */
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes the employee with the given id.")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Integer id) {
        logger.info("Fetching & Deleting Employee with id {}", id);

        Employee employee = employeeService.getById(id);
        if (employee == null) {
            logger.error("Unable to delete. Employee with id {} not found.", id);
            return new ResponseEntity(new ApiResponse("Unable to delete. Employee with id <" + id + "> not found.", false),
                    HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity(new ApiResponse("Employee with id <" + id + "> deleted successfully.", true),
                HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes All Employees
     * @return
     */
    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes all the existing employees.")
    public ResponseEntity<Employee> deleteAllEmployees() {
        logger.info("Deleting All Employees");
        employeeService.deleteAllEmployees();
        return new ResponseEntity(new ApiResponse("Employees deleted successfully.", true),
                HttpStatus.OK);
    }

    /**
     * Filter And Sort Employees by age
     * @param searchCriteria
     *      Specifies (SearchCriteria) object
     * @return
     */
    @RequestMapping(value = "/employee/filterByAge", method = RequestMethod.POST)
    @ApiOperation(value = "Filter & sort employees by age.")
    public ResponseEntity<List<Employee>> filterByAge(@RequestBody SearchCriteria searchCriteria) {
        logger.info("filterByAge() method called");
        List<Employee> employees = employeeService.filterEmployeesByAge(searchCriteria);
        if (employees != null && employees.size() > 0) {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse("No employees found with your search criteria", true),
                HttpStatus.OK);
    }
}
