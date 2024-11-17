package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new HashMap<>();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask) {
        if(!subtasks.containsKey(subtask.getId())) subtasks.put(subtask.getId(),subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask.getId());
    }

    public void updateSubtask(Subtask subtask) {
        if(!subtasks.containsKey(subtask.getId())) return;
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateStatus() {
        status = Status.NEW;
        boolean allDone = true;
        for(Subtask subtask : subtasks.values()) {
            if(subtask.getStatus() != Status.DONE) {
                allDone = false;
                break;
            }
        }
        if(allDone) {
            status = Status.DONE;
        } else {
            boolean anyInProgress = false;
            for(Subtask subtask : subtasks.values()) {
                if(subtask.getStatus() == Status.IN_PROGRESS) {
                    anyInProgress = true;
                    break;
                }
            }
            if(anyInProgress) {
                status = Status.IN_PROGRESS;
            }
        }
    }
}
