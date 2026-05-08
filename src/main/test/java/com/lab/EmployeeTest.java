package com.lab;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для Лабораторної №15 (Lambda expressions).
 */
class EmployeeTest {

    private List<Employee> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
        list.add(new Intern("Богдан", "Яценко", 5000, "СумДУ"));
        list.add(new Manager("Андрій", "Авраменко", 25000, 5000, 5));
        list.add(new FullTimeEmployee("Віктор", "Коваль", 15000, 2000));

        list.get(0).setQuantity(10);
        list.get(1).setQuantity(5);
        list.get(2).setQuantity(1);
    }

    @Test
    @DisplayName("Сортування за зарплатою через Lambda")
    void shouldSortBySalaryUsingLambda() {
        // Використання лямбда-виразу в тесті
        list.sort((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));

        assertEquals(5000, list.get(0).getSalary());
        assertEquals(25000, list.get(2).getSalary());
    }

    @Test
    @DisplayName("Сортування за прізвищем через Lambda")
    void shouldSortByLastNameUsingLambda() {
        list.sort((e1, e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));

        assertEquals("Авраменко", list.get(0).getLastName());
        assertEquals("Яценко", list.get(2).getLastName());
    }

    @Test
    @DisplayName("Сортування за кількістю через Lambda (спадання)")
    void shouldSortByQuantityDescendingUsingLambda() {
        list.sort((e1, e2) -> Integer.compare(e2.getQuantity(), e1.getQuantity()));

        assertEquals(10, list.get(0).getQuantity());
        assertEquals(1, list.get(2).getQuantity());
    }
}