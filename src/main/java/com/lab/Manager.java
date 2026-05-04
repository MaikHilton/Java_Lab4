package com.lab;

/**
 * Клас керівника (менеджера), який керує командою.
 * Розширює функціонал {@link FullTimeEmployee}, додаючи облік розміру команди.
 */
public class Manager extends FullTimeEmployee {
    /** Кількість підлеглих у команді менеджера */
    private int teamSize;

    /**
     * Створює об'єкт менеджера.
     * @param firstName Ім'я
     * @param lastName  Прізвище
     * @param salary    Ставка
     * @param bonus     Бонус керівника
     * @param teamSize  Розмір команди (осіб)
     */
    public Manager(String firstName, String lastName, double salary, double bonus, int teamSize) {
        super(firstName, lastName, salary, bonus);
        setType("Manager");
        this.teamSize = teamSize;
    }

    /**
     * Повертає розширену інформацію про менеджера та його команду.
     * @return рядок з деталями посади
     */
    @Override
    public String toString() {
        return String.format("[Менеджер] %s %s, Зарплата: %.2f (з бонусом: %.2f), Команда: %d осіб (Кількість: %d)",
                getFirstName(), getLastName(), getSalary(), getSalary() + getBonus(), teamSize, getQuantity());
    }

    /**
     * Формує рядок для TXT файлу.
     * Формат: Тип;Ім'я;Прізвище;Ставка;Бонус;Команда;Кількість
     * @return рядок даних
     */
    @Override
    public String toFileString() {
        return "Manager;" + getFirstName() + ";" + getLastName() + ";" +
                getSalary() + ";" + getBonus() + ";" + teamSize + ";" + getQuantity();
    }

    public int getTeamSize() {
        return teamSize;
    }
}