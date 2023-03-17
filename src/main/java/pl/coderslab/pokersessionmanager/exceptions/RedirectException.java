package pl.coderslab.pokersessionmanager.exceptions;

public class RedirectException extends RuntimeException {
    public RedirectException() {
        super("I can't set Poker Room Scope by role.");
    }
}
