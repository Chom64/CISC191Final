import java.util.Stack;

public class CheatingManager {
    private Stack<Cell> actionHistory;

    public CheatingManager() {
        actionHistory = new Stack<>();
    }

    public void addAction(Cell cell) {
        actionHistory.push(cell);
    }

    public Cell undo() {
        return actionHistory.isEmpty() ? null : actionHistory.pop();
    }
}
