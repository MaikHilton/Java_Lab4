package com.lab;

/**
 * Базовий клас, що представляє загальну інформацію про працівника.
 * Використовується як основа для ієрархії класів працівників.
 */
public class Employee {
    private String firstName;
    private String lastName;
    private double salary;

    /**
     * Конструктор для створення об'єкта Employee.
     * @param firstName Ім'я працівника
     * @param lastName Прізвище працівника
     * @param salary Базова заробітна плата
     */
    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }

    /**
     * Повертає рядкове представлення об'єкта.
     * Демонструє базову логіку, яка буде перевизначена в нащадках.
     * @return Інформація про працівника у вигляді рядка
     */
    @Override
    public String toString() {
        return String.format("Працівник: %s %s, Базова ставка: %.2f", firstName, lastName, salary);
    }
}