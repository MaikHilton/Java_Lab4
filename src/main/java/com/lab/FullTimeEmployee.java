package com.lab;

/**
 * Клас, що представляє штатного працівника.
 * Успадковується від {@link Employee} та додає атрибут бонусу.
 */
public class FullTimeEmployee extends Employee {
    private double bonus;

    /**
     * Конструктор для штатного працівника.
     *
     * @param firstName Ім'я
     * @param lastName  Прізвище
     * @param salary    Базова ставка
     * @param bonus     Щомісячний бонус
     */
    public FullTimeEmployee(String firstName, String lastName, double salary, double bonus) {
        super(firstName, lastName, salary);
        setType("FullTimeEmployee");
        this.bonus = bonus;
    }

    public double getBonus() { return bonus; }

    @Override
    public String toString() {
        return String.format("[Штатний] %s %s, Зарплата: %.2f (з бонусом: %.2f)",
                getFirstName(), getLastName(), getSalary(), getSalary() + bonus);
    }

    @Override
    public String toFileString() {
        return "FullTimeEmployee;" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + bonus;
    }
}