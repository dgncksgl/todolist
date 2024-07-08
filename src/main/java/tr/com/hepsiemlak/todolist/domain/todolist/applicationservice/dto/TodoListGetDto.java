package tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto;

import tr.com.hepsiemlak.todolist.domain.todolist.*;

import java.time.LocalDateTime;
import java.util.List;

public class TodoListGetDto {

    private String id;

    private String title;

    private String note;

    private TodolistItemStatus status;

    private TodoListItemPriority priority;

    private LocalDateTime endTime;

    private String userId;

    private List<Tags> tags;

    private List<AssignedUsers> assignedUsers;

    private List<Documents> documents;

    private TodoListGetDto() {
    }

    public String getId() {
        return id;
    }

    public TodoListGetDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TodoListGetDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNote() {
        return note;
    }

    public TodoListGetDto setNote(String note) {
        this.note = note;
        return this;
    }

    public TodolistItemStatus getStatus() {
        return status;
    }

    public TodoListGetDto setStatus(TodolistItemStatus status) {
        this.status = status;
        return this;
    }

    public TodoListItemPriority getPriority() {
        return priority;
    }

    public TodoListGetDto setPriority(TodoListItemPriority priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TodoListGetDto setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TodoListGetDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public TodoListGetDto setTags(List<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public List<AssignedUsers> getAssignedUsers() {
        return assignedUsers;
    }

    public TodoListGetDto setAssignedUsers(List<AssignedUsers> assignedUsers) {
        this.assignedUsers = assignedUsers;
        return this;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public TodoListGetDto setDocuments(List<Documents> documents) {
        this.documents = documents;
        return this;
    }

    public static TodoListGetDto convertToTodoListGetDtoFromTodoList(TodoList todoList) {
        return new TodoListGetDto()
                .setId(todoList.getId())
                .setTitle(todoList.getTitle())
                .setNote(todoList.getNote())
                .setStatus(todoList.getStatus())
                .setPriority(todoList.getPriority())
                .setEndTime(todoList.getEndTime())
                .setUserId(todoList.getUserId())
                .setTags(todoList.getTags())
                .setAssignedUsers(todoList.getAssignedUsers())
                .setDocuments(todoList.getDocuments());
    }
}
