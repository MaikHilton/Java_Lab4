package com.lab;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Клас для роботи з базою даних через JDBC (Лабораторна №12).
 */
public class DatabaseManager {
    private String url;
    private String user;
    private String password;
    private boolean isConfigured = false;

    /**
     * Конструктор, який зчитує налаштування з файлу properties.
     * @param configPath шлях до файлу db.properties
     */
    public DatabaseManager(String configPath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
            this.isConfigured = true;
            System.out.println("[БД] Конфігурацію успішно завантажено.");
        } catch (IOException e) {
            System.out.println("[БД] Помилка читання конфігурації: " + e.getMessage());
        }
    }

    /**
     * Зберігає об'єкт працівника в єдину таблицю employees.
     * Використовує PreparedStatement для уникнення SQL-ін'єкцій.
     * @param emp об'єкт працівника
     */
    public void saveEmployee(Employee emp) {
        if (!isConfigured) {
            System.out.println("[БД] Базу даних не налаштовано. Пропуск збереження.");
            return;
        }

        String sql = "INSERT INTO employees (type, first_name, last_name, salary, quantity, bonus, contract_months, team_size, university_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Спільні поля для всіх
            pstmt.setString(1, emp.getType());
            pstmt.setString(2, emp.getFirstName());
            pstmt.setString(3, emp.getLastName());
            pstmt.setDouble(4, emp.getSalary());
            pstmt.setInt(5, emp.getQuantity());

            // Скидаємо специфічні поля в NULL за замовчуванням
            pstmt.setNull(6, Types.NUMERIC);
            pstmt.setNull(7, Types.INTEGER);
            pstmt.setNull(8, Types.INTEGER);
            pstmt.setNull(9, Types.VARCHAR);

            // Визначаємо специфічні поля через перевірку типу
            if (emp instanceof Manager) {
                Manager m = (Manager) emp;
                pstmt.setDouble(6, m.getBonus());
                pstmt.setInt(8, m.getTeamSize());
            } else if (emp instanceof FullTimeEmployee) {
                FullTimeEmployee ft = (FullTimeEmployee) emp;
                pstmt.setDouble(6, ft.getBonus());
            } else if (emp instanceof ContractEmployee) {
                ContractEmployee ce = (ContractEmployee) emp;
                pstmt.setInt(7, ce.getContractMonths());
            } else if (emp instanceof Intern) {
                Intern i = (Intern) emp;
                pstmt.setString(9, i.getUniversityName());
            }

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("[БД] Об'єкт успішно збережено в PostgreSQL.");
            }

        } catch (SQLException e) {
            System.out.println("[БД] Помилка виконання SQL: " + e.getMessage());
        }
    }
}