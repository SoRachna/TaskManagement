package TaskManagement10.TaskManagement;

import java.util.ArrayList;

public class Project {
    private String projectName;
    private String projectStatus;
    private ArrayList<Tasks> tasks;

    public Project(String projectName, String projectStatus) {
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.tasks = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void addTask(Tasks task) {
        this.tasks.add(task);
    }

    public ArrayList<Tasks> getTasks() {
        return tasks;
    }
}
