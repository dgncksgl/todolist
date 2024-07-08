package tr.com.hepsiemlak.todolist.domain.todolist;

public enum TodoListItemPriority {

    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private final String label;

    TodoListItemPriority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
