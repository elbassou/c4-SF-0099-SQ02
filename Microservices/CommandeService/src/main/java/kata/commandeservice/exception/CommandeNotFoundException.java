package kata.commandeservice.exception;

public class CommandeNotFoundException extends Exception {

    public CommandeNotFoundException(Long id) {
        super(String.valueOf(id));
    }
}
