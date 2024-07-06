package tr.com.hepsiemlak.todolist.shared.logging;

public class ResponseLog {

    private String responseHeader;

    private String responseBody;

    private String exception;

    public String getResponseHeader() {
        return responseHeader;
    }

    public ResponseLog setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public ResponseLog setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public String getException() {
        return exception;
    }

    public ResponseLog setException(String exception) {
        this.exception = exception;
        return this;
    }

    @Override
    public String toString() {
        return "{"
                + "\"ResponseHeader\":\"" + responseHeader + "\""
                + ",\"ResponseBody\":\"" + responseBody + "\""
                + ",\"Exception\":\"" + exception + "\""
                + "}";
    }
}
