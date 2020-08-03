import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);
        AlgorithmSelection algorithmSelection = create(arguments);
        algorithmSelection.runProgram();
    }
    public static AlgorithmSelection create(Arguments arguments) {
        ModeType mode = new EncryptMode();
        CryptType crypt = new ShiftCrypt();
        ReadWriteType readWrite = new ConsoleReadWrite(arguments);
        if ("dec".equals(arguments.mode)) {
            mode = new DecryptMode();
        }
        if ("unicode".equals(arguments.algorithmType)) {
            crypt = new UnicodeCrypt();
        }
        if ("".equals(arguments.data)) {
            readWrite = new FileReadWrite(arguments);
        }
        return new AlgorithmSelection(mode, crypt, readWrite, arguments);
    }
}

class AlgorithmSelection {
    ModeType modeType;
    CryptType cryptType;
    ReadWriteType readWriteType;
    Arguments arguments;

    public AlgorithmSelection(ModeType modeType, CryptType cryptType,
                              ReadWriteType readWriteType, Arguments arguments) {
        this.modeType = modeType;
        this.cryptType = cryptType;
        this.readWriteType = readWriteType;
        this.arguments = arguments;
    }

    void runProgram() {
        this.readWriteType.write(this.modeType.crypt(this.readWriteType.read(), arguments.key, this.cryptType));
    }
}

interface ModeType {
    String crypt(String data, String key, CryptType cryptType);
}

class EncryptMode implements ModeType {
    @Override
    public String crypt(String data, String key, CryptType cryptType) {
        return enc(data, key, cryptType);
    }

    String enc(String data, String key, CryptType cryptType) {
        if ("0".equals(key) || "".equals(key)) {
            return data;
        }
        if ("".equals(data)) {
            return data;
        }
        StringBuilder result = new StringBuilder();
        int i = Integer.decode(key);
        for (int a = 0; a < data.length(); a++) {
            char ch = data.charAt(a);
            result.append(cryptType.encryptChar(ch, i));
        }
        return result.toString();
    }
}

class DecryptMode implements ModeType {
    @Override
    public String crypt(String data, String key, CryptType cryptType) {
        return dec(data, key, cryptType);
    }

    String dec(String data, String key, CryptType cryptType) {
        if ("0".equals(key) || "".equals(key)) {
            return data;
        }
        if ("".equals(data)) {
            return data;
        }
        StringBuilder result = new StringBuilder();
        int i = Integer.decode(key);
        for (int a = 0; a < data.length(); a++) {
            char ch = data.charAt(a);
            result.append(cryptType.decryptChar(ch, i));
        }
        return result.toString();
    }
}

interface CryptType {

    char encryptChar(char ch, int key);

    char decryptChar(char ch, int key);
}

class UnicodeCrypt implements CryptType {

    @Override
    public char encryptChar(char ch, int key) {
        int a = (int)ch;
        return  (char)(a + key);
    }

    @Override
    public char decryptChar(char ch, int key) {
        int a = (int)ch;
        return (char)(a - key);
    }
}

class ShiftCrypt implements CryptType {

    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};

    int indexOfCharInAlphabet(char a) {
        char b = Character.toLowerCase(a);
        int result = 0;
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == b) {
                result = i;
            }
        }
        return result;
    }

    @Override
    public char encryptChar(char ch, int key) {
        if (!Character.isLetter(ch)) {
            return ch;
        }
        int b = indexOfCharInAlphabet(ch);
        if (b + key >= alphabet.length) {
            if (!Character.isLowerCase(ch)) {
                return Character.toUpperCase(alphabet[b + key - 26]);
            } else {
                return alphabet[b + key - 26];
            }
        } else {
            if (!Character.isLowerCase(ch)) {
                return Character.toUpperCase(alphabet[b + key]);
            } else {
                return alphabet[b + key];
            }
        }
    }

    @Override
    public char decryptChar(char ch, int key) {
        if (!Character.isLetter(ch)) {
            return ch;
        }
        int b = indexOfCharInAlphabet(ch);
        if (b - key < 0) {
            if (!Character.isLowerCase(ch)) {
                return Character.toUpperCase(alphabet[b - key + 26]);
            } else {
                return alphabet[b - key + 26];
            }

        } else {
            if (!Character.isLowerCase(ch)) {
                return Character.toUpperCase(alphabet[b - key]);
            } else {
                return alphabet[b - key];
            }
        }
    }

}

interface ReadWriteType {

    String read();

    void write(String data);
}

class ConsoleReadWrite implements ReadWriteType {

    String data;

    public ConsoleReadWrite(Arguments arguments) {
        this.data = arguments.data;
    }

    @Override
    public String read() {
        return data;
    }

    @Override
    public void write(String data) {
        System.out.println(data);
    }
}

class FileReadWrite implements ReadWriteType {

    String pathToFileIn;
    String pathToFileOut;

    public FileReadWrite(Arguments arguments) {
        this.pathToFileIn = arguments.pathToFileIn;
        this.pathToFileOut = arguments.pathToFileOut;
    }

    @Override
    public String read() {
        String result = "";
        File file = new File(pathToFileIn);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Such file don't exist!");
        }
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            result = result + str;
        }
        scanner.close();
        return result;
    }
    @Override
    public void write(String data) {
        File fileOut = new File(pathToFileOut);
        FileWriter fileOutWriter = null;
        try {
            fileOutWriter = new FileWriter(fileOut);
            fileOutWriter.write(data);
            fileOutWriter.close();
        } catch (IOException e) {
            System.out.println("Something goes wrong! It's output mistake!");
        }
    }
}

class Arguments {
    String mode = "";
    String key = "";
    String data = "";
    String pathToFileIn = "";
    String pathToFileOut = "";
    String algorithmType = "";

    public Arguments(String[] arguments) {
        initializeArguments(arguments);
    }

    void initializeArguments(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            if ("-key".equals(arguments[i])) {
                key = arguments[i + 1];
            }
            if ("-mode".equals(arguments[i])) {
                mode = arguments[i + 1];
            }
            if ("-data".equals(arguments[i])) {
                data = arguments[i + 1];
            }
            if ("-in".equals(arguments[i])) {
                pathToFileIn = arguments[i + 1];
            }
            if ("-out".equals(arguments[i])) {
                pathToFileOut = arguments[i + 1];
            }
            if ("-alg".equals(arguments[i])) {
                algorithmType = arguments[i + 1];
            }
        }
    }
}