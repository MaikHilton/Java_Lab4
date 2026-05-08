package com.lab;

/**
 * Клас, що представляє стажера.
 * Успадковує абстрактний клас Employee.
 */
public class Intern extends Employee {
    /** Назва вищого навчального закладу */
    private String universityName;

    /**
     * Конструктор стажера.
     * * @param firstName ім'я
     * @param lastName прізвище
     * @param salary стипендія або базова ставка
     * @param universityName назва ВНЗ
     */
    public Intern(String firstName, String lastName, double salary, String universityName) {
        super(firstName, lastName, salary);
        this.universityName = universityName;
    }

    public String getUniversityName() { return universityName; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s, Стипендія: %.2f, ВНЗ: %s (Кількість: %d)",
                "Стажер", getFirstName(), getLastName(), getSalary(), universityName, getQuantity());
    }

    @Override
    public String toFileString() {
        return getType() + ";" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + universityName + ";" + getQuantity();
    }
}