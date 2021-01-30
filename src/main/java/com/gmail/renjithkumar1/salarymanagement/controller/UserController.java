package com.gmail.renjithkumar1.salarymanagement.controller;

import com.gmail.renjithkumar1.salarymanagement.dto.SalaryDto;
import com.gmail.renjithkumar1.salarymanagement.service.SalaryManagementCommandService;
import com.gmail.renjithkumar1.salarymanagement.service.SalaryManagementQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/users"}, produces = APPLICATION_JSON_VALUE)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String ID = "tradeId";
    private static final String NEW_SALARY_LOG = "New salary record was created as :{}";
    private static final String FETCH_SALARY_LOG = "Salary record fetched as : {}";
    private final SalaryManagementQueryService salaryManagementQueryService;
    private final SalaryManagementCommandService salaryManagementCommandService;

    @Autowired
    public UserController(SalaryManagementCommandService salaryManagementCommandService, SalaryManagementQueryService salaryManagementQueryService) {
        this.salaryManagementCommandService = salaryManagementCommandService;
        this.salaryManagementQueryService = salaryManagementQueryService;
    }


    @Operation(summary = "Fetch an existing employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the employee record", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SalaryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Employee record not found", content = @Content)})
    @GetMapping()
    public ResponseEntity<SalaryDto> get(@RequestParam("id") Long id) {
        final Pair<Integer,SalaryDto> salaryDto = salaryManagementQueryService.getSalary(id);
        if (salaryDto == null) {
            return ResponseEntity.notFound().build();
        }
        logger.info(FETCH_SALARY_LOG, salaryDto.toString());
        //Publish Event : MarketSettlement Message Retrieved with Fetched Message JSON
        return ResponseEntity.ok(salaryDto.getValue1());
    }



    @Operation(summary = "Creates a new employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New employee record created", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SalaryDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad input", content = @Content),
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<SalaryDto> create(@Valid @RequestBody SalaryDto salaryDto) {

        final Pair<Integer, SalaryDto> createdMessage = salaryManagementCommandService.CreateSalary(salaryDto);
        if (createdMessage.getValue0() == 404) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (createdMessage.getValue0() == 409) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        logger.info(NEW_SALARY_LOG, createdMessage.toString());
        //Publish Event : New employee record created event with JSON data to be published
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage.getValue1());
    }
}
