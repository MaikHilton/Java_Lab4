package com.lab;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас-контейнер для агрегації працівників компанії.
 * Керує колекцією об'єктів Employee та надає методи пошуку.
 */
public class Company {
    /** Назва компанії */
    private String name;
    /** Список усіх працівників */
    private List<Employee> employees;

    /**
     * Конструктор компанії.
     * @param name назва компанії
     */
    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Employee> getEmployees() { return employees; }

    /**
     * Додає нового працівника до компанії.
     * Якщо такий працівник вже існує, збільшує його кількість замість створення дубліката.
     * * @param emp об'єкт працівника
     * @param quantity кількість для додавання
     */
    public void addNewEmployee(Employee emp, int quantity) {
        for (Employee existing : employees) {
            if (existing.getType().equals(emp.getType()) &&
                    existing.getFirstName().equalsIgnoreCase(emp.getFirstName()) &&
                    existing.getLastName().equalsIgnoreCase(emp.getLastName())) {
                existing.addQuantity(quantity);
                return;
            }
        }
        emp.setQuantity(quantity);
        employees.add(emp);
    }

    /**
     * Пошук працівників за прізвищем.
     * @param lastName прізвище для пошуку
     * @return список знайдених працівників
     */
    public List<Employee> searchByLastName(String lastName) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getLastName().equalsIgnoreCase(lastName)) {
                result.add(emp);
            }
        }
        return result;
    }

    /**
     * Пошук працівників за діапазоном зарплати.
     * @param min мінімальна зарплата
     * @param max максимальна зарплата
     * @return список знайдених працівників
     */
    public List<Employee> searchBySalaryRange(double min, double max) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getSalary() >= min && emp.getSalary() <= max) {
                result.add(emp);
            }
        }
        return result;
    }

    /**
     * Пошук працівників за типом посади.
     * @param type тип об'єкта (наприклад, "Manager")
     * @return список знайдених працівників
     */
    public List<Employee> searchByType(String type) {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getType().equalsIgnoreCase(type)) {
                result.add(emp);
            }
        }
        return result;
    }
}