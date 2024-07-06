package tr.com.hepsiemlak.todolist.shared.logging;

public class RequestLog {

    private String requestHeader;

    private String requestParameter;

    private String requestBody;

    public String getRequestHeader() {
        return requestHeader;
    }

    public RequestLog setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public RequestLog setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
        return this;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public RequestLog setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "requestHeader='" + requestHeader + '\'' +
                ", requestParameter='" + requestParameter + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
