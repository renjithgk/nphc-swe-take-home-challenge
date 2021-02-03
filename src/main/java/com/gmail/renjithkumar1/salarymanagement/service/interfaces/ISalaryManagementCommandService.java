package com.gmail.renjithkumar1.salarymanagement.service.interfaces;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISalaryManagementCommandService {

    long CheckAndUploadCsv(MultipartFile csvfile) throws IOException;

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(String id, EmployeeDto employeeDto);

    void deleteEmployee(String id);

}
