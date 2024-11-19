package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testEquals() {
        Task taskNew = new Task(1, "задача1", "важная задача", Status.NEW);
        Task taskInProgress = new Task(1, "задача1 обновленная", "важная задача", Status.IN_PROGRESS);
        assertEquals(taskNew, taskInProgress);
    }

    @Test
    void testNotEquals() {
        Task task1 = new Task(1, "задача1", "важная задача", Status.NEW);
        Task task2 = new Task(2, "задача1", "важная задача", Status.NEW);
        assertNotEquals(task1, task2);
    }

    @Test
    void testEqualsWithDiffConstructors() {
        Task taskNew = new Task("задача1", "важная задача");
        Task taskInProgress = new Task(taskNew.getId(), "задача1 обновленная", "важная задача", Status.IN_PROGRESS);
        assertEquals(taskNew, taskInProgress);
    }

    @Test
    void testEqualsWithEpic() {
        Task task = new Task(1, "задача", "важная задача", Status.NEW);
        Epic epic = new Epic(1, "эпик", "важный эпик");
        assertEquals(task, epic);
    }

    @Test
    void testEqualsWithSubtask() {
        Task task = new Task(1, "задача", "важная задача", Status.NEW);
        Subtask subtask = new Subtask(1, "подзадача", "небольшое дело", Status.NEW, 1);
        assertEquals(task, subtask);
    }
}