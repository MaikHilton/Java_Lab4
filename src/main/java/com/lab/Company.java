package com.lab;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас, що представляє компанію.
 * Демонструє принцип агрегації: компанія містить список працівників (Employee).
 */
public class Company {

    private final List<Employee> employees;

    /**
     * Конструктор за замовчуванням. Ініціалізує порожній список працівників.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    /**
     * Метод для додавання працівника до компанії.
     * @param employee об'єкт працівника
     * @throws IllegalArgumentException якщо передано null
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Неможливо додати порожнього (null) працівника.");
        }
        this.employees.add(employee);
    }

    /**
     * Повертає список усіх працівників.
     * Повертається копія списку для збереження інкапсуляції (захист внутрішнього стану).
     * @return список працівників
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(this.employees);
    }

    /**
     * Повертає поточну кількість працівників у списку компанії.
     * @return кількість елементів у списку
     */
    public int getEmployeesCount() {
        return this.employees.size();
    }
}