package controllers;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    ArrayList<Task> getTasks();

    void removeTasks();

    Task getTask(int id);

    void createTask(Task task);

    void updateTask(Task task);

    void removeTask(int id);

    ArrayList<Subtask> getSubtasks();

    void removeSubtasks();

    Subtask getSubtask(int id);

    void createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void removeSubtask(int id);

    ArrayList<Epic> getEpics();

    void removeEpics();

    Epic getEpic(int id);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpic(int id);

    List<Subtask> getEpicSubtasks(int id);

    List<Task> getHistory();
}
