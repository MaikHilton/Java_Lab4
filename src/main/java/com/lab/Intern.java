package com.lab;

/**
 * Клас, що представляє стажера.
 * Успадковується напряму від базового класу Employee.
 */
public class Intern extends Employee {
    private String universityName;

    public Intern(String firstName, String lastName, double salary, String universityName) {
        super(firstName, lastName, salary); // Виклик конструктора Employee
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return String.format("[Стажер] %s %s, Стипендія: %.2f, Університет: %s",
                getFirstName(), getLastName(), getSalary(), universityName);
    }
}