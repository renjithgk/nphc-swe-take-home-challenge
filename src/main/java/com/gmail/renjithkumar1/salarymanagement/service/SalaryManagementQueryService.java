package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Salary;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementQueryService;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoUtils;
import org.javatuples.Pair;
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

    public Pair<Integer, SalaryDto> getSalary(Long id) {
        Optional<Salary> salary = this.salaryManagementRepository.findById(id);
        if (salary.isPresent()) {
            return new Pair<>(200, (SalaryDto) new DtoUtils().convertToDto(salary, new SalaryDto()));
        } else {
            return new Pair<>(404, null);
        }
    }

    public Pair<Integer, List<SalaryDto>> getSalaries() {
        List<Salary> salaries = salaryManagementRepository.findAll();
        return new Pair<>(200, salaries.stream().map(salary -> (SalaryDto) new DtoUtils().convertToDto(salary, new SalaryDto())).collect(Collectors.toList()));
    }
}
