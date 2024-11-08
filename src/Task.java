import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || !(obj instanceof Task)) return false;
        Task task = (Task) obj;
        return task.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{"
                + "id=" + id
                + ", name='" + name + "'"
                + ", description='" + description + "'"
                + ", status=" + status + "}";
    }
}
