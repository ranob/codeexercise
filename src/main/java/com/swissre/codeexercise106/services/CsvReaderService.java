package com.swissre.codeexercise106.services;

import java.io.IOException;
import java.util.List;
import com.opencsv.exceptions.CsvException;
import com.swissre.codeexercise106.model.Employee;

public interface CsvReaderService {

      List<Employee> loadEmployees(String filePath) throws IOException, CsvException;
 
}
