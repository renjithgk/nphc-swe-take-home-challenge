package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.EntityNotFoundException;
import com.gmail.renjithkumar1.salarymanagement.exception.ResourceAlreadyExistsException;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementCommandService;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryManagementCommandService implements ISalaryManagementCommandService {

    private final SalaryManagementRepository salaryManagementRepository;

    @Autowired
    public SalaryManagementCommandService(SalaryManagementRepository salaryManagementRepository) {
        this.salaryManagementRepository = salaryManagementRepository;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        employeeDto.validate();
        List<Employee> employeeList = salaryManagementRepository.findAll();
        Employee existingEmployee = employeeList.stream().filter(e -> employeeDto.getId().equals(e.getId())).findAny().orElse(null);
        if (existingEmployee != null) {
            throw new ResourceAlreadyExistsException("Employee ID already exists");
        }

        existingEmployee = employeeList.stream().filter(e -> employeeDto.getLogin().equals(e.getLogin())).findAny().orElse(null);
        if (existingEmployee != null) {
            throw new ResourceAlreadyExistsException("Employee login not unique");
        }

        Employee employee = (Employee) new DtoUtils().convertToEntity(new Employee(), employeeDto);
        Employee savedEmployee = this.salaryManagementRepository.save(employee);
        return (EmployeeDto) new DtoUtils().convertToDto(savedEmployee, new EmployeeDto());
    }

    @Override
    public EmployeeDto updateEmployee(String id, EmployeeDto employeeDto) {

        employeeDto.validate();

        List<Employee> employeeList = salaryManagementRepository.findAll();

        Employee existingEmployee = employeeList.stream().filter(e -> employeeDto.getLogin().equals(e.getLogin())).findAny().orElse(null);
        if (existingEmployee != null && existingEmployee.getId() != employeeDto.getId()) {
            throw new ResourceAlreadyExistsException("Employee login not unique");
        }

        Employee employee = salaryManagementRepository.findById(id).map(s ->
        {
            s.setName(employeeDto.getName());
            s.setLogin(employeeDto.getLogin());
            s.setSalary(employeeDto.getSalary());
            s.setStartDate(employeeDto.getStartDate());
            return s;
        }).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException("No such employee");
        }


        Employee savedEmployee = this.salaryManagementRepository.save(employee);
        return (EmployeeDto) new DtoUtils().convertToDto(savedEmployee, new EmployeeDto());
    }

    @Override
    public void deleteEmployee(String id) {
        Optional<Employee> employee = salaryManagementRepository.findById(id);
        if (employee.isPresent()) {
            salaryManagementRepository.deleteById(id);
        }
        throw new EntityNotFoundException("No such employee");
    }
}
