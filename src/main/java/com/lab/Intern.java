package com.lab;

/**
 * Клас, що представляє стажера.
 * Успадковується від {@link Employee} та містить інформацію про навчальний заклад.
 */
public class Intern extends Employee {
    private String universityName;

    /**
     * Конструктор для стажера.
     *
     * @param firstName      Ім'я
     * @param lastName       Прізвище
     * @param salary         Стипендія або ставка стажера
     * @param universityName Назва університету, де навчається стажер
     */
    public Intern(String firstName, String lastName, double salary, String universityName) {
        super(firstName, lastName, salary);
        setType("Intern");
        this.universityName = universityName;
    }

    @Override
    public String toString() {
        return String.format("[Стажер] %s %s, Стипендія: %.2f, ВНЗ: %s",
                getFirstName(), getLastName(), getSalary(), universityName);
    }

    @Override
    public String toFileString() {
        return "Intern;" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + universityName;
    }
}