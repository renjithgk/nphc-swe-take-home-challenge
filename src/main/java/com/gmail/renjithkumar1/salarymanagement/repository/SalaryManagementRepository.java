package com.gmail.renjithkumar1.salarymanagement.repository;

import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SalaryManagementRepository extends CrudRepository<Employee, String> {

    Page<Employee> findAll(Pageable pageable);
    Page<Employee> findByNameContaining(String name, Pageable pageable);
    Page<Employee> findBySalaryGreaterThanEqual(BigDecimal maxSalary, Pageable pageable);
    Page<Employee> findBySalaryLessThan(BigDecimal minSalary, Pageable pageable);
}
