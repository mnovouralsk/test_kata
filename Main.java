import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.IIOException;

public class Main {

    public static int num1;
    public static int num2;
    public static char operator;
    public static boolean isRoman = false;
    public static String result;

    public static void main(String[] args) throws IIOException {
        System.out.printf("Введите арифметическую операцию: ");
        Scanner user = new Scanner(System.in);
        String input = user.nextLine();
        System.out.println(calc(input));
        user.close();
    }

    public static String calc(String input) throws IIOException {

        input = input.replaceAll(" ", ""); // убираем пробелы

        if (input.matches("\\d+[+\\-*/]\\d+")) { // проверка ввода по маске

            String[] parts = input.split("[\\+\\-\\*/]"); // разделение по знакам арифметических операций
            num1 = Integer.parseInt(parts[0]); // первое число
            num2 = Integer.parseInt(parts[1]); // второе число
            operator = input.charAt(parts[0].length()); // оператор

        } else if (input.matches("^[IVX]+[+\\-*/][IVX]+$")) { // проверка ввода по маске

            isRoman = true;

            String[] parts = input.split("[\\+\\-\\*/]");
            num1 = NumConverter.RomanToArabic(parts[0]); // первое число
            num2 = NumConverter.RomanToArabic(parts[1]); // второе число
            operator = input.charAt(parts[0].length()); // оператор
        } else {
            throw new IIOException(input, null);
        }

        if ((num1 > 0 && num1 <= 10) && (num2 > 0 && num2 <= 10)) { //проверка на диапазон чисел
            switch (operator) {
                case '+':
                    result = String.valueOf(num1 + num2);
                    break;
                case '-':
                    result = String.valueOf(num1 - num2);
                    break;
                case '*':
                    result = String.valueOf(num1 * num2);
                    break;
                case '/':
                    result = String.valueOf(num1 / num2);
                    break;
            }
        } else {
            throw new IIOException(input, null);
        }

        if (isRoman) {
            int num = Integer.parseInt(result);
            if (num > 0) {
                result = String.valueOf(NumConverter.arabicToRoman(num));
            } else {
                throw new IIOException(input, null);
            }
        }
        return result;
    }
}

class NumConverter {
    private final static Map<Character, Integer> SYMBOLS = new HashMap<>();
    static {
        SYMBOLS.put('I', 1);
        SYMBOLS.put('V', 5);
        SYMBOLS.put('X', 10);
    }

    public static int RomanToArabic(String romanNumeral) {
        int result = 0;
        int prevValue = 0;
        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            int curValue = SYMBOLS.get(romanNumeral.charAt(i));
            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }
        return result;
    }

    public static String arabicToRoman(int number) {
        Map<Integer, String> romanDict = new LinkedHashMap<>();
        //romanDict.put(1000, "M");
        //romanDict.put(900, "CM");
        //romanDict.put(500, "D");
        //romanDict.put(400, "CD");
        romanDict.put(100, "C");
        romanDict.put(90, "XC");
        romanDict.put(50, "L");
        romanDict.put(40, "XL");
        romanDict.put(10, "X");
        romanDict.put(9, "IX");
        romanDict.put(5, "V");
        romanDict.put(4, "IV");
        romanDict.put(1, "I");

        StringBuilder romanNumeral = new StringBuilder();
        for (int key : romanDict.keySet()) {
            while (number >= key) {
                romanNumeral.append(romanDict.get(key));
                number -= key;
            }
        }
        return romanNumeral.toString();
    }
}
