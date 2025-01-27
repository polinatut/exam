package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Генерация случайных чисел
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            numbers.add(random.nextInt(100) + 1);
        }

        // Сортировка по убыванию
        numbers.sort(Collections.reverseOrder());

        // Вывод первых 10 элементов
        System.out.println("Первые 10 элементов отсортированного списка:");
        numbers.stream().limit(10).forEach(System.out::println);

        // Сохранение в базу данных
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory factory = cfg.buildSessionFactory();
             Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Сохранение всех чисел
            numbers.forEach(num -> {
                Number numberEntity = new Number();
                numberEntity.setNumber(num);
                session.persist(numberEntity);
            });

            transaction.commit();
        }
    }
}
