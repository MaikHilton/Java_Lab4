package com.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Employee emp = new Employee("Іван", "Іванов", "Розробник", 40000.0, 3, "IT");

        // Перевіряємо, що встановлення від'ємної зарплати викидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(-500);
        });

        // Перевіряємо помилку при порожньому відділі
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setDepartment("   ");
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        // Перевіряємо, що створення об'єкта з порожнім іменем викидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Коваль", "Дизайнер", 30000.0, 2, "Дизайн");
        });

        // Перевіряємо від'ємний стаж у конструкторі
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Анна", "Петрова", "Менеджер", 50000.0, -1, "HR");
        });
    }
}