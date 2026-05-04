package com.lab;

/**
 * Клас, що представляє контрактного працівника.
 * Успадковується від {@link Employee} та містить інформацію про тривалість контракту.
 */
public class ContractEmployee extends Employee {
    private int contractMonths;

    /**
     * Конструктор для контрактного працівника.
     *
     * @param firstName      Ім'я
     * @param lastName       Прізвище
     * @param salary         Ставка за місяць
     * @param contractMonths Термін дії контракту (у місяцях)
     */
    public ContractEmployee(String firstName, String lastName, double salary, int contractMonths) {
        super(firstName, lastName, salary);
        setType("ContractEmployee");
        this.contractMonths = contractMonths;
    }

    @Override
    public String toString() {
        return String.format("[Контрактник] %s %s, Ставка: %.2f, Термін: %d міс.",
                getFirstName(), getLastName(), getSalary(), contractMonths);
    }

    @Override
    public String toFileString() {
        return "ContractEmployee;" + getFirstName() + ";" + getLastName() + ";" + getSalary() + ";" + contractMonths;
    }
}