package com.gmail.renjithkumar1.salarymanagement;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


public class SalaryManagementServiceDtoUnitTest {

    @Test
    public void EntityToDto() {

        // Given
        Employee employee = new Employee();
        employee.setId("emp0001");
        employee.setName("Harry Potter");
        employee.setLogin("hpotter");
        employee.setSalary(BigDecimal.valueOf(1234.00));
        employee.setStartDate("2016-11-16");

        // When
        EmployeeDto employeeDto = (EmployeeDto) new DtoUtils().convertToDto(employee, new EmployeeDto());

        // Then
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getName(), employeeDto.getName());
        assertEquals(employee.getLogin(), employeeDto.getLogin());
        assertEquals(employee.getSalary(), employeeDto.getSalary());
        assertEquals(employee.getStartDate(), employeeDto.getStartDate());
    }

    @Test
    public void DtoToEntity() {

        // Given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId("emp0001");
        employeeDto.setName("Harry Potter");
        employeeDto.setLogin("hpotter");
        employeeDto.setSalary(BigDecimal.valueOf(1234.00));
        employeeDto.setStartDate("2016-11-16");

        // When
        Employee employee = (Employee) new DtoUtils().convertToEntity(new Employee(),  employeeDto);

        // Then
        assertEquals(employeeDto.getId(), employee.getId());
        assertEquals(employeeDto.getName(), employee.getName());
        assertEquals(employeeDto.getLogin(), employee.getLogin());
        assertEquals(employeeDto.getSalary(), employee.getSalary());
        assertEquals(employeeDto.getStartDate(), employee.getStartDate());
    }
}


