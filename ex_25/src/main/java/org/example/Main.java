package org.example;

public class Main {
    public static void main(String[] args) {
        // Создаем объект Ивана
        Worker ivan = new Worker();
        ivan.setName("Иван");
        ivan.setAge(25);
        ivan.setSalary(1000);

        // Создаем объект Васи
        Worker vasya = new Worker();
        vasya.setName("Вася");
        vasya.setAge(26);
        vasya.setSalary(2000);

        // Находим сумму зарплат Ивана и Васи
        double totalSalary = ivan.getSalary() + vasya.getSalary();
        System.out.println("Сумма зарплат Ивана и Васи: " + totalSalary);

        // Пример создания объекта студента
        Student student = new Student();
        student.setName("Алексей");
        student.setAge(20);
        student.setScholarship(1500);
        student.setCourse(3);

        System.out.println("Студент: " + student.getName() + ", возраст: " + student.getAge() + ", стипендия: " + student.getScholarship() + ", курс: " + student.getCourse());
    }
}
