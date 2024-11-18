package model;

import controllers.TaskManager;

import java.util.Objects;

public class Task {


    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.id = TaskManager.getTaskCount();
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Task)) return false;
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
