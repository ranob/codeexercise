package com.swissre.codeexercise106;

import java.io.IOException;
import java.util.List;
import com.opencsv.exceptions.CsvException;

public interface CsvReaderService {

      List<Employee> loadEmployees(String filePath) throws IOException, CsvException;
 
}
