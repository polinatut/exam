package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();
        try (SessionFactory factory = cfg.buildSessionFactory();
             Session session = factory.openSession();
             Scanner scanner = new Scanner(System.in)) {

            boolean run = true;
            while (run) {
                System.out.println("Введите слово (или 'q' для выхода): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    run = false;
                } else {
                    System.out.println("Ваше слово:");
                    for (int i = 0; i < 6; i++) {
                        System.out.println(input);
                    }
                    Word word = new Word();
                    word.setWord(input);

                    Transaction transaction = null;
                    try {
                        transaction = session.beginTransaction();
                        session.persist(word);
                        transaction.commit();
                        System.out.println("Слово успешно сохранено в базе данных.");
                    } catch (Exception e) {
                        if (transaction != null) transaction.rollback();
                        System.err.println("Ошибка при сохранении слова: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
