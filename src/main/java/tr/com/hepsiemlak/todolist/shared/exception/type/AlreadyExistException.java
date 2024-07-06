package tr.com.hepsiemlak.todolist.shared.exception.type;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String className) {
        super(className.concat(" already exists"));
    }
}
