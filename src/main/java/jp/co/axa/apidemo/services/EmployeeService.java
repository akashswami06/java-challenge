package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.ResourceNotFoundException;

import java.util.List;

public interface EmployeeService {

    public List<Employee> retrieveEmployees();

    public Employee getEmployee(Long employeeId) throws ResourceNotFoundException;

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long employeeId) throws ResourceNotFoundException;

    public void updateEmployee(Employee employee);
}