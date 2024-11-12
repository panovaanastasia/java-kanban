
public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        Task task1 = new Task("задача", "важная задача");
        Task task2 = new Task("задача2", "очень важная задача");
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic1 = new Epic("эпик", "большой эпик");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("подзадача", "из 1 эпика", 3);
        Subtask subtask2 = new Subtask("подзадача2", "из 1 эпика", 3);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic("эпик", "маленький эпик");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("подзадача", "из 2 эпика", 6);
        manager.createSubtask(subtask3);

        for(Task task: manager.getTasks().values()) {
            System.out.println(task);
        }

        for(Subtask subtask: manager.getSubtasks().values()) {
            System.out.println(subtask);
        }

        for(Epic epic: manager.getEpics().values()) {
            System.out.println(epic);
        }

        Task updatedTask1 = new Task(1, "задача", "важная задача", Status.IN_PROGRESS);
        manager.updateTask(updatedTask1);
        System.out.println(manager.getTaskById(1));

        Subtask updatedSubtask2 = new Subtask(5, "подзадача2", "из 1 эпика", Status.IN_PROGRESS, 3);
        manager.updateSubtask(updatedSubtask2);
        System.out.println(manager.getSubtaskById(5));
        System.out.println(manager.getEpicById(3));

        manager.removeSubtaskById(4);
        for(Subtask subtask: manager.getSubtasks().values()) {
            System.out.println(subtask);
        }
        System.out.println(manager.getEpicById(3));

        manager.removeEpicById(6);
        for(Subtask subtask: manager.getSubtasks().values()) {
            System.out.println(subtask);
        }
        for(Epic epic: manager.getEpics().values()) {
            System.out.println(epic);
        }
    }
}
