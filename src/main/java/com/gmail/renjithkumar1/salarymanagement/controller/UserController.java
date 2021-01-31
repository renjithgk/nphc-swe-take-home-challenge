package com.gmail.renjithkumar1.salarymanagement.controller;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.dto.FetchEmployeesDto;
import com.gmail.renjithkumar1.salarymanagement.service.SalaryManagementCommandService;
import com.gmail.renjithkumar1.salarymanagement.service.SalaryManagementQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private static final String FETCH_EMPLOYEES_LOG = "Employee records fetched as : {}";
    private static final String NEW_EMPLOYEE_LOG = "New employee record created as :{}";
    private static final String FETCH_EMPLOYEE_LOG = "Employee record fetched as : {}";
    private static final String UPDATE_EMPLOYEE_LOG = "Employee record updated as :{}";
    private static final String DELETE_EMPLOYEE_LOG = "Employee record deleted as :{}";

    private final SalaryManagementQueryService salaryManagementQueryService;
    private final SalaryManagementCommandService salaryManagementCommandService;

    @Autowired
    public UserController(SalaryManagementCommandService salaryManagementCommandService, SalaryManagementQueryService salaryManagementQueryService) {
        this.salaryManagementCommandService = salaryManagementCommandService;
        this.salaryManagementQueryService = salaryManagementQueryService;
    }


    @Operation(summary = "Fetch all existing employee records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the employee record", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "404", description = "Employee record not found", content = @Content)})
    @GetMapping(value = "/")
    public ResponseEntity<FetchEmployeesDto> fetch() {
        final FetchEmployeesDto fetchEmployeesDto = new FetchEmployeesDto();
        fetchEmployeesDto.setResults(salaryManagementQueryService.getEmployees());
        logger.info(FETCH_EMPLOYEES_LOG, fetchEmployeesDto.toString());
        //Publish Event : Employees Retrieved with Fetched Message JSON
        return ResponseEntity.ok(fetchEmployeesDto);
    }

    @Operation(summary = "Get an existing employee record based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get the employee record", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "400", description = "No such employee found", content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable String id) {
        final EmployeeDto employeeDto = salaryManagementQueryService.getEmployee(id);
        logger.info(FETCH_EMPLOYEE_LOG, employeeDto.toString());
        //Publish Event : Employee Retrieved with Fetched Message JSON
        return ResponseEntity.ok(employeeDto);
    }


    @Operation(summary = "Creates a new employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New employee record created", content = {@Content(mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad input", content = @Content),
    })
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody EmployeeDto employeeDto) {
        final EmployeeDto createdEmployeeDto = salaryManagementCommandService.createEmployee(employeeDto);
        logger.info(NEW_EMPLOYEE_LOG, createdEmployeeDto.toString());
        //Publish Event : New employee record created event with JSON data to be published
        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
    }

    @Operation(summary = "Update an existing  employee record based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee record updated", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad input", content = @Content),
    })
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@Valid @RequestBody EmployeeDto employeeDto, @PathVariable String id) {
        final EmployeeDto updateEmployee = salaryManagementCommandService.updateEmployee(id, employeeDto);
        logger.info(UPDATE_EMPLOYEE_LOG, updateEmployee.toString());
        //Publish Event : Employee record updated event with JSON data to be published
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully updated");
    }


    @Operation(summary = "Delete a employee record based on Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New employee record created", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad input", content = @Content),
    })
    @DeleteMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        salaryManagementCommandService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successfully deleted");
    }
}
