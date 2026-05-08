package com.lab;

/**
 * Клас, що представляє штатного працівника.
 * Успадковує абстрактний клас Employee.
 */
public class FullTimeEmployee extends Employee {
    /** Фіксований бонус для штатного працівника */
    private double bonus;

    /**
     * Конструктор штатного працівника.
     * * @param firstName ім'я
     * @param lastName прізвище
     * @param salary базова ставка
     * @param bonus розмір бонусу
     */
    public FullTimeEmployee(String firstName, String lastName, double salary, double bonus) {
        super(firstName, lastName, salary);
        this.bonus = bonus;
    }

    public double getBonus() { return bonus; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s, Зарплата: %.2f (з бонусом: %.2f) (Кількість: %d)",
                "Штатний", getFirstName(), getLastName(), getSalary(), getSalary() + bonus, getQuantity());
    }

    @Override
    public String toFileString() {
        return getType() + ";" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + bonus + ";" + getQuantity();
    }
}