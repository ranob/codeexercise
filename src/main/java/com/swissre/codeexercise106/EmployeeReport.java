package com.swissre.codeexercise106;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeReport implements EmployeeReportService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeReport.class);

	private void initCEOLevel(List<Employee> employees,Map<Integer, Integer> employeeLevels)
	{
		logger.debug("-->generateReport");
		boolean ceoFound = false;
		for (Employee employee : employees) {
			if (employee.getManagerId() == null) {
				employeeLevels.put(employee.getId(), 1);
				ceoFound = true;
			}
			if (ceoFound) {
				break;
			}
		}		
		logger.debug("<--generateReport");
	}

	private void calculateEmployLevelsAndGroupSalariesByManager(List<Employee> employees,Map<Integer, List<Double>> managerSalaries,Map<Integer, Integer> employeeLevels){
		logger.debug("-->calculateEmployLevelsAndGroupSalariesByManager");
		for (Employee employee : employees) {
			//Calculate employee level
			if (employee.getManagerId() != null && employeeLevels.containsKey(employee.getManagerId())
					&& !employeeLevels.containsKey(employee.getId())) {
				employeeLevels.put(employee.getId(), employeeLevels.get(employee.getManagerId()) + 1);
				
			}
			//Group employee salaries by manager employee salaries
			if (employee.getManagerId() != null) {
				managerSalaries.computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>())
						.add(employee.getSalary());
			}
		}
		logger.debug("<--calculateEmployLevelsAndGroupSalariesByManager");
	}
	
	private void performChecks(List<Employee> employees,Map<Integer, List<Double>> managerSalaries,Map<Integer, Integer> employeeLevels,List<Anomaly> anomalies){
		logger.debug("-->performChecks");
		for (Employee employee : employees) {
			// Get the employee level or 0 if it´s not defined
			int level = employeeLevels.getOrDefault(employee.getId(), 0); 

			if (level > 5) {
				anomalies.add(new Anomaly(employee,AnomalyType.REPORTLINE,(level-1)-4.0));
			}
			if (managerSalaries.containsKey(employee.getId())) { // Es gerente
				List<Double> salaries = managerSalaries.get(employee.getId());
				double averageSalary = salaries.stream().mapToDouble(d -> d).average().orElse(0);

				if (employee.getSalary() < averageSalary * 1.2) {
					anomalies.add(new Anomaly(employee,AnomalyType.UNDERPAID,((averageSalary * 1.2) - employee.getSalary())));
				}

				if (employee.getSalary() > averageSalary * 1.5) {
					anomalies.add(new Anomaly(employee,AnomalyType.OVERPAID,(employee.getSalary()-averageSalary * 1.5)));
				}
			}
		}
		logger.debug("<--performChecks");
	}
	
	public List<Anomaly> generateReport(List<Employee> employees) {
		logger.debug("-->generateReport");
		List<Anomaly> anomalies = new ArrayList<>();
		Map<Integer, List<Double>> managerSalaries = new HashMap<>();
		Map<Integer, Integer> employeeLevels = new HashMap<>(); // Mapa para almacenar el nivel de cada empleado

		// Initialize the level of CEO (without a manager) to 1
		initCEOLevel(employees,employeeLevels);

		// Calculate employee levels and group salaries by manager
		calculateEmployLevelsAndGroupSalariesByManager(employees,managerSalaries,employeeLevels);
		

		// Identify managers that earn less and more more that they should and employes with level deeper than 4 
		performChecks(employees, managerSalaries, employeeLevels,anomalies);
		
		logger.debug("<--generateReport");
		return anomalies;
	}

}
