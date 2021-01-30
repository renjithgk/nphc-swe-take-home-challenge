package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import org.javatuples.Pair;

public interface ISalaryManagementCommandService {

    Pair<Integer, SalaryDto> CreateSalary(SalaryDto salaryDto);

}
