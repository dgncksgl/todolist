package tr.com.hepsiemlak.todolist.shared.exception.type;

public class NotDeleteException extends RuntimeException {

    public NotDeleteException(String className) {
        super(className.concat(" is not deleted due to the existence of related data"));
    }
}
