package com.lab;

/**
 * Клас, що представляє штатного працівника компанії.
 * Успадковується від {@link Employee} та додає механізм нарахування бонусів.
 * У Лабораторній №11 підтримує облік кількості через базовий клас.
 */
public class FullTimeEmployee extends Employee {
    /** Щомісячна бонусна надбавка до зарплати */
    private double bonus;

    /**
     * Створює об'єкт штатного працівника.
     * @param firstName Ім'я працівника
     * @param lastName  Прізвище працівника
     * @param salary    Базова ставка
     * @param bonus     Розмір бонусу
     */
    public FullTimeEmployee(String firstName, String lastName, double salary, double bonus) {
        super(firstName, lastName, salary);
        setType("FullTimeEmployee");
        this.bonus = bonus;
    }

    /**
     * Повертає розмір бонусу працівника.
     * @return сума бонусу
     */
    public double getBonus() { return bonus; }

    /**
     * Повертає текстове представлення об'єкта, включаючи загальну суму виплат та кількість.
     * @return форматований рядок з інформацією про працівника
     */
    @Override
    public String toString() {
        return String.format("[Штатний] %s %s, Зарплата: %.2f (з бонусом: %.2f) (Кількість: %d)",
                getFirstName(), getLastName(), getSalary(), getSalary() + bonus, getQuantity());
    }

    /**
     * Формує спеціальний рядок для збереження даних у текстовий файл.
     * Формат: Тип;Ім'я;Прізвище;Ставка;Бонус;Кількість
     * @return рядок з роздільниками ';'
     */
    @Override
    public String toFileString() {
        return "FullTimeEmployee;" + getFirstName() + ";" + getLastName() + ";" +
                getSalary() + ";" + bonus + ";" + getQuantity();
    }
}