package com.lab;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовий клас для перевірки логіки сортування Comparable (Лаб 13)
 * та агрегації (Лаб 11).
 */
class EmployeeTest {

    /**
     * Перевіряє коректність реалізації інтерфейсу Comparable
     * в абстрактному класі Employee. Сортування має відбуватися за прізвищем.
     */
    @Test
    @DisplayName("Перевірка сортування Comparable за прізвищем")
    void shouldSortEmployeesByLastName() {
        List<Employee> list = new ArrayList<>();
        list.add(new Intern("Богдан", "Яценко", 5000, "СумДУ"));
        list.add(new Manager("Андрій", "Авраменко", 25000, 5000, 5));
        list.add(new FullTimeEmployee("Віктор", "Коваль", 15000, 2000));

        Collections.sort(list);

        assertAll("Порядок сортування",
                () -> assertEquals("Авраменко", list.get(0).getLastName(), "Першим має бути Авраменко"),
                () -> assertEquals("Коваль", list.get(1).getLastName(), "Другим має бути Коваль"),
                () -> assertEquals("Яценко", list.get(2).getLastName(), "Останнім має бути Яценко")
        );
    }

    /**
     * Перевіряє агрегацію компанії та збільшення кількості замість дублювання.
     */
    @Test
    @DisplayName("Перевірка Company: агрегація та додавання з урахуванням кількості")
    void shouldAddEmployeeAndIncreaseQuantityIfDuplicate() {
        Company comp = new Company("Test IT");
        Employee e1 = new Intern("Олександр", "Шевченко", 5000, "СумДУ");
        Employee e2 = new Intern("Олександр", "Шевченко", 5000, "СумДУ");

        comp.addNewEmployee(e1, 1);
        comp.addNewEmployee(e2, 5);

        assertAll("Перевірка логіки агрегації",
                () -> assertEquals(1, comp.getEmployees().size(), "Розмір списку не має змінюватись при дублікатах"),
                () -> assertEquals(6, comp.getEmployees().get(0).getQuantity(), "Кількість має сумуватися (1+5)")
        );
    }
}