package tr.com.hepsiemlak.todolist.domain.todolist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
public class TodoList {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull
    @Size(min = 2, max = 50)
    @Field("title")
    private String title;

    @Size(max = 200)
    @Field("note")
    private String note;

    @NotNull
    @Field("status")
    private TodolistItemStatus status = TodolistItemStatus.NOT_DONE;

    @Field("priority")
    private TodoListItemPriority priority;

    @Field("endTime")
    private LocalDateTime endTime;

    @NotNull
    @Field("userId")
    private String userId;

    @Field("tags")
    private List<Tags> tags;

    @Field("assignedUsers")
    private List<AssignedUsers> assignedUsers;

    @Field("documents")
    private List<Documents> documents;

    public String getId() {
        return id;
    }

    public TodoList setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TodoList setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNote() {
        return note;
    }

    public TodoList setNote(String note) {
        this.note = note;
        return this;
    }

    public TodolistItemStatus getStatus() {
        return status;
    }

    public TodoList setStatus(TodolistItemStatus status) {
        this.status = status;
        return this;
    }

    public TodoListItemPriority getPriority() {
        return priority;
    }

    public TodoList setPriority(TodoListItemPriority priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TodoList setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TodoList setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public TodoList setTags(List<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public List<AssignedUsers> getAssignedUsers() {
        return assignedUsers;
    }

    public TodoList setAssignedUsers(List<AssignedUsers> assignedUsers) {
        this.assignedUsers = assignedUsers;
        return this;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public TodoList setDocuments(List<Documents> documents) {
        this.documents = documents;
        return this;
    }
}
