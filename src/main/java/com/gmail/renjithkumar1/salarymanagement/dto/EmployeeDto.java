package com.gmail.renjithkumar1.salarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gmail.renjithkumar1.salarymanagement.exception.InvalidPropertyValueException;
import com.gmail.renjithkumar1.salarymanagement.utils.DtoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name", "login", "salary", "startDate"})
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto implements DtoEntity {

    @NotNull
    private String id;

    @NotNull
    private String login;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal salary;

    //@PastOrPresent
    @NotNull
    private String startDate;

    public boolean validate() {
        try {
            if (this.salary.signum() < 0) {
                throw new InvalidPropertyValueException("Invalid salary");
            }
        } catch (NumberFormatException e) {
            throw new InvalidPropertyValueException("Invalid salary");
        }

        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(startDate);
            if (date != null) {
            }
        } catch (DateTimeParseException ex) {
            throw new InvalidPropertyValueException("Invalid date");
        }
        if (date == null) {
            throw new InvalidPropertyValueException("Invalid date");
        }
        return true;
    }
}
