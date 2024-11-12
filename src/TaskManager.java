import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void removeTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        if(!tasks.containsKey(task.getId())) return;
        tasks.put(task.getId(), task);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void removeSubtasks() {
        for(Epic epic : epics.values()) {
            Epic updatedEpic = new Epic(epic.getId(), epic.getName(), epic.getDescription(), epic.getStatus(), new ArrayList<>());
            epics.put(epic.getId(), updatedEpic);
        }
        subtasks.clear();
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if(epic == null) return;
        subtasks.put(subtask.getId(), subtask);
        List<Integer> subtasksId = epic.getSubtasksId();
        subtasksId.add(subtask.getId());
        Epic updatedEpic = new Epic(epic.getId(), epic.getName(), epic.getDescription(), epic.getStatus(), subtasksId);
        epics.put(epic.getId(), updatedEpic);
    }

    public void updateSubtask(Subtask subtask) {
        if(!subtasks.containsKey(subtask.getId())) return;
        subtasks.put(subtask.getId(), subtask);
        if(subtask.getStatus() != Status.NEW) {
            updateEpic(epics.get(subtask.getEpicId()));
        }
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if(subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            List<Integer> subtasksId = epic.getSubtasksId();
            subtasksId.remove(Integer.valueOf(subtask.getId()));
            Epic updatedEpic = new Epic(epic.getId(), epic.getName(), epic.getDescription(), epic.getStatus(), subtasksId);
            epics.put(epic.getId(), updatedEpic);
        }
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void removeEpics() {
        removeSubtasks();
        epics.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) return;
        List<Integer> subtasksId = epic.getSubtasksId();
        Status updatedStatus = Status.NEW;
        boolean allDone = true;
        for(Integer subtaskId : subtasksId) {
            if(subtasks.get(subtaskId).getStatus() != Status.DONE) {
                allDone = false;
                break;
            }
        }
        if(allDone) {
            updatedStatus = Status.DONE;
        } else {
            boolean anyInProgress = false;
            for(Integer subtaskId : subtasksId) {
                if(subtasks.get(subtaskId).getStatus() == Status.IN_PROGRESS) {
                    anyInProgress = true;
                    break;
                }
            }
            if(anyInProgress) {
                updatedStatus = Status.IN_PROGRESS;
            }
        }
        epics.put(epic.getId(), new Epic(epic.getId(), epic.getName(), epic.getDescription(), updatedStatus, epic.getSubtasksId()));
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if(epic != null) {
            List<Integer> subtasksId = epic.getSubtasksId();
            for(Integer subtaskId : subtasksId) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public List<Subtask> getSubtaskListByEpicId(int id) {
        if (!epics.containsKey(id)) return null;
        List<Integer> subtasksId = epics.get(id).getSubtasksId();
        List<Subtask> epicSubtasks = new ArrayList<>();
        for(Integer subtaskId : subtasksId) {
            epicSubtasks.add(subtasks.get(subtaskId));
        }
        return epicSubtasks;
    }
}
