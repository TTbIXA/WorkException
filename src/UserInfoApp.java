import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class UserInfoApp {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your data (Surname Name MiddleName DateOfBirth PhoneNubmer Gender) :");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String[] parts = input.split(" ");

            if (parts.length != 6){
                throw new IllegalArgumentException("Invalid nubmer of argumets. Expected 6.");
            }


            String surname = parts[0];
            String name = parts[1];
            String middleName = parts[2];

            LocalDate dateOfBirth = parseDate(parts[3]);
            long phoneNumber = parsePhoneNubmer(parts[4]);
            char gender = parseGender(parts[5]);

            writeToFile(surname, name, middleName, dateOfBirth, phoneNumber, gender);
        }
        catch (IllegalArgumentException e){
            System.err.println("Error: " + e.getMessage());
        }
        catch (DateTimeException e){
            System.err.println("Error: Invalid date format. Please use dd.MM.yyyy.");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private static LocalDate parseDate(String dateStr) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    private static long parsePhoneNubmer(String phoneNubmerStr) {
        try {
            return Long.parseLong(phoneNubmerStr);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid phone nubmer format.");
        }
    }

    private static char parseGender(String genderStr) {
        if (genderStr.length() != 1 || ! (genderStr.equalsIgnoreCase("f") || genderStr.equalsIgnoreCase("m"))) {
            throw  new IllegalArgumentException("Invalid gender. Expected 'f' or 'm'.");
      }
        return genderStr.toLowerCase().charAt(0);
    }

    private static void writeToFile(String surname, String name,
                                    String middleName, LocalDate dateOfBirth,
                                    long phoneNumber, char gender)
            throws IOException {
        String filename = surname + ".txt";
        String line = String.format("%s %s %s %s %d %c", surname,
                name, middleName, dateOfBirth, phoneNumber, gender);
        try (BufferedWriter writer = new BufferedWriter(new
                FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine(); // Переход на новую строку
        }
    }

}
