package pl.coderslab.pokersessionmanager.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
        super("Entity not found.");
    }
}
