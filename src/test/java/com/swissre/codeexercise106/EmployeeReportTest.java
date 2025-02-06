package com.swissre.codeexercise106;




import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.opencsv.exceptions.CsvException;
import com.swissre.codeexercise106.Anomaly;
import com.swissre.codeexercise106.AnomalyType;
import com.swissre.codeexercise106.CsvReader;
import com.swissre.codeexercise106.CsvReaderService;
import com.swissre.codeexercise106.Employee;
import com.swissre.codeexercise106.EmployeeReport;
import com.swissre.codeexercise106.EmployeeReportService;

class EmployeeReportTest {

    @Test
    void generateReport_underpaidManagers() throws IOException, CsvException {
        String filePath = "target/test_underpaid.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Smith,100000,\n" +
                "2,Alice,Johnson,90000,1\n" +
                "3,Bob,Williams,80000,1\n" +
                "4,Eva,Brown,70000,2\n" +
                "5,Michael,Davis,60000,2\n" +
                "6,Sophia,Rodriguez,70000,3\n" +
                "7,Daniel,Wilson,65000,3\n" +
                "8,Mia,Martinez,70000,4\n" +
                "9,David,Anderson,60000,4\n" +
                "10,Olivia,Taylor,51000,5";
        TestUtils.createCsvFile(filePath, csvData);
        EmployeeReportService er = new EmployeeReport();
        CsvReaderService csvReader = new CsvReader();
        List<Employee> employees = csvReader.loadEmployees(filePath);
        List<Anomaly> anomalies = er.generateReport(employees);
        

        assertEquals(4, anomalies.size());        
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 1 && a.getType().equals(AnomalyType.UNDERPAID) && a.getValue() == 2000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 3 && a.getType().equals(AnomalyType.UNDERPAID) && a.getValue() == 1000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 4 && a.getType().equals(AnomalyType.UNDERPAID) && a.getValue() == 8000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 5 && a.getType().equals(AnomalyType.UNDERPAID) && a.getValue() == 1200));
    }

    @Test
    void generateReport_overpaidManagers() throws IOException, CsvException {
        String filePath = "target/test_overpaid.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Smith,110000,\n" +
                "2,Alice,Johnson,90000,1\n" +
                "3,Bob,Williams,80000,1\n" +
                "4,Eva,Brown,20000,2\n" +
                "5,Michael,Davis,20000,2\n" +
                "6,Sophia,Rodriguez,10000,3\n" +
                "7,Daniel,Wilson,10000,3\n" +
                "8,Mia,Martinez,10000,4\n" +
                "9,David,Anderson,10000,4\n" +
                "10,Olivia,Taylor,10000,5";
        TestUtils.createCsvFile(filePath, csvData);
        EmployeeReportService er = new EmployeeReport();
        CsvReaderService csvReader = new CsvReader();
        List<Employee> employees = csvReader.loadEmployees(filePath);
        List<Anomaly> anomalies = er.generateReport(employees);
        
    
        assertEquals(4, anomalies.size());
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 2 && a.getType().equals(AnomalyType.OVERPAID) && a.getValue() == 60000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 3 && a.getType().equals(AnomalyType.OVERPAID)  && a.getValue() == 65000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 4 && a.getType().equals(AnomalyType.OVERPAID) && a.getValue() == 5000));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 5 && a.getType().equals(AnomalyType.OVERPAID) && a.getValue() == 5000));

    }

    @Test
    void generateReport_noIssues() throws IOException, CsvException {
        String filePath = "target/test_no_issues.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Smith,110000,\n" +
                "2,Alice,Johnson,90000,1\n" +
                "3,Bob,Williams,80000,1\n" +
                "4,Eva,Brown,75000,2\n" +
                "5,Michael,Davis,70000,2\n" +
                "6,Sophia,Rodriguez,65000,3\n" +
                "7,Daniel,Wilson,60000,3\n" +
                "8,Mia,Martinez,55000,4\n" +
                "9,David,Anderson,50000,4\n" +
                "10,Olivia,Taylor,50000,5";
        TestUtils.createCsvFile(filePath, csvData);
        EmployeeReportService er = new EmployeeReport();
        CsvReaderService csvReader = new CsvReader();
        List<Employee> employees = csvReader.loadEmployees(filePath);
        List<Anomaly> anomalies = er.generateReport(employees);
        
        assertTrue(anomalies.isEmpty());
    }

    @Test
    void generateReport_level() throws IOException, CsvException {
        String filePath = "target/test_no_issues.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Smith,110000,\n" +
                "2,Alice,Johnson,90000,1\n" +
                "3,Bob,Williams,80000,1\n" +
                "4,Eva,Brown,75000,2\n" +
                "5,Michael,Davis,70000,2\n" +
                "6,Sophia,Rodriguez,65000,3\n" +
                "7,Daniel,Wilson,60000,3\n" +
                "8,Mia,Martinez,55000,4\n" +
                "9,David,Anderson,50000,4\n" +
                "10,Olivia,Taylor,50000,5\n" + 
                "11,Liam,Rodriguez,40000,8\n" + 
                "12,Grace,Garcia,33000,11\n" +
                "13,Amelia,Lopez,45000,6\n" +
                "14,Ethan,Moore,37000,13\n" +
                "15,Lucas,Lee,30000,14";
                
        TestUtils.createCsvFile(filePath, csvData);
        EmployeeReportService er = new EmployeeReport();
        CsvReaderService csvReader = new CsvReader();
        List<Employee> employees = csvReader.loadEmployees(filePath);
        List<Anomaly> anomalies = er.generateReport(employees);
        
        assertEquals(2, anomalies.size());
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 12 && a.getType().equals(AnomalyType.REPORTLINE) && a.getValue() == 1));
        assertTrue(anomalies.stream().anyMatch(a -> a.getEmployee().getId() == 15 && a.getType().equals(AnomalyType.REPORTLINE) && a.getValue() == 1));
    }
}
