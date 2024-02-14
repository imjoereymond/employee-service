package com.joeproject.employeeService.service;

import com.joeproject.employeeService.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface APIClient {

    @GetMapping("api/departments/getDepartmentByCode/{code}")
    DepartmentDto getDepartmentByCode(@PathVariable("code") String code);
}
