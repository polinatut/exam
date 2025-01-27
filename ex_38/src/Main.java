import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;

public class Main {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    // Генерация ключа AES-128
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE, new SecureRandom());
        return keyGen.generateKey();
    }

    // Сохранение ключа в файл
    public static void saveKey(SecretKey key, String filePath) throws Exception {
        byte[] encodedKey = key.getEncoded();
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(encodedKey);
        }
    }

    // Загрузка ключа из файла
    public static SecretKey loadKey(String filePath) throws Exception {
        byte[] encodedKey = Files.readAllBytes(new File(filePath).toPath());
        return new SecretKeySpec(encodedKey, ALGORITHM);
    }

    // Шифрование данных
    public static void encryptFile(String inputFile, String outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] inputBytes = fis.readAllBytes();
            byte[] encryptedBytes = cipher.doFinal(inputBytes);
            fos.write(encryptedBytes);
        }
    }

    // Дешифрование данных
    public static void decryptFile(String inputFile, String outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] encryptedBytes = fis.readAllBytes();
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            fos.write(decryptedBytes);
        }
    }

    public static void main(String[] args) {
        try {
            // Путь к файлам
            String inputFile = "src/input.txt"; // Файл с исходными данными
            String encryptedFile = "src/encrypted.dat"; // Файл для зашифрованных данных
            String decryptedFile = "src/decrypted.txt"; // Файл для расшифрованных данных
            String keyFile = "src/aesKey.key"; // Файл для сохранения ключа

            // Генерация и сохранение ключа
            SecretKey key = generateKey();
            saveKey(key, keyFile);

            // Шифрование данных
            System.out.println("Шифрование данных...");
            encryptFile(inputFile, encryptedFile, key);
            System.out.println("Данные зашифрованы и сохранены в " + encryptedFile);

            // Загрузка ключа
            SecretKey loadedKey = loadKey(keyFile);

            // Дешифрование данных
            System.out.println("Дешифрование данных...");
            decryptFile(encryptedFile, decryptedFile, loadedKey);
            System.out.println("Данные расшифрованы и сохранены в " + decryptedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
