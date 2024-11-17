package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask) {
        if(!subtasks.contains(subtask)) subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void updateStatus() {
        status = Status.NEW;
        boolean allDone = true;
        for(Subtask subtask : subtasks) {
            if(subtask.getStatus() != Status.DONE) {
                allDone = false;
                break;
            }
        }
        if(allDone) {
            status = Status.DONE;
        } else {
            boolean anyInProgress = false;
            for(Subtask subtask : subtasks) {
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
