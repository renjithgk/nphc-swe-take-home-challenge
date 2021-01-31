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

    public Pair<Integer, SalaryDto> createSalary(SalaryDto salaryDto) {
        Salary salary = (Salary) new DtoUtils().convertToEntity(new Salary(), salaryDto);
        Salary saved = this.salaryManagementRepository.save(salary);
        return new Pair<>(201, salaryDto);
    }

    @Override
    public Pair<Integer, SalaryDto> updateSalary(Long id, SalaryDto salaryDto) {
        Salary salary = salaryManagementRepository.findById(id).map(s ->
        {
            s.setSalary(salaryDto.getSalary());
            s.setName(salaryDto.getName());
            return s;
        }).orElse(null);
        Salary saved = this.salaryManagementRepository.save(salary);
        return new Pair<>(201, salaryDto);
    }

    @Override
    public Pair<Integer, String> deleteSalary(Long id) {
       salaryManagementRepository.deleteById(id);
       return new Pair<>(200, "Deleted record successfully");
    }
}
