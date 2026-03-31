package com.lab;

/**
 * Перерахування посад працівників.
 * Використовується для фіксованого набору значень атрибута посади.
 */
public enum Position {
    DEVELOPER,
    QA,
    MANAGER,
    HR,
    DESIGNER;

    /**
     * Повертає масив усіх доступних посад для зручного виводу в меню.
     * @return масив значень перерахування
     */
    public static Position[] getAvailablePositions() {
        return values();
    }
}