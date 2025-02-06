package com.swissre.codeexercise106;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.exceptions.CsvException;

public class CodeExercise106 {

	private static final Logger logger = LoggerFactory.getLogger(CodeExercise106.class);

	public void execute(String filepath) {

		logger.info("-->execute");
		CsvReaderService csvReader = new CsvReader();
		List<Employee> employees;
		List<Anomaly> report = null;
		try {
			employees = csvReader.loadEmployees(filepath);

			EmployeeReportService ce = new EmployeeReport();
			report = ce.generateReport(employees);
            logger.info("##########################################################");
			logger.info("#################### Report results ######################");
			logger.info("##########################################################");
			if (report.isEmpty()) {
				logger.info("No anomalies found.");
			} else {
				for (Anomaly anomaly : report) {
					if (anomaly.getType().equals(AnomalyType.UNDERPAID)) {
						logger.info("{} is underpaid for {}",anomaly.getEmployee(),anomaly.getValue());
								
					} else if (anomaly.getType().equals(AnomalyType.OVERPAID)) {
						logger.info("{} is overpaid for {}",anomaly.getEmployee(),anomaly.getValue());					
					} else {
						logger.info("{} . The reporter line is {} levels deeper that it should be.",anomaly.getEmployee(),anomaly.getValue());							
					}
				}
			}
		} catch (IOException | CsvException e) {
			logger.error("Error reading the csvfile: {}", e.toString());
		}
		if (report!=null) {
			logger.info("#### Total Anomalies: {}",report.size());
		}
		logger.info("<--execute");
	}
}
