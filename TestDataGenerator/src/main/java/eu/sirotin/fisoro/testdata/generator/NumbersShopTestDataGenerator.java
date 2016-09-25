package eu.sirotin.fisoro.testdata.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generator test data for FiSoo example
 */
public class NumbersShopTestDataGenerator {
    private static final String[] singleNumberProperties = new String[]{"Even number",
            "Uneven number",
            "Prime number",
            "Not prime number",
            "Fibonacci number",
            "Not Fibonacci number",
            "Factorial number",
            "Not factorial number"};

    private final String pathDataDir = "TestDataGenerator" + File.separator + "output" + File.separator;

    public static void main(String[] args) {
        NumbersShopTestDataGenerator tg = new NumbersShopTestDataGenerator();
        tg.generateModelJSON();
        tg.generateDataJSON();
    }

    private void generateDataJSON() {
         StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i1 = 0; i1 <= 9; i1++) {
            for (int i2 = 0; i2 <= 9; i2++)
                for (int i3 = 0; i3 <= 9; i3++) {
                    int number = i1 * 100 + i2 * 10 + i3;
                    sb.append(generateDataStringForNumber(number));
                    sb.append("]");
                    if (number != 999) {
                        sb.append(",");
                    }
                    sb.append("\n");
                }
        }
        sb.append("]");

