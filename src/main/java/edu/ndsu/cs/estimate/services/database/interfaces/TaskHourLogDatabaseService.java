package edu.ndsu.cs.estimate.services.database.interfaces;

import java.util.Date;
import java.util.List;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskHourLog;

public interface TaskHourLogDatabaseService {
	public List<TaskHourLog> getTaskHourLogs(Task task); 
	public void addTaskHourLog(Task task, int hours);
	public TaskHourLog getNewLog();
}
