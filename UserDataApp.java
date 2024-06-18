import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество дата рождения номер телефона пол):");
        String input = scanner.nextLine();
        String[] userData = input.split(" ");

        try {
            if (userData.length != 6) {
                throw new IllegalArgumentException("Введено неверное количество данных.");
            }

            String lastName = userData[0];
            String firstName = userData[1];
            String middleName = userData[2];
            String birthDate = userData[3];
            String phoneNumber = userData[4];
            String gender = userData[5];

            // Проверка формата даты
            try {
                LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Неверный формат даты. Ожидается dd.MM.yyyy.");
            }

            // Проверка формата номера телефона
            try {
                Long.parseLong(phoneNumber);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Номер телефона должен быть целым числом.");
            }

            // Проверка формата пола
            if (!gender.equals("f") && !gender.equals("m")) {
                throw new IllegalArgumentException("Пол должен быть символом 'f' или 'm'.");
            }

            // Запись в файл
            String fileName = lastName + ".txt";
            String dataToWrite = lastName + " " + firstName + " " + middleName + " " + birthDate + " " + phoneNumber + " " + gender;

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write(dataToWrite);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Ошибка записи в файл:");
                e.printStackTrace();
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка ввода данных: " + e.getMessage());
        } finally {
            // Закрытие Scanner
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
