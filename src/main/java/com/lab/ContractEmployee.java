package com.lab;

/**
 * Клас, що описує працівника, який працює за тимчасовим контрактом.
 * Містить інформацію про термін дії трудового договору.
 */
public class ContractEmployee extends Employee {
    /** Термін дії контракту в місяцях */
    private int contractMonths;

    /**
     * Конструктор для створення контрактного працівника.
     * @param firstName      Ім'я
     * @param lastName       Прізвище
     * @param salary         Ставка за місяць
     * @param contractMonths Кількість місяців за контрактом
     */
    public ContractEmployee(String firstName, String lastName, double salary, int contractMonths) {
        super(firstName, lastName, salary);
        setType("ContractEmployee");
        this.contractMonths = contractMonths;
    }

    /**
     * Повертає інформацію про контрактника у зручному для читання форматі.
     * @return рядок з описом контрактника та кількістю
     */
    @Override
    public String toString() {
        return String.format("[Контрактник] %s %s, Ставка: %.2f, Термін: %d міс. (Кількість: %d)",
                getFirstName(), getLastName(), getSalary(), contractMonths, getQuantity());
    }

    /**
     * Формує рядок для запису в TXT файл.
     * Формат: Тип;Ім'я;Прізвище;Ставка;Місяці;Кількість
     * @return рядок даних
     */
    @Override
    public String toFileString() {
        return "ContractEmployee;" + getFirstName() + ";" + getLastName() + ";" +
                getSalary() + ";" + contractMonths + ";" + getQuantity();
    }
}