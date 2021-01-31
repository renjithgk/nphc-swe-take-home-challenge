package com.gmail.renjithkumar1.salarymanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class FetchEmployeesDto {

    private List<EmployeeDto> results;
}
