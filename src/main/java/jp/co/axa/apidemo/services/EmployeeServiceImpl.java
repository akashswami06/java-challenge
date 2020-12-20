package jp.co.axa.apidemo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.ResourceNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Cacheable("employees")
	public List<Employee> retrieveEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		if (employees.size() > 0) {
			return employees;
		} else {
			return new ArrayList<Employee>();
		}
	}

	@Cacheable("optEmp")
	public Employee getEmployee(Long employeeId) throws ResourceNotFoundException {
		Employee optEmp = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + employeeId));
		return optEmp;
	}

	@Caching(evict = { @CacheEvict(value = "optEmp", allEntries = true),
			@CacheEvict(value = "employees", allEntries = true) })
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Caching(evict = { @CacheEvict(value = "optEmp", allEntries = true),
			@CacheEvict(value = "employees", allEntries = true) })
	public void deleteEmployee(Long employeeId) throws ResourceNotFoundException {
		Employee optEmp = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + employeeId));
	
		employeeRepository.delete(optEmp);
	}

	@Caching(evict = { @CacheEvict(value = "optEmp", allEntries = true),
			@CacheEvict(value = "employees", allEntries = true) })
	public void updateEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
}