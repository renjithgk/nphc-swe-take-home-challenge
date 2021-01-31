package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.EntityNotFoundException;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementQueryService;
import com.gmail.renjithkumar1.salarymanagement.utils.EmployeeDtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryManagementQueryService implements ISalaryManagementQueryService {

    private final SalaryManagementRepository salaryManagementRepository;

    @Autowired
    public SalaryManagementQueryService(SalaryManagementRepository salaryManagementRepository) {
        this.salaryManagementRepository = salaryManagementRepository;
    }

    public EmployeeDto getEmployee(String id) {
        Optional<Employee> employee = salaryManagementRepository.findById(id);
        if (employee.isPresent()) {
            return  (EmployeeDto) new EmployeeDtoUtils().convertToDto(employee.get(), new EmployeeDto());
        }
        throw new EntityNotFoundException("No such employee");
    }

    public List<EmployeeDto> getEmployees() {
        List<Employee> employees = salaryManagementRepository.findAll();
        if (!employees.isEmpty()) {
            return employees.stream().map(employee -> (EmployeeDto) new EmployeeDtoUtils().convertToDto(employee, new EmployeeDto())).collect(Collectors.toList());
        }
        throw new EntityNotFoundException("No employees found");
    }
}
