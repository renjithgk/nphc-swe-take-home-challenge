package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import org.javatuples.Pair;

public interface ISalaryManagementCommandService {

    Pair<Integer, SalaryDto> createSalary(SalaryDto salaryDto);

    Pair<Integer, SalaryDto> updateSalary(Long id, SalaryDto salaryDto);

    Pair<Integer, String> deleteSalary(Long id);

}
