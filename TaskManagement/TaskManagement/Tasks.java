package TaskManagement10.TaskManagement;



public class Tasks {
    private String taskName;
    private String taskOwner;
    private String taskId;
    private String taskStatus;
    private String deadline;

    public Tasks(String taskName, String taskOwner, String taskId, String taskStatus, String deadline) {
        this.taskName = taskName;
        this.taskOwner = taskOwner;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        this.deadline = deadline;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
