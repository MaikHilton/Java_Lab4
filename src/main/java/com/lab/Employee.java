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
    private String department; // Новий атрибут для 5-ї лаби

    /**
     * Конструктор з перевіркою параметрів.
     */
    public Employee(String firstName, String lastName, String position,
                    double salary, int experienceYears, String department) {
        setFirstName(firstName);
        setLastName(lastName);
        setPosition(position);
        setSalary(salary);
        setExperienceYears(experienceYears);
        setDepartment(department);
    }

    // --- ГЕТЕРИ ---
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public int getExperienceYears() { return experienceYears; }
    public String getDepartment() { return department; }

    // --- СЕТЕРИ (з валідацією) ---

    /**
     * Встановлює ім'я працівника. Не може бути порожнім.
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім.");
        }
        this.firstName = firstName.trim();
    }

    /**
     * Встановлює прізвище працівника. Не може бути порожнім.
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Прізвище не може бути порожнім.");
        }
        this.lastName = lastName.trim();
    }

    /**
     * Встановлює посаду. Не може бути порожньою.
     */
    public void setPosition(String position) {
        if (position == null || position.isBlank()) {
            throw new IllegalArgumentException("Посада не може бути порожньою.");
        }
        this.position = position.trim();
    }

    /**
     * Встановлює зарплату. Має бути >= 0.
     */
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Зарплата не може бути від'ємною.");
        }
        this.salary = salary;
    }

    /**
     * Встановлює стаж. Має бути >= 0.
     */
    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Стаж не може бути від'ємним.");
        }
        this.experienceYears = experienceYears;
    }

    /**
     * Встановлює відділ. Не може бути порожнім.
     */
    public void setDepartment(String department) {
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("Відділ не може бути порожнім.");
        }
        this.department = department.trim();
    }

    @Override
    public String toString() {
        return String.format(
                "Employee { %-10s %-12s | відділ: %-10s | посада: %-15s | з/п: %8.2f грн | стаж: %d р. }",
                firstName, lastName, department, position, salary, experienceYears
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee emp = (Employee) o;
        return Double.compare(emp.salary, salary) == 0
                && experienceYears == emp.experienceYears
                && Objects.equals(firstName, emp.firstName)
                && Objects.equals(lastName, emp.lastName)
                && Objects.equals(position, emp.position)
                && Objects.equals(department, emp.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, position, salary, experienceYears, department);
    }
}