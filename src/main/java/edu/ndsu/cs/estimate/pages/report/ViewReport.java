package edu.ndsu.cs.estimate.pages.report;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.Property;
/*import org.apache.tapestry5.beanmodel.BeanModel;
import org.apache.tapestry5.beanmodel.services.BeanModelSource;
import org.apache.tapestry5.commons.Messages;*/
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.cs.estimate.cayenne.persistent.Report;
import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskHourLog;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskInReport;
import edu.ndsu.cs.estimate.services.database.interfaces.ReportDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.TaskHourLogDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class ViewReport {
	
	@Inject
	private ReportDatabaseService reportDBS;

	@Inject
	private TaskHourLogDatabaseService thldbs;
	
	@Property
	private Report report; 
	
	@Property
	private List<TaskHourLog> log_records;
	
	@Property
	private TaskInReport taskInReport; 
	
	@Property
	private Task task; 
	
	@Property
	private TaskHourLog log;
	
	@Property
	private List<TaskInReport> tasks;
		
	@Property
	private List<TaskInReport> inProgressTasks;
	
	@Property
	private List<TaskInReport> newlyCompletedTasks;
	
	@Property
	private List<TaskInReport> newlyDroppedTasks;
	
	
	@Property
	private Integer reportPK;
	
	void onActivate(Integer reportPK) {
		this.reportPK = reportPK;
	}
	
	Integer onPassivate() {
		return reportPK;
	}
	
	void setupRender() {
		if(reportPK != null) {
			report = reportDBS.getReport(reportPK);
			tasks = report.getTasksInReport();
		}
	}

	public List<Task> getCompletedTaskList(){
		return this.reportDBS.getCompletedTasks(report.getPK());
	}
	
	public List<Task> getDroppedTaskList(){
		return this.reportDBS.getDroppedTasks(report.getPK());
	}
	
	public List<Task> getInProgressTaskList(){
		return this.reportDBS.getInProgressTasks(report.getPK());
	}
	
	public int getHours(Task task) {
		List<TaskHourLog> list = getTHL(task);
		int hoursTotal = 0;
		for(TaskHourLog l : list) {
			if(l.getTimestamp().compareTo(report.getDateGenerated())<0) {
				hoursTotal+=l.getHours();
			}
		}
		return hoursTotal;
	}
	
	public double getRatio(Task task) { // for completed tasks
		return ((int)(task.getTimeTaken()*100.0/task.getEstimatedHours()+0.5))/100.0;
	}
	
	public String getAnalysis(Task task) {
		LocalDateTime start = task.getStartDate();
		LocalDateTime end = task.getActualEndDate();//(task.getCompleted() || task.getDropped()) ? task.getActualEndDate() : task.getEstEndDate();
		LocalDateTime midpoint = start.plus(start.until(end, ChronoUnit.HOURS), ChronoUnit.HOURS);
		int hoursBeforeHalf = 0;
		int hoursAfterHalf = 0;
		List<TaskHourLog> list = getTHL(task);
		for(TaskHourLog thl : list) {
			if(thl.getTimestamp().compareTo(midpoint)<=0) { // if the log was before or at the midpoint
				hoursBeforeHalf += thl.getHours();
			}
			else hoursAfterHalf += thl.getHours(); // otherwise (log after midpoint)
		}
		int totalHours = hoursAfterHalf + hoursBeforeHalf;
		double ratioAfterHalf = (double)hoursAfterHalf / totalHours;
		if(ratioAfterHalf >= 0.9) {
			return "Time management on this task is severely skewed, over 90% of hours have been logged in the second half of the project.";
		}
		if(ratioAfterHalf >= 0.8) {
			return "Time management on this task is heavily skewed, 80% - 90% of hours have been logged in the second half of the project.";
		}
		if(ratioAfterHalf >= 0.7) {
			return "Time management on this task is moderately skewed, 70% - 80% of hours have been logged in the second half of the project.";
		}
		if(ratioAfterHalf >= 0.6) {
			return "Time management on this task is moderately skewed, 60% - 70% of hours have been logged in the second half of the project.";
		}
		if(ratioAfterHalf >= 0.5) {
			return "Time management on this task is slightly skewed, 50% - 60% of hours have been logged in the second half of the project.";
		}
		return "Time management on this task is fairly consistent, procrastination minimal. Less than 50% of the hours logged in the second half of the project.";		
	}
	
	public List<TaskHourLog> getTHL(Task task){
		List<TaskHourLog> taskHourLogsBeforeGenerated = new ArrayList();
		log_records = thldbs.getTaskHourLogs(task);
		for(TaskHourLog l : log_records) {
			if(l.getTimestamp().compareTo(report.getDateGenerated())<0) {
				taskHourLogsBeforeGenerated.add(l);
			}
		}
		return taskHourLogsBeforeGenerated;
	}
	
	public String formatDate(Date d) {
		@SuppressWarnings("deprecation")
		LocalDate ld = LocalDate.of(d.getYear()+1900, d.getMonth()+1, d.getDate());
		return ld.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
	}
	
	public boolean isOver1(double n) {
		return n>1;
	}
}

