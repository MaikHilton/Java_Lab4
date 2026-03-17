package com.lab;

import java.util.Objects;

/**
 * Клас Employee — представляє працівника організації.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String position;
    private double salary;
    private int experienceYears;

    // Конструктор з параметрами

    public Employee(String firstName, String lastName,
                    String position, double salary, int experienceYears) {
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.position        = position;
        this.salary          = salary;
        this.experienceYears = experienceYears;
    }

    // Гетери

    public String getFirstName()      { return firstName; }
    public String getLastName()       { return lastName; }
    public String getPosition()       { return position; }
    public double getSalary()         { return salary; }
    public int    getExperienceYears(){ return experienceYears; }

    // Сетери

    public void setFirstName(String firstName)           { this.firstName = firstName; }
    public void setLastName(String lastName)             { this.lastName  = lastName; }
    public void setPosition(String position)             { this.position  = position; }
    public void setSalary(double salary)                 { this.salary    = salary; }
    public void setExperienceYears(int experienceYears)  { this.experienceYears = experienceYears; }

    // toString

    @Override
    public String toString() {
        return String.format(
            "Employee { %-12s %-15s | посада: %-20s | зарплата: %8.2f грн | стаж: %d р. }",
            firstName, lastName, position, salary, experienceYears
        );
    }

    // equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee emp = (Employee) o;
        return Double.compare(emp.salary, salary) == 0
            && experienceYears == emp.experienceYears
            && Objects.equals(firstName,       emp.firstName)
            && Objects.equals(lastName,        emp.lastName)
            && Objects.equals(position,        emp.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, position, salary, experienceYears);
    }
}
