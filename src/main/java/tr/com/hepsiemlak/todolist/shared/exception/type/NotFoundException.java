package tr.com.hepsiemlak.todolist.shared.exception.type;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String className) {
        super(className.concat(" is not found"));
    }
}
