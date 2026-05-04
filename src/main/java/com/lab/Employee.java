package com.lab;

/**
 * Базовий клас працівника.
 * У Лабораторній №11 додано поле {@code quantity} для реалізації агрегації
 * та обліку кількості однакових об'єктів.
 */
public class Employee {
    private String type = "Employee";
    private String firstName;
    private String lastName;
    private double salary;
    /** Кількість працівників даного типу з ідентичними даними */
    private int quantity = 1;

    /**
     * Конструктор базового класу.
     * @param firstName Ім'я
     * @param lastName Прізвище
     * @param salary Базова ставка
     */
    public Employee(String firstName, String lastName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getType() { return type; }
    protected void setType(String type) { this.type = type; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Збільшує поточну кількість об'єктів на задане значення.
     * @param q кількість для додавання
     */
    public void addQuantity(int q) { this.quantity += q; }

    @Override
    public String toString() {
        return String.format("Працівник: %s %s, Ставка: %.2f (Кількість: %d)",
                firstName, lastName, salary, quantity);
    }

    /**
     * Формує рядок для збереження у TXT файл.
     * Додано поле quantity в кінець рядка.
     */
    public String toFileString() {
        return "Employee;" + firstName + ";" + lastName + ";" + salary + ";" + quantity;
    }
}