package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем фабрику сессий
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Заполняем таблицу алфавитом
        populateAlphabet(sessionFactory);

        // Читаем и выводим алфавит
        readAlphabet(sessionFactory);

        // Закрываем фабрику сессий
        sessionFactory.close();
    }

    private static void populateAlphabet(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Проверяем, пустая ли таблица
            long count = (long) session.createQuery("SELECT COUNT(a) FROM Alphabet a").uniqueResult();
            if (count == 0) {
                // Заполняем таблицу
                char letter = 'A';
                for (int i = 1; i <= 26; i++) {
                    session.save(new Alphabet(i, letter));
                    letter++;
                }
            }

            transaction.commit();
        }
    }

    private static void readAlphabet(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Alphabet> alphabetList = session.createQuery("FROM Alphabet ORDER BY id", Alphabet.class).list();

            // Выводим буквы алфавита с использованием цикла while
            int index = 0;
            while (index < alphabetList.size()) {
                System.out.println(alphabetList.get(index).getLetter());
                index++;
            }
        }
    }
}
