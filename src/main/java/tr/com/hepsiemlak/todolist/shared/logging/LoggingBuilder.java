package tr.com.hepsiemlak.todolist.shared.logging;

import tr.com.hepsiemlak.todolist.shared.wrapper.RequestWrapper;
import tr.com.hepsiemlak.todolist.shared.wrapper.ResponseWrapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class LoggingBuilder {

    private String correlationId;

    private String currentUser;

    private LocalDateTime requestTime;

    private LocalDateTime responseTime;

    private String responseBody;

    private LoggingBuilder() {
    }

    public static LoggingBuilder getInstance() {
        return new LoggingBuilder();
    }

    public String getCorrelationId() {
        return Objects.nonNull(correlationId) ? correlationId : UUID.randomUUID().toString();
    }

    public LoggingBuilder setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public String getCurrentUser() {
        return Objects.nonNull(currentUser) ? currentUser : "";
    }

    public LoggingBuilder setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public LocalDateTime getRequestTime() {
        return Objects.nonNull(requestTime) ? requestTime : LocalDateTime.now();
    }

    public LoggingBuilder setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public LocalDateTime getResponseTime() {
        return Objects.nonNull(responseTime) ? responseTime : LocalDateTime.now();
    }

    public LoggingBuilder setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
        return this;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public LoggingBuilder setResponseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public String build(RequestWrapper requestWrapper, ResponseWrapper responseWrapper) throws IOException {

        return new Logging()
                .setCorrelationId(this.getCorrelationId())
                .setCurrentUser(this.getCurrentUser())
                .setEndpoint(requestWrapper.getRequestURL())
                .setRequestTime(this.getRequestTime())
                .setRequest(
                        new RequestLog()
                                .setRequestHeader(requestWrapper.getRequestHeaders())
                                .setRequestParameter(requestWrapper.getRequestParameters())
                                .setRequestBody(requestWrapper.getRequestBody())
                )
                .setHttpStatusCode(responseWrapper.getStatus())
                .setResponseTime(this.getResponseTime())
                .setResponse(
                        new ResponseLog()
                                .setResponseHeader(responseWrapper.getResponseHeaders())
                                .setResponseBody(
                                        Objects.nonNull(getResponseBody()) ?
                                                getResponseBody() :
                                                responseWrapper.getResponseBody()
                                )
                )
                .toString();

    }
}
