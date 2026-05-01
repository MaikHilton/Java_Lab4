package com.lab;

/**
 * Клас, що представляє менеджера.
 * Успадковує логіку штатного працівника (FullTimeEmployee) та додає управління командою.
 */
public class Manager extends FullTimeEmployee {
    private int teamSize;

    public Manager(String firstName, String lastName, double salary, double bonus, int teamSize) {
        super(firstName, lastName, salary, bonus); // Виклик конструктора FullTimeEmployee
        this.teamSize = teamSize;
    }

    @Override
    public String toString() {
        // Використовуємо toString() батька і додаємо свою інформацію
        return super.toString() + String.format(", Розмір команди: %d осіб", teamSize);
    }
}