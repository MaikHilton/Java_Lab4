package com.lab;

/**
 * Абстрактний базовий клас працівника (Лабораторна №13).
 * Визначає загальні атрибути та методи для всіх типів працівників.
 * Реалізує інтерфейс Comparable для забезпечення природного сортування
 * списку працівників за прізвищем (та ім'ям у разі збігу).
 * * @author Яценко Іван
 */
public abstract class Employee implements Comparable<Employee> {
    /** Тип працівника (визначається автоматично на основі класу-нащадка) */
    private String type;
    /** Ім'я працівника */
    private String firstName;
    /** Прізвище працівника */
    private String lastName;
    /** Базова ставка або зарплата працівника */
    private double salary;
    /** Кількість однакових працівників (для агрегації) */
    private int quantity = 1;

    /**
     * Конструктор базового класу.
     * * @param firstName ім'я працівника
     * @param lastName прізвище працівника
     * @param salary базова ставка
     */
    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.type = this.getClass().getSimpleName();
    }

    /**
     * Порівнює цього працівника з іншим для сортування.
     * Критерій сортування: Прізвище (ігноруючи регістр).
     * Якщо прізвища однакові, сортує за ім'ям.
     * * @param other об'єкт Employee для порівняння
     * @return від'ємне число, нуль або додатне число, якщо цей об'єкт менший, дорівнює або більший за інший
     */
    @Override
    public int compareTo(Employee other) {
        if (other == null) return 1;
        int res = this.lastName.compareToIgnoreCase(other.lastName);
        if (res == 0) {
            return this.firstName.compareToIgnoreCase(other.firstName);
        }
        return res;
    }

    // --- Гетери та сетери ---

    public String getType() { return type; }
    protected void setType(String type) { this.type = type; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Збільшує кількість працівників на задане значення.
     * @param q кількість для додавання
     */
    public void addQuantity(int q) { this.quantity += q; }

    /**
     * Формує рядок з інформацією про працівника для виводу в консоль.
     * Оскільки клас абстрактний, реалізація покладається на нащадків.
     * * @return відформатований рядок
     */
    @Override
    public abstract String toString();

    /**
     * Формує рядок з даними працівника для збереження у TXT файл.
     * * @return рядок з даними, розділеними крапкою з комою (;)
     */
    public abstract String toFileString();
}