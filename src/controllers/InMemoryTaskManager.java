package controllers;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int taskCount = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;
    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        this.historyManager = historyManager;
    }

    public static int getTaskCount() {
        return ++taskCount;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }


    @Override
    public void removeTasks() {
        tasks.clear();
    }

    @Override
    public Task getTask(int id) {
        if(tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public int createTask(Task task) {
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        if(!tasks.containsKey(task.getId())) return;
        tasks.put(task.getId(), task);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeSubtasks() {
        for(Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpic(epic);
        }
        subtasks.clear();
    }

    @Override
    public Subtask getSubtask(int id) {
        if(subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public int createSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if(epic == null) return 0;
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        epic.updateStatus();
        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if(!subtasks.containsKey(subtask.getId())) return;
        if(subtasks.get(subtask.getId()).getEpicId() != subtask.getEpicId()) return;
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.updateSubtask(subtask);
        epic.updateStatus();
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if(subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtask(subtask);
            epic.updateStatus();
        }
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeEpics() {
        removeSubtasks();
        epics.clear();
    }

    @Override
    public Epic getEpic(int id) {
        if(epics.containsKey(id)) {
            historyManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public int createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) return;
        epics.put(epic.getId(), epic);
        if(epic.getSubtasks().isEmpty()) {
            List<Subtask> epicSubtasks = getEpicSubtasks(epic.getId());
            for (Subtask subtask : epicSubtasks) {
                epic.addSubtask(subtask);
            }
        }
        epic.updateStatus();
    }

    @Override
    public void removeEpic(int id) {
        Epic epic = epics.remove(id);
        if(epic != null) {
            List<Subtask> epicSubtasks = epic.getSubtasks();
            for(Subtask subtask : epicSubtasks) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        if (!epics.containsKey(id)) return null;
        List<Subtask> epicSubtasks = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if(subtask.getEpicId() == id) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
