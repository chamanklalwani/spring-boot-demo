package com.test.springbootdemo.rest;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@Api(value = "Employee Rest API", description = "Defines endpoints for the root resource.")
public class RootResource {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Welcome to SpringBoot Rest Api", HttpStatus.OK);
    }
}
