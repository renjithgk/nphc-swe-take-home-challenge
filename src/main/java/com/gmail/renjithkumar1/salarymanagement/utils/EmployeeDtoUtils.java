package com.gmail.renjithkumar1.salarymanagement.utils;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class EmployeeDtoUtils extends DtoUtils {

    public DtoEntity convertToDto(Employee obj, EmployeeDto mapper) {
        EmployeeDto employeeDto = new ModelMapper().map(obj, mapper.getClass());
        employeeDto.setSalary(employeeDto.getSalary().setScale(2, RoundingMode.CEILING));
        return employeeDto;
    }
}
