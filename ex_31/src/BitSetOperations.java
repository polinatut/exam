import java.util.BitSet;
import java.util.Scanner;

public class BitSetOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размеров BitSet
        System.out.print("Введите размер первого BitSet: ");
        int size1 = scanner.nextInt();
        BitSet bitSet1 = new BitSet(size1);

        System.out.print("Введите размер второго BitSet: ");
        int size2 = scanner.nextInt();
        BitSet bitSet2 = new BitSet(size2);

        // Инициализация первого BitSet
        System.out.println("Введите индексы установленных битов для первого BitSet (введите -1 для завершения):");
        while (true) {
            int index = scanner.nextInt();
            if (index == -1) break;
            bitSet1.set(index);
        }

        // Инициализация второго BitSet
        System.out.println("Введите индексы установленных битов для второго BitSet (введите -1 для завершения):");
        while (true) {
            int index = scanner.nextInt();
            if (index == -1) break;
            bitSet2.set(index);
        }

        System.out.println("BitSet1: " + bitSet1);
        System.out.println("BitSet2: " + bitSet2);

        // Операция AND
        BitSet andResult = (BitSet) bitSet1.clone();
        andResult.and(bitSet2);
        System.out.println("AND result: " + andResult);

        // Операция OR
        BitSet orResult = (BitSet) bitSet1.clone();
        orResult.or(bitSet2);
        System.out.println("OR result: " + orResult);

        // Операция XOR
        BitSet xorResult = (BitSet) bitSet1.clone();
        xorResult.xor(bitSet2);
        System.out.println("XOR result: " + xorResult);

        // Маскирование
        BitSet mask = new BitSet();
        System.out.println("Введите индексы установленных битов для маски (введите -1 для завершения):");
        while (true) {
            int index = scanner.nextInt();
            if (index == -1) break;
            mask.set(index);
        }

        BitSet maskedResult = (BitSet) bitSet1.clone();
        maskedResult.and(mask);
        System.out.println("Masked result: " + maskedResult);

        scanner.close();
    }
}
