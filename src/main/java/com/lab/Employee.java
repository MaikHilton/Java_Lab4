package com.lab;

import java.util.Objects;

/**
 * Клас, що представляє працівника.
 * Містить логіку валідації даних, статичний лічильник об'єктів
 * та конструктор копіювання.
 */
public class Employee {

    /**
     * Статичне поле для підрахунку загальної кількості створених об'єктів Employee.
     */
    private static int totalEmployees = 0;

    private String firstName;
    private String lastName;
    private String department;
    private Position position;
    private double salary;
    private int experienceYears;

    /**
     * Основний конструктор для створення нового працівника.
     *
     * @param firstName       Ім'я працівника
     * @param lastName        Прізвище працівника
     * @param department      Відділ
     * @param position        Посада (перерахування)
     * @param salary          Зарплата
     * @param experienceYears Стаж роботи в роках
     * @throws IllegalArgumentException якщо передані некоректні дані
     */
    public Employee(String firstName, String lastName, String department, Position position, double salary, int experienceYears) {
        setFirstName(firstName);
        setLastName(lastName);
        setDepartment(department);
        setPosition(position);
        setSalary(salary);
        setExperienceYears(experienceYears);

        totalEmployees++; // Збільшуємо лічильник при успішному створенні
    }

    /**
     * Конструктор копіювання.
     * Створює нового працівника на основі існуючого об'єкта.
     *
     * @param other об'єкт працівника, дані якого потрібно скопіювати
     * @throws IllegalArgumentException якщо переданий об'єкт null
     */
    public Employee(Employee other) {
        if (other == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null");
        }
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.department = other.department;
        this.position = other.position;
        this.salary = other.salary;
        this.experienceYears = other.experienceYears;

        totalEmployees++; // Збільшуємо лічильник, адже створено новий об'єкт
    }

    /**
     * Статичний метод для отримання загальної кількості створених працівників.
     * @return кількість створених об'єктів Employee
     */
    public static int getTotalEmployees() {
        return totalEmployees;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ім'я не може бути порожнім.");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Прізвище не може бути порожнім.");
        }
        this.lastName = lastName.trim();
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Відділ не може бути порожнім.");
        }
        this.department = department.trim();
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Посада не може бути порожньою (null).");
        }
        this.position = position;
    }

    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Зарплата має бути більшою за нуль.");
        }
        this.salary = salary;
    }

    public void setExperienceYears(int experienceYears) {
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Стаж не може бути від'ємним.");
        }
        this.experienceYears = experienceYears;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public Position getPosition() { return position; }
    public double getSalary() { return salary; }
    public int getExperienceYears() { return experienceYears; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Double.compare(employee.salary, salary) == 0 &&
                experienceYears == employee.experienceYears &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(department, employee.department) &&
                position == employee.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, department, position, salary, experienceYears);
    }

    @Override
    public String toString() {
        return String.format("Employee { %s %s | відділ: %s | посада: %s | з/п: %.2f грн | стаж: %d р. }",
                firstName, lastName, department, position, salary, experienceYears);
    }
}