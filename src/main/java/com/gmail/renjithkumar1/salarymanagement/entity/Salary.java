package com.gmail.renjithkumar1.salarymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Salary extends EntityBase {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "login", nullable = false)
    private String login;

    @Positive
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

}
