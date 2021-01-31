package com.gmail.renjithkumar1.salarymanagement.repository;

import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryManagementRepository extends JpaRepository<Employee, String> {
}
