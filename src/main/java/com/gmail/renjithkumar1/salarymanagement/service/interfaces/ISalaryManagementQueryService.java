package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import org.javatuples.Pair;

public interface ISalaryManagementQueryService {

    Pair<Integer, SalaryDto> getSalary(Long id);

}
