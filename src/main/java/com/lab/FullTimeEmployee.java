package com.lab;

/**
 * Клас, що представляє штатного працівника з фіксованим бонусом.
 * Успадковується від класу {@link Employee}.
 */
public class FullTimeEmployee extends Employee {
    private double bonus;

    /**
     * Конструктор для штатного працівника.
     * @param firstName Ім'я
     * @param lastName Прізвище
     * @param salary Базова ставка
     * @param bonus Щомісячний бонус
     */
    public FullTimeEmployee(String firstName, String lastName, double salary, double bonus) {
        super(firstName, lastName, salary);
        this.bonus = bonus;
    }

    /**
     * Перевизначений метод для виведення детальної інформації про штатного працівника.
     * Демонструє принцип поліморфізму.
     * @return Рядок з іменем, базовою ставкою та розрахованою загальною зарплатою
     */
    @Override
    public String toString() {
        double total = getSalary() + bonus;
        return String.format("[Штатний] %s %s, Зарплата: %.2f (з бонусом: %.2f)",
                getFirstName(), getLastName(), getSalary(), total);
    }
}