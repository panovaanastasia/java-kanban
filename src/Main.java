import controllers.InMemoryTaskManager;
import controllers.Managers;
import controllers.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        manager.createTask(new Task("задача", "важная задача"));
        manager.createTask(new Task("задача2", "очень важная задача"));

        manager.createEpic(new Epic("эпик", "большой эпик"));

        manager.createSubtask(new Subtask("подзадача", "из 1 эпика", 3));
        manager.createSubtask(new Subtask("подзадача2", "из 1 эпика", 3));

        manager.createEpic(new Epic("эпик", "маленький эпик"));

        manager.createSubtask(new Subtask("подзадача", "из 2 эпика", 6));

        manager.updateTask(new Task(1, "задача", "важная задача", Status.IN_PROGRESS));
        System.out.println(manager.getTask(1));

        manager.updateSubtask(new Subtask(5, "подзадача2", "из 1 эпика", Status.IN_PROGRESS, 3));
        System.out.println(manager.getSubtask(5));
        System.out.println(manager.getEpic(3));

        manager.removeSubtask(4);
        System.out.println(manager.getEpic(3));

        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
