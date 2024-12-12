package kata.commandeservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommandeNotFoundException.class)
    public ResponseEntity<String> handleCommandeNotFoundException(CommandeNotFoundException ex) {
        String message = "Commande avec l'ID " + ex.getMessage() + " non trouvée.";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}