package edu.ndsu.cs.estimate.services.database.interfaces;

import java.time.LocalDate;
import java.util.List;

import edu.ndsu.cs.estimate.cayenne.persistent.Report;
import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskInReport;
import edu.ndsu.cs.estimate.cayenne.persistent.User;

public interface ReportDatabaseService {
	public List<Report> getReports(LocalDate start, LocalDate end, User user); 
	Report getReport(int PK);
	void generateReport(User user);
	List<Report> getAllReports(User user);
	TaskInReport getNewTaskInReport();
	List<Task> getCompletedTasks(int reportID);
	List<Task> getDroppedTasks(int reportID);
	List<Task> getInProgressTasks(int reportID);
}