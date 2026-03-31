package com.lab;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Клас для модульного тестування (JUnit 5) логіки Employee.
 */
class EmployeeTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Employee emp = new Employee("Тест", "Тестович", "IT", Position.QA, 10000, 2);

        // Перевірка валідації зарплати
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(-500);
        });

        // Перевірка валідації стажу
        assertThrows(IllegalArgumentException.class, () -> {
            emp.setExperienceYears(-1);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        // Перевірка порожнього імені
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("", "Прізвище", "Відділ", Position.DEVELOPER, 10000, 2);
        });

        // Перевірка null для посади (enum)
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Ім'я", "Прізвище", "Відділ", null, 10000, 2);
        });
    }

    @Test
    void shouldCreateExactCopyUsingCopyConstructor() {
        Employee original = new Employee("Оригінал", "Оригіналович", "Sales", Position.MANAGER, 25000, 5);
        Employee copy = new Employee(original);

        // Об'єкти мають бути різними у пам'яті (різні посилання)
        assertNotSame(original, copy);

        // Але їхній вміст має бути ідентичним (спрацьовує перевизначений equals)
        assertEquals(original, copy);
    }

    @Test
    void shouldIncrementStaticCounterWhenCreated() {
        int initialCount = Employee.getTotalEmployees();

        new Employee("Тест", "Статика", "IT", Position.HR, 15000, 1);

        assertEquals(initialCount + 1, Employee.getTotalEmployees());
    }
}