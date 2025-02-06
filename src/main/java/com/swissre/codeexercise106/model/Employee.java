package com.swissre.codeexercise106.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter  @NoArgsConstructor @ToString
public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private double salary;
	private Integer managerId;

	public Employee(int id, String firstName, String lastName, double salary, String managerId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.managerId = managerId.isEmpty() ? null : Integer.parseInt(managerId);
	}
}