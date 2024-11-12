import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksId;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasksId = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, List<Integer> subtasksId) {
        super(id, name, description, status);
        this.subtasksId = subtasksId;
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }
}
