import java.io.FileWriter;
import java.io.IOException;

class AuditFileHandler implements Serializable {
    private final String FILE_PATH;
    public AuditFileHandler(String filePath) {
        this.FILE_PATH=filePath;
    }

    public void addAuditLog(String loggedCardNumber,String receiverCardNumber, String amountToTransfer, boolean isSuccessful) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            String status = isSuccessful ? "success" : "error";
            String dataToWrite = loggedCardNumber+ "-" + receiverCardNumber + "-" + amountToTransfer + "-" + status + "\n";
            writer.append(dataToWrite);
        } catch (IOException e) {
            System.out.println("Error on updating the file: " + e.getMessage());
        }
    }
}
