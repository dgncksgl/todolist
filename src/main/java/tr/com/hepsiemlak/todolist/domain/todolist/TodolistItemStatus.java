package tr.com.hepsiemlak.todolist.domain.todolist;

public enum TodolistItemStatus {

    DONE("DONE"),
    NOT_DONE("NOT_DONE");

    private final String label;

    TodolistItemStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
