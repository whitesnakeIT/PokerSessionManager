package pl.coderslab.pokersessionmanager.utilities;

public interface AbstractFactory {
    static <T> T create() {
        return (T) new Object();
    }

     <T> T create(T item);
}
