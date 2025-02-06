package com.swissre.codeexercise106;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @Setter  @NoArgsConstructor @ToString
public class Anomaly {
    private Employee employee;
    private AnomalyType type;
    private double value;

    public Anomaly(Employee employee, AnomalyType type,double value) {
        this.employee = employee;
        this.type = type;
        this.value = value;
    }
}
