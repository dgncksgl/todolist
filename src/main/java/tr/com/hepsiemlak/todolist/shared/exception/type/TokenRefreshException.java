package tr.com.hepsiemlak.todolist.shared.exception.type;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException() {
        super("Refresh token was expired. Please make a new sign-in request");
    }
}
