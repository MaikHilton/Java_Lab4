package com.lab;

/**
 * Клас, що представляє менеджера компанії.
 * Успадковує абстрактний клас Employee.
 */
public class Manager extends Employee {
    /** Бонус за керівництво */
    private double bonus;
    /** Кількість підлеглих у команді */
    private int teamSize;

    /**
     * Конструктор менеджера.
     * * @param firstName ім'я
     * @param lastName прізвище
     * @param salary базова ставка
     * @param bonus розмір бонусу
     * @param teamSize розмір команди
     */
    public Manager(String firstName, String lastName, double salary, double bonus, int teamSize) {
        super(firstName, lastName, salary);
        this.bonus = bonus;
        this.teamSize = teamSize;
    }

    public double getBonus() { return bonus; }
    public int getTeamSize() { return teamSize; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s, Зарплата: %.2f (з бонусом: %.2f), Команда: %d осіб (Кількість: %d)",
                "Менеджер", getFirstName(), getLastName(), getSalary(), getSalary() + bonus, teamSize, getQuantity());
    }

    @Override
    public String toFileString() {
        return getType() + ";" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + bonus + ";" + teamSize + ";" + getQuantity();
    }
}