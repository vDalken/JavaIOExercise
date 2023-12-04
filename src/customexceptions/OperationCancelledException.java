package customexceptions;

public class OperationCancelledException extends RuntimeException{
    public OperationCancelledException(){
        super("Operation cancelled by order of the user");
    }
}
