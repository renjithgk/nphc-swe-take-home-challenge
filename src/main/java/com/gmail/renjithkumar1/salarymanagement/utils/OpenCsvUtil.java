package com.gmail.renjithkumar1.salarymanagement.utils;

import com.gmail.renjithkumar1.salarymanagement.entity.Employee;
import com.gmail.renjithkumar1.salarymanagement.exception.InvalidFileDataException;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class OpenCsvUtil {

    public static List<Employee> parseCsvFile(InputStream is) throws IOException {

        String[] CSV_HEADER = {"id", "login", "name", "salary", "startDate"};
        Reader fileReader = null;
        CsvToBean<Employee> csvToBean;
        List<Employee> employees;

        try {
            fileReader = new InputStreamReader(is);
            ColumnPositionMappingStrategy<Employee> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(Employee.class);
            mappingStrategy.setColumnMapping(CSV_HEADER);
            csvToBean = new CsvToBeanBuilder<Employee>(fileReader).withMappingStrategy(mappingStrategy).withSkipLines(1).withIgnoreLeadingWhiteSpace(true).build();
            employees = csvToBean.parse();
            return employees;
        } catch (Exception e) {
            throw new InvalidFileDataException("Invalid data in the file " + e.getMessage());
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
