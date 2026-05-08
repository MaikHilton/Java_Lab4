package com.lab;

/**
 * Клас, що представляє контрактного працівника.
 * Успадковує абстрактний клас Employee.
 */
public class ContractEmployee extends Employee {
    /** Тривалість контракту в місяцях */
    private int contractMonths;

    /**
     * Конструктор контрактного працівника.
     * * @param firstName ім'я
     * @param lastName прізвище
     * @param salary місячна ставка
     * @param contractMonths термін дії контракту (місяців)
     */
    public ContractEmployee(String firstName, String lastName, double salary, int contractMonths) {
        super(firstName, lastName, salary);
        this.contractMonths = contractMonths;
    }

    public int getContractMonths() { return contractMonths; }

    @Override
    public String toString() {
        return String.format("[%s] %s %s, Ставка: %.2f, Термін: %d міс. (Кількість: %d)",
                "Контрактник", getFirstName(), getLastName(), getSalary(), contractMonths, getQuantity());
    }

    @Override
    public String toFileString() {
        return getType() + ";" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + contractMonths + ";" + getQuantity();
    }
}