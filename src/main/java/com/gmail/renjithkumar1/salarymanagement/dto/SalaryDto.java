package com.gmail.renjithkumar1.salarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "login", "salary", "startDate" })
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto implements DtoEntity {

    private String name;

    private String login;

    private BigDecimal salary;

    private Date startDate;
}
