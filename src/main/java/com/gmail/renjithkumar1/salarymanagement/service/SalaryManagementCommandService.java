package com.gmail.renjithkumar1.salarymanagement.service;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.EntityNotFoundException;
import com.gmail.renjithkumar1.salarymanagement.exception.InvalidFileDataException;
import com.gmail.renjithkumar1.salarymanagement.exception.ResourceAlreadyExistsException;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import com.gmail.renjithkumar1.salarymanagement.service.interfaces.ISalaryManagementCommandService;
import com.gmail.renjithkumar1.salarymanagement.utils.ApacheCommonsCsvUtil;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryManagementCommandService implements ISalaryManagementCommandService {

    private final SalaryManagementRepository salaryManagementRepository;

    @Autowired
    public SalaryManagementCommandService(SalaryManagementRepository salaryManagementRepository) {
        this.salaryManagementRepository = salaryManagementRepository;
    }

    @Transactional
    public long CheckAndUploadCsv(MultipartFile csvfile) throws IOException {

        if (csvfile.getOriginalFilename().isEmpty()) {
            throw new FileNotFoundException("Csv file not found");
        }

        if (!ApacheCommonsCsvUtil.isCSVFile(csvfile)) {
            throw new InvalidObjectException("Not a valid CSV file");
        }

        if (ApacheCommonsCsvUtil.getColumnCount(csvfile.getInputStream()) != 5) {
            throw new InvalidFileDataException("All 5 columns must be there and filled");
        }

        try {
            return loadFromCsv(csvfile.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Unable to process the csv file, please validate the data inside " + e.getMessage());
        }
    }

    private long loadFromCsv(InputStream file) {
        try {

            // Using ApacheCommons Csv Utils to parse CSV file
            List<Employee> lstCustomers = ApacheCommonsCsvUtil.parseCsvFile(file);

            // Using OpenCSV Utils to parse CSV file
            // List<Customer> lstCustomers = OpenCsvUtil.parseCsvFile(file);

            salaryManagementRepository.saveAll(lstCustomers);
            return lstCustomers.stream().count();
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        employeeDto.validate();
        List<Employee> employeeList = IterableUtils.toList(salaryManagementRepository.findAll());
        Employee existingEmployee = employeeList.stream().filter(e -> employeeDto.getId().equals(e.getId())).findAny().orElse(null);
        if (existingEmployee != null) {
            throw new ResourceAlreadyExistsException("Employee ID already exists");
        }

        existingEmployee = employeeList.stream().filter(e -> employeeDto.getLogin().equals(e.getLogin())).findAny().orElse(null);
        if (existingEmployee != null) {
            throw new ResourceAlreadyExistsException("Employee login not unique");
        }

        Employee employee = (Employee) new DtoUtils().convertToEntity(new Employee(), employeeDto);
        Employee savedEmployee = this.salaryManagementRepository.save(employee);
        return (EmployeeDto) new DtoUtils().convertToDto(savedEmployee, new EmployeeDto());
    }

    @Override
    public EmployeeDto updateEmployee(String id, EmployeeDto employeeDto) {

        employeeDto.validate();

        Employee employee = salaryManagementRepository.findById(id).map(s ->
        {
            s.setId(employeeDto.getId());
            s.setName(employeeDto.getName());
            s.setLogin(employeeDto.getLogin());
            s.setSalary(employeeDto.getSalary());
            s.setStartDate(employeeDto.getStartDate());
            return s;
        }).orElse(null);

        if (employee == null) {
            throw new EntityNotFoundException("No such employee");
        }

        this.salaryManagementRepository.save(employee);
        return employeeDto;
    }

    @Override
    public void deleteEmployee(String id) {
        Optional<Employee> employee = salaryManagementRepository.findById(id);
        if (employee.isPresent()) {
            salaryManagementRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No such employee");
        }
    }
}
