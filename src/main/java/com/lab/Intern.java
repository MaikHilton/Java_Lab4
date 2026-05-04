package com.lab;

/**
 * Клас, що представляє стажера (інтерна).
 * Містить інформацію про навчальний заклад стажера.
 */
public class Intern extends Employee {
    /** Назва університету, де навчається стажер */
    private String universityName;

    /**
     * Конструктор стажера.
     * @param firstName      Ім'я
     * @param lastName       Прізвище
     * @param salary         Стипендія
     * @param universityName Назва ВНЗ
     */
    public Intern(String firstName, String lastName, double salary, String universityName) {
        super(firstName, lastName, salary);
        setType("Intern");
        this.universityName = universityName;
    }

    /**
     * Повертає інформацію про стажера, включаючи назву його університету.
     * @return рядок з даними стажера
     */
    @Override
    public String toString() {
        return String.format("[Стажер] %s %s, Стипендія: %.2f, ВНЗ: %s (Кількість: %d)",
                getFirstName(), getLastName(), getSalary(), universityName, getQuantity());
    }

    /**
     * Формує рядок для запису в TXT файл.
     * Формат: Тип;Ім'я;Прізвище;Ставка;Університет;Кількість
     * @return рядок даних
     */
    @Override
    public String toFileString() {
        return "Intern;" + getFirstName() + ";" + getLastName() + ";" +
                getSalary() + ";" + universityName + ";" + getQuantity();
    }

    public String getUniversityName() {
        return universityName;
    }
}