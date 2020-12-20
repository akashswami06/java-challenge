package jp.co.axa.apidemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiDemoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private static String path = "/api/v1/employees/";

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password").exchange(getRootUrl() + path,
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		int id = 1;
		Employee employee = restTemplate.withBasicAuth("user", "password").getForObject(getRootUrl() + path + id,
				Employee.class);
		assertNotNull(employee);
	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setName("XYZ");
		employee.setDepartment("DepartXYZ");
		employee.setSalary(30000);
		ResponseEntity<Employee> postResponse = restTemplate.withBasicAuth("admin", "password")
				.postForEntity(getRootUrl() + path, employee, Employee.class);
		assertNotNull(postResponse);
		//assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee() {
		int id = 1;
		Employee employee = restTemplate.withBasicAuth("user", "password").getForObject(getRootUrl() + path + id,
				Employee.class);
		employee.setName("Akki4");
		employee.setDepartment("5Depart");
		employee.setSalary(10110);
		restTemplate
		.withBasicAuth("admin", "password")
		.put(getRootUrl() + path + id, employee);
		Employee updatedEmployee = restTemplate.withBasicAuth("user", "password")
				.getForObject(getRootUrl() + path + id, Employee.class);
		assertNotNull(updatedEmployee);
		assertTrue(updatedEmployee.getName().equals("Akki4"));
		assertTrue(updatedEmployee.getDepartment().equals("5Depart"));

	}

	@Test
	public void testDeleteEmployee() {
		int id = 2;
			Employee employee = restTemplate.withBasicAuth("user", "password").getForObject(getRootUrl() + path + id,
					Employee.class);
			assertNotNull(employee);
			restTemplate.withBasicAuth("admin", "password").delete(getRootUrl() + path + id);
			Employee deletedEmployee = restTemplate.withBasicAuth("user", "password")
					.getForObject(getRootUrl() + path + id, Employee.class);
			assertTrue(null==deletedEmployee.getDepartment());

	}

}
