package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import org.javatuples.Pair;

import java.util.List;

public interface ISalaryManagementQueryService {

    EmployeeDto getEmployee(String id);

    List<EmployeeDto> getEmployees();
}
