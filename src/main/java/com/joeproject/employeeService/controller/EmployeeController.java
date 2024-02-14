package com.joeproject.employeeService.controller;

import com.joeproject.employeeService.dto.APIResponseDto;
import com.joeproject.employeeService.dto.EmployeeDto;
import com.joeproject.employeeService.exception.ErrorDetails;
import com.joeproject.employeeService.exception.ResourceNotFoundException;
import com.joeproject.employeeService.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("create")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeDto1 = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
    }

    @GetMapping("getEmployeeById/{id}")
    public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("id") Long id) {
        APIResponseDto employeeDto = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimeStamp(LocalDateTime.now());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setErrorCode("USER_NOT_FOUND");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
