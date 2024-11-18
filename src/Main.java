import controllers.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        manager.createTask(new Task("задача", "важная задача"));
        manager.createTask(new Task("задача2", "очень важная задача"));

        manager.createEpic(new Epic("эпик", "большой эпик"));

        manager.createSubtask(new Subtask("подзадача", "из 1 эпика", 3));
        manager.createSubtask(new Subtask("подзадача2", "из 1 эпика", 3));

        manager.createEpic(new Epic("эпик", "маленький эпик"));

        manager.createSubtask(new Subtask("подзадача", "из 2 эпика", 6));

        for(Task task: manager.getTasks()) {
            System.out.println(task);
        }

        for(Subtask subtask: manager.getSubtasks()) {
            System.out.println(subtask);
        }

        for(Epic epic: manager.getEpics()) {
            System.out.println(epic);
        }

        manager.updateTask(new Task(1, "задача", "важная задача", Status.IN_PROGRESS));
        System.out.println(manager.getTaskById(1));
        
        manager.updateSubtask(new Subtask(5, "подзадача2", "из 1 эпика", Status.IN_PROGRESS, 3));
        System.out.println(manager.getSubtaskById(5));
        System.out.println(manager.getEpicById(3));

        manager.removeSubtaskById(4);
        for(Subtask subtask: manager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println(manager.getEpicById(3));

        manager.removeEpicById(6);
        for(Subtask subtask: manager.getSubtasks()) {
            System.out.println(subtask);
        }
        for(Epic epic: manager.getEpics()) {
            System.out.println(epic);
        }
    }
}
