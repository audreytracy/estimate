package edu.ndsu.cs.estimate.services.database.implementations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import edu.ndsu.cs.estimate.cayenne.persistent.Report;
import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskInReport;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.services.database.interfaces.CayenneService;
import edu.ndsu.cs.estimate.services.database.interfaces.ReportDatabaseService;

public class CayenneReportDatabaseService implements ReportDatabaseService {

	private CayenneService cayenneService;
	
	public CayenneReportDatabaseService(CayenneService cayenneService)
	{
		this.cayenneService = cayenneService;
	}

	@Override
	public List<Report> getAllReports(User user) {
		return ObjectSelect.query(Report.class).where(Report.USER.eq((User)user)).select(cayenneService.newContext());
	}
	
	@Override
	public List<Report> getReports(LocalDate start, LocalDate end, User user) {
		return ObjectSelect.query(Report.class).where(Report.DATE_GENERATED.between(start.atTime(LocalTime.MIDNIGHT), end.atTime(LocalTime.MIDNIGHT)).andExp(Report.USER.eq((User)user))).select(cayenneService.newContext());
	}
	
	@Override
	public Report getReport(int PK) {
		return Cayenne.objectForPK(cayenneService.newContext(), Report.class, PK);
	}

	@Override
	public List<Task> getCompletedTasks(int reportID){
		Report report = getReport(reportID);
	    Report previousReport = ObjectSelect.query(Report.class).where(Report.USER.eq((User) report.getUser()).andExp(Report.DATE_GENERATED.lt(report.getDateGenerated()))).orderBy(Report.DATE_GENERATED.desc()).selectFirst(report.getObjectContext());
	    LocalDateTime most_recent = (previousReport != null) ? previousReport.getDateGenerated() : LocalDate.of(1900, 1, 1).atStartOfDay();
	    return ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(true).andExp(Task.ACTUAL_END_DATE.between(most_recent, report.getDateGenerated()).andExp(Task.USER.eq((User) report.getUser())))).select(report.getObjectContext());
	}
	
	@Override
	public List<Task> getDroppedTasks(int reportID) {
	    Report report = getReport(reportID);
	    Report previousReport = ObjectSelect.query(Report.class).where(Report.USER.eq((User) report.getUser()).andExp(Report.DATE_GENERATED.lt(report.getDateGenerated()))).orderBy(Report.DATE_GENERATED.desc()).selectFirst(report.getObjectContext());
	    LocalDateTime most_recent = (previousReport != null) ? previousReport.getDateGenerated() : LocalDate.of(1900, 1, 1).atStartOfDay();
	    return ObjectSelect.query(Task.class).where(Task.DROPPED.eq(true).andExp(Task.ACTUAL_END_DATE.between(most_recent, report.getDateGenerated()).andExp(Task.USER.eq((User) report.getUser())))).select(report.getObjectContext());
	}
	
	@Override
	public List<Task> getInProgressTasks(int reportID){
		// list all tasks that are either currently in progress & had start date before report generated, or are currently ended but had start date before report & end date is after report
		Report report = getReport(reportID);
	    List<Task> stillNotClosed = ObjectSelect.query(Task.class)
	    		.where(Task.COMPLETED.eq(false)
	    				.andExp(Task.DROPPED.eq(false))
	    				.andExp(Task.TIMESTAMP_CREATED.between(LocalDate.of(1800,1,1).atStartOfDay(), report.getDateGenerated().minusSeconds(1)))
	    				.andExp(Task.USER.eq((User)report.getUser())))
	    		.select(report.getObjectContext());
	    List<Task> sinceDroppedOrCompleted = ObjectSelect.query(Task.class).where((Task.DROPPED.eq(true).orExp(Task.COMPLETED.eq(true))).andExp(Task.ACTUAL_END_DATE.between(report.getDateGenerated(), LocalDateTime.now())).andExp(Task.START_DATE.between(LocalDate.of(1800,1,1).atStartOfDay(), report.getDateGenerated().minusSeconds(1))).andExp(Task.USER.eq((User)report.getUser()))).select(report.getObjectContext());
	    List<Task> returnList = stillNotClosed;
	    returnList.addAll(sinceDroppedOrCompleted);
	    return returnList;
	}
	
	public double getCurrentMultiplier(User user) {
		Report most_recent = ObjectSelect.query(Report.class).where(Report.USER.eq((User)user)).orderBy(Report.DATE_GENERATED.desc()).selectFirst(user.getObjectContext()); // all reports for user
		return (most_recent == null) ? 1.0 : most_recent.getPredictionMultiplier();
	}
	
	@SuppressWarnings({"unused"})
	@Override
	public void generateReport(User user) {
		List<Report> list = ObjectSelect.query(Report.class).where(Report.USER.eq((User)user)).select(cayenneService.newContext()); // all reports for user
		LocalDateTime most_recent = LocalDateTime.of(1900,1,1, 0, 0); // "minimum" date
		if(list.size() > 0) {
			for(Report r : list) {
				LocalDateTime temp = r.getDateGenerated();
				if(temp.compareTo(most_recent)>0) {
					most_recent = temp;
				}
			}
		}
		// bounds of report are most_recent and current date
        ObjectContext context = user.getObjectContext();
        Report report = context.newObject(Report.class);
        report.setDateGenerated(LocalDateTime.now());
        report.setUser(user);
	    List<Task> tasksCompletedSinceLastReport = ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(true).andExp(Task.ACTUAL_END_DATE.between(most_recent, LocalDateTime.now()).andExp(Task.USER.eq((User)user)))).select(context);
	    List<Task> tasksDroppedSinceLastReport = ObjectSelect.query(Task.class).where(Task.DROPPED.eq(true).andExp(Task.ACTUAL_END_DATE.between(most_recent, LocalDateTime.now()).andExp(Task.USER.eq((User)user)))).select(context);
	    List<Task> tasksInProgress = ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(false).andExp(Task.DROPPED.eq(false).andExp(Task.USER.eq((User)user)))).select(context);
	    List<Task> tasksInReport = new ArrayList();
	    tasksInReport.addAll(tasksCompletedSinceLastReport);
	    tasksInReport.addAll(tasksDroppedSinceLastReport);
	    tasksInReport.addAll(tasksInProgress);
	    	
	    List<Task> allCompleted = ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(true).andExp(Task.DROPPED.eq(false))).select(context);

	    double multiplier = 0.0;
	    int n = allCompleted.size();
	    LocalDateTime current_date = LocalDate.now().atStartOfDay();
	    LocalDateTime lastHalfYearCutoff = current_date.minus(6, ChronoUnit.MONTHS);
	    LocalDateTime lastYearCutoff = current_date.minus(12, ChronoUnit.MONTHS);
	    LocalDateTime lastTwoYearsCutoff = current_date.minus(24, ChronoUnit.MONTHS);
	    if(n>0) {
		    for(Task task : allCompleted) { // calculate multiplier
		    	double weight = 0.1; // if task ended more than two years ago
		    	if(task.getActualEndDate().compareTo(lastHalfYearCutoff) > 0) { // if task ended within the last 6 months
		    		weight = 1.0;
		    	}
		    	else if(task.getActualEndDate().compareTo(lastYearCutoff) > 0) { // if task ended within the last year
		    		weight = 0.7;
		    	}
		    	else if(task.getActualEndDate().compareTo(lastTwoYearsCutoff) > 0) { // if task ended within the last two years
		    		weight = 0.4;
		    	}
		    	multiplier += ((double)task.getTimeTaken() / task.getEstimatedHours()) * weight; // older data is weighted less (devalued)
		    }
		    multiplier /= n;
	    }
	    else multiplier = 1.0;
	    multiplier = (int)(multiplier*1000 + 0.5)/1000.0; // round to three decimal places
	    System.out.println(multiplier);
	    report.setPredictionMultiplier(multiplier);
	    
	    for(Task task : tasksInReport) { // put each Task in TaskInReport table
	    	TaskInReport t = context.newObject(TaskInReport.class);
	    	t.setReport(report);
	    	t.setTaskInReport(task);
	    }
        context.registerNewObject(report);
        context.commitChanges();
	}
	
	@Override
	public TaskInReport getNewTaskInReport() {
		return cayenneService.newContext().newObject(TaskInReport.class); 
	}

}
