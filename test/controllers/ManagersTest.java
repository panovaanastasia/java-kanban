package controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefault() {
        TaskManager taskManager= Managers.getDefault();
        assertNotNull(taskManager);
        assertNotNull(taskManager.getTasks());
        assertEquals(taskManager.getTasks().size(), 0);
        assertNotNull(taskManager.getSubtasks());
        assertEquals(taskManager.getSubtasks().size(), 0);
        assertNotNull(taskManager.getEpics());
        assertEquals(taskManager.getEpics().size(), 0);
    }

    @Test
    void getDefaultHistory() {
        HistoryManager historyManager= Managers.getDefaultHistory();
        assertNotNull(historyManager);
        assertNotNull(historyManager.getHistory());
        assertEquals(historyManager.getHistory().size(), 0);
    }
}