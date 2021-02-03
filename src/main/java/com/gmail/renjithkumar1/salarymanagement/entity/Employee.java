package com.gmail.renjithkumar1.salarymanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends EntityBase {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;


    @Column(name = "login", nullable = false, unique = true)
    @NotEmpty
    private String login;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "salary", nullable = false)
    @DecimalMin("0.0")
    private BigDecimal salary;

    @Column(name = "startDate", nullable = false)
    @NotEmpty
    private String startDate;

}
