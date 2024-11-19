package controllers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager;
    @BeforeEach
    void before() {
        taskManager = Managers.getDefault();
    }

    @Test
    void removeTasks() {
        for(int i = 0; i < 5; i++) {
            taskManager.createTask(new Task("задача", "важная задача"));
        }
        assertEquals(5, taskManager.getTasks().size());
        taskManager.removeTasks();
        assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void createTask() {
        Task task = new Task("task", "some task");
        int id = taskManager.createTask(task);
        assertEquals(1, taskManager.getTasks().size());
        assertEquals(task, taskManager.getTask(id));
        assertEquals(task.getName(), taskManager.getTask(id).getName());
        assertEquals(task.getDescription(), taskManager.getTask(id).getDescription());
        assertEquals(task.getStatus(), taskManager.getTask(id).getStatus());
    }

    @Test
    void updateTask() {
        Task task = new Task("task", "some task");
        int id = taskManager.createTask(task);
        assertEquals(task, taskManager.getTask(id));
        Task updatedTask = new Task(id, "updated task", "updated task description", Status.IN_PROGRESS);
        taskManager.updateTask(updatedTask);
        assertEquals(updatedTask, taskManager.getTask(id));
        assertEquals(updatedTask.getName(), taskManager.getTask(id).getName());
        assertEquals(updatedTask.getDescription(), taskManager.getTask(id).getDescription());
        assertEquals(updatedTask.getStatus(), taskManager.getTask(id).getStatus());
    }

    @Test
    void removeTask() {
        Task task = new Task("task", "some task");
        int id = taskManager.createTask(task);
        assertEquals(task, taskManager.getTask(id));
        taskManager.removeTask(id);
        assertNull(taskManager.getTask(id));
    }

    @Test
    void removeSubtasks() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(2, taskManager.getSubtasks().size());
        assertEquals(2, taskManager.getEpic(idEpic).getSubtasks().size());
        taskManager.removeSubtasks();
        assertEquals(0, taskManager.getSubtasks().size());
        assertEquals(0, taskManager.getEpic(idEpic).getSubtasks().size());
        assertEquals(Status.NEW, taskManager.getEpic(idEpic).getStatus());
    }
    @Test
    void updateSubtaskWithInProgressStatus() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        int idSubtask = taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(Status.NEW, taskManager.getEpic(idEpic).getStatus());
        Subtask updatedSubtask = new Subtask(idSubtask, "subtask", "updated subtask", Status.IN_PROGRESS, idEpic);
        taskManager.updateSubtask(updatedSubtask);
        assertEquals(updatedSubtask, taskManager.getSubtask(idSubtask));
        assertEquals(updatedSubtask.getName(), taskManager.getSubtask(idSubtask).getName());
        assertEquals(updatedSubtask.getDescription(), taskManager.getSubtask(idSubtask).getDescription());
        assertEquals(updatedSubtask.getStatus(), taskManager.getSubtask(idSubtask).getStatus());
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(idEpic).getStatus());
    }

    @Test
    void updateSubtaskWithDoneStatus() {
        ArrayList<Integer> subtasks = new ArrayList<>();
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        for(int i = 0; i < 2; i++) {
            int id = taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
            subtasks.add(id);
        }
        assertEquals(Status.NEW, taskManager.getEpic(idEpic).getStatus());
        for(Integer id : subtasks) {
            taskManager.updateSubtask(new Subtask(id, "subtask", "some subtask", Status.DONE, idEpic));
        }
        assertEquals(Status.DONE, taskManager.getEpic(idEpic).getStatus());
    }

    @Test
    void removeSubtask() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        int idSubtask = taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(Status.NEW, taskManager.getEpic(idEpic).getStatus());
        Subtask updatedSubtask = new Subtask(idSubtask, "subtask", "updated subtask", Status.IN_PROGRESS, idEpic);
        taskManager.updateSubtask(updatedSubtask);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(idEpic).getStatus());
        taskManager.removeSubtask(idSubtask);
        assertNull(taskManager.getSubtask(idSubtask));
        assertEquals(Status.NEW, taskManager.getEpic(idEpic).getStatus());
    }

    @Test
    void removeEpics() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(2, taskManager.getSubtasks().size());
        taskManager.removeEpics();
        assertEquals(0, taskManager.getEpics().size());
        assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        int idSubtask = taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        taskManager.updateSubtask(new Subtask(idSubtask, "subtask", "updated subtask", Status.IN_PROGRESS, idEpic));
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(epic, taskManager.getEpic(idEpic));
        assertEquals(3, taskManager.getEpic(idEpic).getSubtasks().size());
        Epic updatedEpic = new Epic(idEpic, "epic", "updated epic");
        taskManager.updateEpic(updatedEpic);
        assertEquals(updatedEpic, taskManager.getEpic(idEpic));
        assertEquals(3, taskManager.getEpic(idEpic).getSubtasks().size());
        assertEquals(Status.IN_PROGRESS, taskManager.getEpic(idEpic).getStatus());
    }

    @Test
    void removeEpic() {
        Epic epic = new Epic("epic1", "some epic");
        int idEpic = taskManager.createEpic(epic);
        for(int i = 0; i < 2; i++) {
            taskManager.createSubtask(new Subtask("subtask", "some subtask", idEpic));
        }
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(2, taskManager.getEpicSubtasks(idEpic).size());
        taskManager.removeEpic(idEpic);
        assertNull(taskManager.getEpic(idEpic));
        assertNull(taskManager.getEpicSubtasks(idEpic));
    }
}