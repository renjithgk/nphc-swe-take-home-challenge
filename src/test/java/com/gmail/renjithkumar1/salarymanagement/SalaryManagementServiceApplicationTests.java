package com.gmail.renjithkumar1.salarymanagement;

import com.gmail.renjithkumar1.salarymanagement.dto.EmployeeDto;
import com.gmail.renjithkumar1.salarymanagement.dto.FetchEmployeesDto;
import com.gmail.renjithkumar1.salarymanagement.repository.SalaryManagementRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalaryManagementServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaryManagementServiceApplicationTests {

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private SalaryManagementRepository salaryManagementRepository;

    @Before
    public void setup() {
        salaryManagementRepository.deleteAll();
    }

    @Test
    public void should_be_able_to_create_an_employee() {
        //Arrange
        EmployeeDto aEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(aEmployeeDto, headers);

        //Act
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);

        //Assert
        assertThat(response.getStatusCodeValue() == 201);
        assertThat(response.getBody().equals(aEmployeeDto));

    }

    @Test
    public void should_be_able_to_retrieve_an_employee() {
        //Arrange
        EmployeeDto anEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);
        HttpEntity<EmployeeDto> employeeDto = new HttpEntity<>(null, headers);
        //Act
        ResponseEntity<EmployeeDto> response = restTemplate.exchange(createURLWithPort("/api/v1/users/emp0001"), HttpMethod.GET, employeeDto, EmployeeDto.class);

        //Assert
        assertThat(response.getBody().getId().equals("emp0001"));
    }

    @Test
    public void should_be_able_to_retrieve_employees() {
        //Arrange
        EmployeeDto anEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);
        anEmployeeDto = getAEmployeeDto("emp0002");
        anEmployeeDto.setLogin("login2");
        entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);

        HttpEntity<FetchEmployeesDto> employeeDtos = new HttpEntity<>(null, headers);
        //Act
        ResponseEntity<FetchEmployeesDto> response = restTemplate.exchange(createURLWithPort("/api/v1/users/"), HttpMethod.GET, employeeDtos, FetchEmployeesDto.class);

        //Assert
        assertThat(response.getBody().getResults().size() == 2);
    }

    @Test
    public void should_not_be_able_to_create_employees_with_same_login() {
        //Arrange
        EmployeeDto anEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);
        anEmployeeDto = getAEmployeeDto("emp0002");
        entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);

        HttpEntity<FetchEmployeesDto> employeeDtos = new HttpEntity<>(null, headers);
        //Act
        ResponseEntity<FetchEmployeesDto> response = restTemplate.exchange(createURLWithPort("/api/v1/users/"), HttpMethod.GET, employeeDtos, FetchEmployeesDto.class);

        //Assert
        assertThat(response.getBody().getResults().size() == 1);
    }

    @Test
    public void should_be_able_to_edit_an_employee() {
        //Arrange
        EmployeeDto anEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);

        HttpEntity<EmployeeDto> employeeDto = new HttpEntity<>(null, headers);
        anEmployeeDto.setSalary(BigDecimal.valueOf(10000));
        //Act
        ResponseEntity<EmployeeDto> response = restTemplate.exchange(createURLWithPort("/api/v1/users/emp0001"), HttpMethod.PUT, employeeDto, EmployeeDto.class);

        //Assert
        assertThat(response.getStatusCodeValue() == 201);
        assertThat(response.getBody().getSalary() == BigDecimal.valueOf(10000));

    }

    @Test
    public void should_be_able_to_delete_an_employee() {
        //Arrange
        EmployeeDto anEmployeeDto = getAEmployeeDto("emp0001");
        HttpEntity<EmployeeDto> entity = new HttpEntity<>(anEmployeeDto, headers);
        restTemplate.exchange(createURLWithPort("/api/v1/users"), HttpMethod.POST, entity, String.class);

        HttpEntity<EmployeeDto> employeeDto = new HttpEntity<>(null, headers);

        //Act
        restTemplate.exchange(createURLWithPort("/api/v1/users/emp0001"), HttpMethod.DELETE, employeeDto, String.class);
        ResponseEntity<EmployeeDto> response = restTemplate.exchange(createURLWithPort("/api/v1/users/emp0001"), HttpMethod.GET, employeeDto, EmployeeDto.class);

        //Assert
        assertThat(response.getStatusCodeValue() == 201);
        assertThat(response.getBody().equals(null));

    }

	@Ignore
	public void should_be_able_to_upload_data_from_csv() throws Exception {
		String data = "id,login,name,salary,startDate\ne0001,hpotter,Harry Potter,1234.00,16-Nov-01";
		MockMultipartFile file = new MockMultipartFile("file", "data.csv", null, data.getBytes());
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/users/upload", file).file(file).characterEncoding("UTF-8")).andExpect(MockMvcResultMatchers.status().isOk());
	}

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private EmployeeDto getAEmployeeDto(String id) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);
        employeeDto.setName("Harry Potter");
        employeeDto.setLogin("hpotter");
        employeeDto.setSalary(BigDecimal.valueOf(1234.00));
        employeeDto.setStartDate("2016-11-16");
        return employeeDto;
    }
}
