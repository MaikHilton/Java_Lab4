package com.lab;

/**
 * Клас, що представляє менеджера компанії.
 * Успадковується від {@link FullTimeEmployee} та містить інформацію про розмір команди.
 */
public class Manager extends FullTimeEmployee {
    private int teamSize;

    /**
     * Конструктор для менеджера.
     *
     * @param firstName Ім'я
     * @param lastName  Прізвище
     * @param salary    Базова ставка
     * @param bonus     Бонус
     * @param teamSize  Кількість людей у підпорядкуванні
     */
    public Manager(String firstName, String lastName, double salary, double bonus, int teamSize) {
        super(firstName, lastName, salary, bonus);
        setType("Manager");
        this.teamSize = teamSize;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Команда: %d осіб", teamSize);
    }

    @Override
    public String toFileString() {
        return "Manager;" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + getBonus() + ";" + teamSize;
    }
}