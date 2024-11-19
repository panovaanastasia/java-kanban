package controllers;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager historyManager;
    @BeforeEach
    void before() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void add() {

        Task task = new Task("задача", "важная задача");
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    @Test
    void addMoreThen10Tasks() {
        Task task = new Task("задача", "важная задача");
        for(int i = 0; i < 11; i++)
            historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history);
        assertEquals(10, history.size());
    }
}