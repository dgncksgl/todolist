package tr.com.hepsiemlak.todolist.shared.exception.type;

public class NotActiveException extends RuntimeException {

    public NotActiveException(String className) {
        super(className.concat(" is not active"));
    }
}
