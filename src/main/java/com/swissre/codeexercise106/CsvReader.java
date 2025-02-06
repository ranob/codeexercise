package com.swissre.codeexercise106;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class CsvReader implements CsvReaderService{

    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public List<Employee> loadEmployees(String filePath) throws IOException, CsvException {
        logger.info("-->loadEmployees");
        List<Employee> employees = new ArrayList<>();
        
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).build()) {
            reader.readNext(); // Skip header
            String[] data;
            while ((data = reader.readNext()) != null) {
                try {
                    Employee employee = new Employee(Integer.parseInt(data[0]), data[1], data[2],
                            Double.parseDouble(data[3]), data[4]);
                    employees.add(employee);
                    logger.debug("Employee:{}",employee);
                } catch (NumberFormatException e) {                   
                    throw e;
                } catch (ArrayIndexOutOfBoundsException e2) {                    
                    throw e2;
                }
            }
        }
        logger.info("<--loadEmployees");
        return employees;
    }
}
