package com.joeproject.employeeService.service;

import com.joeproject.employeeService.dto.APIResponseDto;
import com.joeproject.employeeService.dto.DepartmentDto;
import com.joeproject.employeeService.dto.EmployeeDto;
import com.joeproject.employeeService.entity.Employee;
import com.joeproject.employeeService.exception.EmailAlreadyExistsException;
import com.joeproject.employeeService.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

//    private RestTemplate restTemplate;

//    private WebClient webClient;

    private APIClient apiClient;

    private ModelMapper modelMapper;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
//        Employee employee = new Employee();
//        employee.setId(employeeDto.getId());
//        employee.setFirstName(employeeDto.getFirstName());
//        employee.setLastName(employeeDto.getLastName());
//        employee.setEmail(employeeDto.getEmail());
//        employee.setDepartmentCode(employeeDto.getDepartmentCode());
        Optional<Employee> optionalUser = employeeRepository.findByEmail(employeeDto.getEmail());
        if(optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for User");
        }

        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee saveEmployee = employeeRepository.save(employee);

//        EmployeeDto saveEmployeeDto = new EmployeeDto();
//        saveEmployeeDto.setId(saveEmployee.getId());
//        saveEmployeeDto.setFirstName(saveEmployee.getFirstName());
//        saveEmployeeDto.setLastName(saveEmployee.getLastName());
//        saveEmployeeDto.setEmail(saveEmployee.getEmail());
//        saveEmployeeDto.setDepartmentCode(saveEmployee.getDepartmentCode());

        EmployeeDto saveEmployeeDto = modelMapper.map(saveEmployee, EmployeeDto.class);

        return saveEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();

//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/getDepartmentByCode/"+employee.getDepartmentCode(),
//                DepartmentDto.class);
//
//        DepartmentDto departmentDto = responseEntity.getBody();

//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://localhost:8080/api/departments/getDepartmentByCode/"+employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDepartmentCode(employee.getDepartmentCode());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }
}
