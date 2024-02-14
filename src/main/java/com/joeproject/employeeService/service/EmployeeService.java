package com.joeproject.employeeService.service;

import com.joeproject.employeeService.dto.APIResponseDto;
import com.joeproject.employeeService.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
