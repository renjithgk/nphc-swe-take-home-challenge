package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.EntityNotFoundException;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementQueryService;
import com.gmail.renjithkumar1.salarymanagement.utils.EmployeeDtoUtils;
import com.gmail.renjithkumar1.salarymanagement.utils.SortOrder;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    public EmployeeDto getEmployee(String id) {
        Optional<Employee> employee = salaryManagementRepository.findById(id);
        if (employee.isPresent()) {
            return (EmployeeDto) new EmployeeDtoUtils().convertToDto(employee.get(), new EmployeeDto());
        }
        throw new EntityNotFoundException("No such employee");
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<EmployeeDto> getEmployees(Optional<BigDecimal> minSalary, Optional<BigDecimal> maxSalary, Optional<Integer> pageNo, Optional<Integer> pageSize, String sortBy, SortOrder sortOrder) {

        Pageable paging;
        if (sortOrder == SortOrder.ASC) {
            if (pageNo.isPresent() && pageSize.isPresent()) {
                paging = PageRequest.of(pageNo.get(), pageSize.get(), Sort.by(sortBy));
            } else {
                paging = PageRequest.of(0, 10, Sort.by(sortBy));
            }
        } else {
            if (pageNo.isPresent() && pageSize.isPresent()) {
                paging = PageRequest.of(pageNo.get(), pageSize.get(), Sort.by(sortBy).descending());
            } else {
                paging = PageRequest.of(0, 10, Sort.by(sortBy).descending());
            }
        }

       List<Employee> employees;

        if (maxSalary.isPresent()) {
            employees = IterableUtils.toList(salaryManagementRepository.findBySalaryLessThan(maxSalary.get(), paging));
        } else if (minSalary.isPresent()) {
            employees = IterableUtils.toList(salaryManagementRepository.findBySalaryGreaterThanEqual(minSalary.get(), paging));
        } else {
            employees = IterableUtils.toList(salaryManagementRepository.findAll(paging));
        }

        if (!employees.isEmpty()) {
            return employees.stream().map(employee -> (EmployeeDto) new EmployeeDtoUtils().convertToDto(employee, new EmployeeDto())).collect(Collectors.toList());
        }
        throw new EntityNotFoundException("No employees found");
    }
}
