import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FileHandler {
    private String filePath;
    public FileHandler(String filePath) {
        this.filePath=filePath;
    }

    public void writeOnTheFile(String name, String cardNumber, String passcode) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            String dataToWrite = name + "-" + cardNumber + "-" + passcode + "-0-false\n";
            writer.append(dataToWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isCardNumberValid(String cardNumber, String passcode) {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber) && splitData[2].equals(passcode)) {
                    return true;
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isCardNumberValid(String cardNumber) {
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    return true;
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<String> getCardInfo(String cardNumber) {
        FileReader reader;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = bufferedReader.readLine();
            while (data != null) {
                String[] splitData = data.split("-");
                if (splitData[1].equals(cardNumber)) {
                    arrayList.add(splitData[0]);
                    arrayList.add(splitData[1]);
                    arrayList.add(splitData[2]);
                    arrayList.add(splitData[3]);
                    arrayList.add(splitData[4]);
                }
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public void updateFile(ArrayList<String> updatedResourceFile) {
        try {
            FileWriter rest = new FileWriter(filePath);
            rest.write("");
            rest.close();
            FileWriter writer = new FileWriter(filePath, true);
            for (int i = 0; i < updatedResourceFile.size(); i++) {
                writer.append(updatedResourceFile.get(i)).append("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
