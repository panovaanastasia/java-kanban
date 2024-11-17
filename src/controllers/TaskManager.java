package controllers;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private static int taskCount = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static int getTaskCount() {
        return ++taskCount;
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
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

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeSubtasks() {
        for(Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpic(epic);
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
        epic.addSubtask(subtask);
        updateEpic(epic);
    }

    public void updateSubtask(Subtask subtask) {
        if(!subtasks.containsKey(subtask.getId())) return;
        if(subtasks.get(subtask.getId()).getEpicId() != subtask.getEpicId()) return;
        subtasks.put(subtask.getId(), subtask);
        if(subtask.getStatus() != Status.NEW) {
            updateEpic(epics.get(subtask.getEpicId()));
        }
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if(subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtask(subtask);
            updateEpic(epic);
        }
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
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
        epics.put(epic.getId(), epic);
        updateEpicSubtasks(epic);
        epic.updateStatus();
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if(epic != null) {
            List<Subtask> epicSubtasks = epic.getSubtasks();
            for(Subtask subtask : epicSubtasks) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    public List<Subtask> getSubtaskListByEpicId(int id) {
        if (!epics.containsKey(id)) return null;
        List<Subtask> epicSubtasks = new ArrayList<>();
        for(Subtask subtask : subtasks.values()) {
            if(subtask.getEpicId() == id) {
                epicSubtasks.add(subtask);
            }
        }
        return epicSubtasks;
    }

    private void updateEpicSubtasks(Epic epic) {
        epic.clearSubtasks();
        List<Subtask> epicSubtasks = getSubtaskListByEpicId(epic.getId());
        for(Subtask subtask : epicSubtasks) {
            epic.addSubtask(subtask);
        }
    }
}
