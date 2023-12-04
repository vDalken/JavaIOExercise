import java.util.ArrayList;

public interface UsersRepository {
    public void createAccount(String name, String cardNumber, String passcode);
    public boolean isCardNumberValid(String cardNumber, String passcode);
    public Card getCardInfo(String cardNumber);
    public void updateFile(ArrayList<String> updatedResourceFile);
    public void performTransfer(String cardNumber, String amountToTransfer, ArrayList<String> updatedResourceFile, Card loggedCard);
    public void performWithdrawal(String amountToWithdraw, ArrayList<String> updatedResourceFile, Card loggedCard);
    public void performDeposit(String amountToDeposit, ArrayList<String> updatedResourceFile, Card loggedCard);
    public void blockCard(ArrayList<String> updatedResourceFile, Card loggedCard);
}
