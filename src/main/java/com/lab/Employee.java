package com.lab;

/**
 * Базовий клас, що представляє загальну інформацію про працівника.
 * Є коренем ієрархії для всіх інших типів працівників.
 */
public class Employee {
    /** Маркер типу класу для коректної десеріалізації з JSON */
    private String type = "Employee";
    private String firstName;
    private String lastName;
    private double salary;

    /**
     * Конструктор для ініціалізації базових атрибутів працівника.
     *
     * @param firstName Ім'я працівника
     * @param lastName  Прізвище працівника
     * @param salary    Базова заробітна плата (ставка)
     */
    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getType() { return type; }

    /**
     * Встановлює тип об'єкта для JSON. Використовується в класах-нащадках.
     * @param type Назва класу (маркер)
     */
    protected void setType(String type) { this.type = type; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }

    /**
     * Формує рядкове представлення об'єкта для виведення в консоль.
     *
     * @return Рядок із базовою інформацією про працівника
     */
    @Override
    public String toString() {
        return String.format("Працівник: %s %s, Ставка: %.2f", firstName, lastName, salary);
    }

    /**
     * Формує рядок даних для збереження об'єкта у текстовий файл (TXT).
     *
     * @return Рядок у форматі: ТипКласу;Ім'я;Прізвище;Зарплата
     */
    public String toFileString() {
        return "Employee;" + firstName + ";" + lastName + ";" + salary;
    }
}