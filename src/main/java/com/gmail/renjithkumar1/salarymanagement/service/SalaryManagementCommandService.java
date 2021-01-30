package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Salary;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementCommandService;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryManagementCommandService implements ISalaryManagementCommandService {

    private final SalaryManagementRepository salaryManagementRepository;

    @Autowired
    public SalaryManagementCommandService(SalaryManagementRepository salaryManagementRepository) {
        this.salaryManagementRepository = salaryManagementRepository;
    }

    public Pair<Integer, SalaryDto> CreateSalary(SalaryDto salaryDto) {
        Salary salary = (Salary) new DtoUtils().convertToEntity(new Salary(), salaryDto);
        this.salaryManagementRepository.save(salary);
        return new Pair<>(201, salaryDto);
    }
}
