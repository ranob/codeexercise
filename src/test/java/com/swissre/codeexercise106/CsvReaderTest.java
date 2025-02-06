package com.swissre.codeexercise106;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvException;
import com.swissre.codeexercise106.CsvReader;
import com.swissre.codeexercise106.CsvReaderService;
import com.swissre.codeexercise106.Employee;

class CsvReaderTest {

    @Test
    void loadEmployees_success() throws IOException, CsvException {
        // Crear un archivo CSV de prueba
        String filePath = "target/test.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Doe,50000,2\n" +
                "2,Jane,Smith,60000,\n" +
                "3,Peter,Jones,70000,2";
        TestUtils.createCsvFile(filePath, csvData);

        CsvReaderService csvReader = new CsvReader();
        List<Employee> employees = csvReader.loadEmployees(filePath);

        assertEquals(3, employees.size());
        assertEquals(1, employees.get(0).getId());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("Doe", employees.get(0).getLastName());
        assertEquals(50000.0, employees.get(0).getSalary());
        assertEquals(2, employees.get(0).getManagerId());

        assertEquals(2, employees.get(1).getId());
        assertEquals("Jane", employees.get(1).getFirstName());
        assertEquals("Smith", employees.get(1).getLastName());
        assertEquals(60000.0, employees.get(1).getSalary());
        assertNull(employees.get(1).getManagerId());

        assertEquals(3, employees.get(2).getId());
        assertEquals("Peter", employees.get(2).getFirstName());
        assertEquals("Jones", employees.get(2).getLastName());
        assertEquals(2, employees.get(2).getManagerId());
    }

    @Test
    void loadEmployees_emptyFile() throws IOException, CsvException {
        String filePath = "target/empty.csv";
        TestUtils.createCsvFile(filePath, "");

        CsvReaderService csvReader = new CsvReader();
	    List<Employee> employees = csvReader.loadEmployees(filePath);

        assertTrue(employees.isEmpty());
    }

    @Test
    void loadEmployees_invalidSalary() throws IOException, CsvException {
        String filePath = "target/invalid_salary.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,Doe,abc,2";
        TestUtils.createCsvFile(filePath, csvData);

        CsvReaderService csvReader = new CsvReader();
        assertThrows(NumberFormatException.class, () -> csvReader.loadEmployees(filePath));
    }

    @Test
    void loadEmployees_missingData() throws IOException, CsvException {
        String filePath = "target/missing_data.csv";
        String csvData = "id,firstName,lastName,salary,managerId\n" +
                "1,John,50000,2";
        TestUtils.createCsvFile(filePath, csvData);
        CsvReaderService csvReader = new CsvReader();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> csvReader.loadEmployees(filePath));
    }

    @Test
    void loadEmployees_fileNotFound() {
        String filePath = "target/nonexistent.csv";
        CsvReaderService csvReader = new CsvReader();
        assertThrows(IOException.class, () -> csvReader.loadEmployees(filePath));        
    }
}