package tr.com.hepsiemlak.todolist.shared.exception.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExceptionEntity implements Serializable {

    private String id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String messageCode;

    private String message;

    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExceptionEntity> subException;

    private ExceptionEntity() {
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public ExceptionEntity setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ExceptionEntity setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public ExceptionEntity setMessageCode(String messageCode) {
        this.messageCode = messageCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ExceptionEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public List<ExceptionEntity> getSubException() {
        return subException;
    }

    public ExceptionEntity setSubException(List<ExceptionEntity> subException) {
        this.subException = subException;
        return this;
    }

    public void addSubException(ExceptionEntity exceptionEntity) {
        if (this.subException == null) {
            this.subException = new ArrayList<>();
        }
        this.subException.add(exceptionEntity);
    }

    public static ExceptionEntity getExceptionEntity() {
        return new ExceptionEntity();
    }

}
