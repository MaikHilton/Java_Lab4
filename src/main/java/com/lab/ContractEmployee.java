package com.lab;

/**
 * Клас, що представляє контрактного працівника.
 * Містить інформацію про термін дії контракту.
 */
public class ContractEmployee extends Employee {
    private int contractMonths;

    /**
     * Конструктор для контрактного працівника.
     * @param firstName Ім'я
     * @param lastName Прізвище
     * @param salary Ставка за місяць
     * @param contractMonths Термін контракту в місяцях
     */
    public ContractEmployee(String firstName, String lastName, double salary, int contractMonths) {
        super(firstName, lastName, salary);
        this.contractMonths = contractMonths;
    }

    /**
     * Перевизначений метод для виведення інформації про контракт.
     * @return Рядок з деталями контракту
     */
    @Override
    public String toString() {
        return String.format("[Контрактник] %s %s, Ставка: %.2f, Термін: %d міс.",
                getFirstName(), getLastName(), getSalary(), contractMonths);
    }
}