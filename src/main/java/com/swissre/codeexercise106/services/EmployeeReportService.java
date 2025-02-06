package com.swissre.codeexercise106.services;

import java.util.List;

import com.swissre.codeexercise106.model.Anomaly;
import com.swissre.codeexercise106.model.Employee;

public interface EmployeeReportService {
    List<Anomaly> generateReport(List<Employee> employees);

}
