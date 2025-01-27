package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем SessionFactory из hibernate.cfg.xml
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Добавление данных в базу
        addPhonesToDatabase(sessionFactory);

        // Вывод всех телефонов
        System.out.println("Все телефоны:");
        displayAllPhones(sessionFactory);

        // Вывод телефонов по году выпуска
        System.out.println("\nТелефоны, выпущенные в 2023 году:");
        displayPhonesByYear(sessionFactory, 2023);

        // Удаление телефона по ID
        System.out.println("\nУдаление телефона с ID = 5:");
        deletePhoneById(sessionFactory, 5);

        // Повторный вывод всех телефонов
        System.out.println("\nТелефоны после удаления:");
        displayAllPhones(sessionFactory);

        // Закрытие фабрики сессий
        sessionFactory.close();
    }

    // Метод для добавления данных в базу
    private static void addPhonesToDatabase(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Создаем объекты мобильных телефонов
            Phone phone1 = new Phone("Фирма 1", "Телефон 1", 2023, 6.8);
            Phone phone2 = new Phone("Фирма 2", "Телефон 2", 2022, 6.1);
            Phone phone3 = new Phone("Фирма 3", "Телефон 3", 2023, 6.5);
            Phone phone4 = new Phone("Фирма 4", "Телефон 4", 2022, 6.3);
            Phone phone5 = new Phone("Фирма 5", "Телефон 5", 2023, 6.7);
            Phone phone6 = new Phone("Фирма 6", "Телефон 6", 2022, 6.1);
            Phone phone7 = new Phone("Фирма 7", "Телефон 7", 2021, 6.6);
            Phone phone8 = new Phone("Фирма 8", "Телефон 8", 2023, 6.8);
            Phone phone9 = new Phone("Фирма 9", "Телефон 9", 2023, 6.7);
            Phone phone10 = new Phone("Фирма 10", "Телефон 10", 2022, 6.5);

            // Сохраняем данные в базу
            session.save(phone1);
            session.save(phone2);
            session.save(phone3);
            session.save(phone4);
            session.save(phone5);
            session.save(phone6);
            session.save(phone7);
            session.save(phone8);
            session.save(phone9);
            session.save(phone10);

            transaction.commit();
        }
    }

    // Метод для вывода всех телефонов
    private static void displayAllPhones(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Phone> phones = session.createQuery("from Phone", Phone.class).list();
            phones.forEach(System.out::println);
        }
    }

    // Метод для вывода телефонов по году выпуска
    private static void displayPhonesByYear(SessionFactory sessionFactory, int year) {
        try (Session session = sessionFactory.openSession()) {
            List<Phone> phones = session.createQuery("from Phone where releaseYear = :year", Phone.class)
                    .setParameter("year", year)
                    .list();
            phones.forEach(System.out::println);
        }
    }

    // Метод для удаления телефона по ID
    private static void deletePhoneById(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Phone phoneToDelete = session.get(Phone.class, id);
            if (phoneToDelete != null) {
                session.delete(phoneToDelete);
                System.out.println("Телефон удалён: " + phoneToDelete);
            } else {
                System.out.println("Телефон с ID = " + id + " не найден.");
            }
            transaction.commit();
        }
    }
}
