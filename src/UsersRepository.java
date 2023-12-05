import java.util.ArrayList;

public interface UsersRepository {
    void createAccount(String name, String cardNumber, String passcode);
    boolean isCardNumberValid(String cardNumber, String passcode);
    Card getCardInfo(String cardNumber);
    void updateFile(ArrayList<String> updatedResourceFile);
    void performTransfer(String cardNumber, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard);
    void performWithdrawal(String amountToWithdraw, ArrayList<String> updatedResourceFile, Card loggedCard);
    void performDeposit(String amountToDeposit, ArrayList<String> updatedResourceFile, Card loggedCard);
    void blockCard(ArrayList<String> updatedResourceFile, Card loggedCard);
}
