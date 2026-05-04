package com.lab;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас-контейнер, що реалізує агрегацію працівників.
 * Забезпечує управління колекцією ArrayList та логіку унікальності об'єктів.
 */
public class Company {
    private String name;
    private List<Employee> employees;

    /**
     * Створює нову компанію з порожнім списком працівників.
     * @param name Назва компанії
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
     * Якщо працівник з таким ім'ям, прізвищем та типом вже існує,
     * замість дублювання об'єкта збільшується його кількість.
     *
     * @param emp Об'єкт працівника для додавання
     * @param quantity Кількість одиниць
     */
    public void addNewEmployee(Employee emp, int quantity) {
        for (Employee e : employees) {
            if (e.getType().equals(emp.getType()) &&
                    e.getFirstName().equals(emp.getFirstName()) &&
                    e.getLastName().equals(emp.getLastName())) {
                e.addQuantity(quantity);
                return;
            }
        }
        emp.setQuantity(quantity);
        employees.add(emp);
    }

    /**
     * Пошук за прізвищем (Лабораторна №10).
     * @param lastName Прізвище для пошуку
     * @return Список знайдених об'єктів
     */
    public List<Employee> searchByLastName(String lastName) {
        List<Employee> found = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getLastName().equalsIgnoreCase(lastName)) found.add(e);
        }
        return found;
    }

    /**
     * Пошук за діапазоном базової ставки.
     * @param min Мінімальна межа
     * @param max Максимальна межа
     * @return Список знайдених об'єктів
     */
    public List<Employee> searchBySalaryRange(double min, double max) {
        List<Employee> found = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getSalary() >= min && e.getSalary() <= max) found.add(e);
        }
        return found;
    }

    /**
     * Пошук за типом посади.
     * @param type Назва класу (Manager, Intern тощо)
     * @return Список знайдених об'єктів
     */
    public List<Employee> searchByType(String type) {
        List<Employee> found = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getType().equalsIgnoreCase(type)) found.add(e);
        }
        return found;
    }
}