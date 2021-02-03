package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.utils.SortOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ISalaryManagementQueryService {

    EmployeeDto getEmployee(String id);

    List<EmployeeDto> getEmployees(Optional<BigDecimal> minSalary, Optional<BigDecimal> maxSalary, Optional<Integer> pageNo, Optional<Integer> pageSize, String sortBy, SortOrder sortOrder);
}
