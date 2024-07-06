package tr.com.hepsiemlak.todolist.shared.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class Logging {

    private String correlationId;

    private String currentUser;

    private String endpoint;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime requestTime;

    private RequestLog request;

    private Integer httpStatusCode;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime responseTime;

    private ResponseLog response;

    private String error;

    public String getCorrelationId() {
        return correlationId;
    }

    public Logging setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public Logging setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Logging setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public Logging setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public RequestLog getRequest() {
        return request;
    }

    public Logging setRequest(RequestLog request) {
        this.request = request;
        return this;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Logging setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public Logging setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
        return this;
    }

    public ResponseLog getResponse() {
        return response;
    }

    public Logging setResponse(ResponseLog response) {
        this.response = response;
        return this;
    }

    public String getError() {
        return error;
    }

    public Logging setError(String error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "correlationId='" + correlationId + '\'' +
                ", currentUser='" + currentUser + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", requestTime=" + requestTime +
                ", request=" + request +
                ", httpStatusCode=" + httpStatusCode +
                ", responseTime=" + responseTime +
                ", response=" + response +
                ", error='" + error + '\'' +
                '}';
    }
}
