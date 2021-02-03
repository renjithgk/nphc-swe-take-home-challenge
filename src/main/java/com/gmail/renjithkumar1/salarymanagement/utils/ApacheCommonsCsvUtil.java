package com.gmail.renjithkumar1.salarymanagement.utils;

import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.InvalidFileDataException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ApacheCommonsCsvUtil {
    private static String csvExtension = "csv";

    public static List<Employee> parseCsvFile(InputStream is) throws IOException {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<Employee> employees = new ArrayList<>();

        try {
            fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                if (!csvRecord.get(0).startsWith("#")) {
                    Employee employee = new Employee();
                    employee.setId(csvRecord.get("id"));
                    employee.setLogin(csvRecord.get("login"));
                    employee.setName(csvRecord.get("name"));
                    employee.setSalary(new BigDecimal(csvRecord.get("salary")));
                    employee.setStartDate(csvRecord.get("startDate"));
                    employees.add(employee);
                }
            }

        } catch (Exception e) {
            throw new InvalidFileDataException("Invalid data in the file " + e.getMessage());
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        return employees;
    }

    public static Integer getColumnCount(InputStream is) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        return csvParser.getHeaderMap().size();
    }

    public static boolean isCSVFile(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1];
        if (!extension.equals(csvExtension)) {
            return false;
        }
        return true;
    }
}