        File dir = new File(pathDataDir);
        dir.mkdirs();
        writeToFile(sb, pathDataDir + "data.json");

    }

    String generateDataStringForNumber(final int number) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(generateNumberProperties(number));

        int i1 = number/100;
        sb.append(generateBitVector(i1, 8));
        sb.append(", ");

        int i2 = (number - i1*100)/10;
        sb.append(generateBitVector(i2, 8));
        sb.append(", ");

        int i3 = (number - i1*100 - i2*10);
        sb.append(generateBitVector(i3, 8));
        sb.append(", ");

        sb.append(generateBitVector(number, 10));
        sb.append(", ");

        sb.append(generateTextProperties(number));
        sb.append(generateTail(number));

        return sb.toString();
    }

    String generateTextProperties(final int number) {
        if(number == 0)return "\"Zero\"";

        StringBuilder sb = new StringBuilder();
        sb.append("\"");

        int i1 = number/100;
        int i2 = (number - i1*100)/10;
        int i3 = (number - i1*100 - i2*10);

        String digitAsString = convertDigitInString(i1);
        if (i1 > 2){
            sb.append(digitAsString).append(" Hundreds ");
        }else if (i1 > 0){
            sb.append(digitAsString).append(" Hundred ");
        }

        if(i2 == 1){
            sb.append(convertTeensInString(10 + i3));
        }else if(i2 > 1){
            sb.append(convertTenthsInString(i2));
            if(i3 != 0){
                sb.append(" ");
            }
        }

        if((i3 != 0) && (i2 != 1)){
            sb.append(convertDigitInString(i3));
        }
        sb.append("\"");
        return sb.toString();
    }

    private String generateTail(final int number) {
        return ", \"This is the number " + number + ".\", \"www.magic-numbers.examples/" + number + ".html\"";
    }


    private String convertDigitInString(final int digit) {
        switch (digit) {
            case 0:
                return "";
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            default:
                throw new IllegalArgumentException("False number: " + digit);
        }

    }

    private String convertTeensInString(final int teen) {
        switch (teen) {
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Twelve";
            case 13:
                return "Thirteen";
            case 15:
                return "Fifteen";
            case 18:
                return "Eighteen";
            case 14:
            case 16:
            case 17:
            case 19:
                return convertDigitInString(teen - 10) + "teen";
            default:
                throw new IllegalArgumentException("False number: " + teen);
        }

    }

    private String convertTenthsInString(final int digit) {
        final int tenth = 10*digit;
        switch (tenth) {
            case 20:
                return "Twenty";
            case 30:
                return "Thirty";
            case 40:
                return "Forty";
            case 50:
                return "Fifty";
            case 60:
                return "Sixty";
            case 70:
                return "Seventy";
            case 80:
                return "Eighty";
            case 90:
                return "Ninety";
            default:
                throw new IllegalArgumentException("False number: " + digit);
        }
    }

    private String generateNumberProperties(final int number) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(number).append("\", ");

        int i1 = number/100;
        int i2 = (number - i1*100)/10;
        int i3 = (number - i1*100 - i2*10);
        int sum = i1 + i2 + i3;

        sb.append("\"").append(sum).append("\", ");
        return sb.toString();
    }

    private String generateBitVector(final int i1, final int maxCriterionNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for(int i = 0; i < maxCriterionNumber; i++){
            boolean b = calculateValue(i, i1);
            char c = b? '1' : '0';
            sb.append(c);
        }
        sb.append("\"");
        return sb.toString();
    }

    private void generateModelJSON() {
        //Generate Head
        int columnInData = 2;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"filterGroups\": [\n");
        sb.append(generateBitVectorModelElement("Nx100", columnInData++,
                singleNumberProperties));
        sb.append(",\n");
        sb.append(generateBitVectorModelElement("Nx10", columnInData++,
                singleNumberProperties));
        sb.append(",\n");
        sb.append(generateBitVectorModelElement("Nx1", columnInData++,
                singleNumberProperties));
        sb.append(",\n");
        String singleNumberPropertiesEx[] = new String[singleNumberProperties.length + 2];
        int j = 0;
        for(; j < singleNumberProperties.length; j++){
            singleNumberPropertiesEx[j] = singleNumberProperties[j];
        }
        singleNumberPropertiesEx[j++] = "Round";
        singleNumberPropertiesEx[j] = "Horizontal";

        sb.append(generateBitVectorModelElement("WholeNumber", columnInData++,
                singleNumberPropertiesEx));
        sb.append(",\n");

        sb.append("{\n" +
                "\t\"name\": \"Values\",\n" +
                "\t\"type\": \"Number\",\n" +
                "\t\"filters\": [");
        sb.append(generateValueModelElement("Min value", 0, "minValue", 0) + ",");
        sb.append(generateValueModelElement("Max value", 999, "maxValue", 0) + ",");
        sb.append(generateValueModelElement("Min digit sum", 0, "minValue", 1) + ",");
        sb.append(generateValueModelElement("Max digit sum", 27, "maxValue", 1));

        sb.append("\n\t]\n" +
                "},\n" +
                "{\n" +
                "\t\"name\": \"Texts\",\n" +
                "\t\"type\": \"Text\",\n" +
                "\t\"filters\": [");

        sb.append(generateTextModelElement("Substring in text representation (e.g. Five)", 6) + ",");
        sb.append(generateTextModelElement("Substring in description (e.g. 15)", 7));

        sb.append("\n\t]\n" +
                "}\n" +
                "]\n" +
                "}");

        File dir = new File(pathDataDir);
        dir.mkdirs();
        writeToFile(sb, pathDataDir + "model.json");
    }

    private String generateBitVectorModelElement(String name, int columnInData, String[] properties) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t\"name\": \"" + name + "\",\n");
        sb.append("\t\"type\": \"Bit Vector\",\n");
        sb.append("\t\"columnInData\": \"" + columnInData + "\",\n");
        sb.append("\t\"filters\": [\n");
        return generatePropertiesEntries(properties, sb);
    }

    private String generateValueModelElement(String name, int value, String type, int columnInData) {
        return "\n\t{\"name\": \"" + name
                + "\", \"value\": \"" + value
                + "\", \"type\": \"" + type
                + "\", \"columnInData\": \"" + columnInData + "\"}";
    }

    private String generateTextModelElement(String name, int columnInData) {
        return "\n\t{\"name\": \"" + name
                + "\", \"value\": \""
                + "\", \"columnInData\": \"" + columnInData + "\"}";
    }

    private String generatePropertiesEntries(final String[] properties, final StringBuilder sb) {
        for(int j = 0; j < properties.length; j++){
            String p = properties[j];
           // sb.append("\t{\t\"id\": " + id++ + ",\"Name\": \""+ p + "\"}");
            sb.append("\t{\"name\": \""+ p + "\"}");
            if(j < properties.length - 1){
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("\t]\n}");
        return sb.toString();
    }


    private void writeToFile(StringBuilder sb, String pathname) {
        try {

            File file = new File(pathname);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean calculateValue(int criterion, int i) {
        switch (criterion) {
            case 0:
                return isEven(i);
            case 1:
                return !isEven(i);
            case 2:
                return isPrime(i);
            case 3:
                return !isPrime(i);
            case 4:
                return isFibonacci(i);
            case 5:
                return !isFibonacci(i);
            case 6:
                return isFactorial(i);
            case 7:
                return !isFactorial(i);
            case 8:
                return isRound(i);
            case 9:
                return isHorizontal(i);
            default:
                throw new RuntimeException("False criterion index");

        }
    }

    static boolean isHorizontal(final int number) {
        int i1 = number/100;
        int i2 = (number - i1*100)/10;
        int i3 = (number - i1*100 - i2*10);
        return (((i1 == i2) && (i2 == i3))? true : false);
    }

    static boolean isRound(final int number) {
        return ((10*(number/10) == number)? true : false);
    }

    static boolean isFactorial(int i) {
        if (i == 0) return false;
        int f = 1;
        for (int j = 1; j <= i; j++) {
            f = f * j;
            if (f == i) return true;
            if (f > i) break;
        }
        return false;
    }

    static boolean isFibonacci(int i) {

        if (i <= 2) return true;
        int fN1 = 2;
        int fN2 = 1;
        for (int j = 3; j <= i; j++) {
            int fN = fN1 + fN2;
            fN2 = fN1;
            fN1 = fN;
            if (fN == i) return true;
            if (fN > i) break;
        }
        return false;
    }

    static boolean isPrime(int i) {
        if (i < 2) return false;
        if (i == 2) return true;
        int m = (int) Math.sqrt(i) + 1;
        if (m < 2) return false;
        for (int j = 2; j < m; j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isEven(int i) {
        return i % 2 == 0;
    }
}