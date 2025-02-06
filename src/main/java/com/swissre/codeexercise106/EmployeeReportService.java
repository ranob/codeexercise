package com.swissre.codeexercise106;

import java.util.List;

public interface EmployeeReportService {
    List<Anomaly> generateReport(List<Employee> employees);

}
