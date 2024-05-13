package edu.ndsu.cs.estimate.services.database.implementations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.ObjectId;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import edu.ndsu.cs.estimate.cayenne.persistent.CallibrationEstimate;
import edu.ndsu.cs.estimate.cayenne.persistent.CallibrationExercise;
import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskHourLog;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.EstimationExercise;
import edu.ndsu.cs.estimate.services.database.interfaces.CayenneService;
import edu.ndsu.cs.estimate.services.database.interfaces.TaskHourLogDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

public class CayenneTaskHourLogDatabaseService implements TaskHourLogDatabaseService {
	
	private CayenneService cayenneService;
	
	public CayenneTaskHourLogDatabaseService(CayenneService cayenneService)
	{
		this.cayenneService = cayenneService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskHourLog> getTaskHourLogs(Task task) {
		return ObjectSelect.query(TaskHourLog.class).where(TaskHourLog.TASK.eq(task)).select(cayenneService.newContext());
	}
	
	 public TaskHourLog getTaskHourLog(int taskID, Date timestamp) {
	        Expression expression = ExpressionFactory.matchExp("task", taskID)
	            .andExp(ExpressionFactory.matchExp("timestamp", timestamp));

	        List<TaskHourLog> result = SelectQuery.query(TaskHourLog.class, expression).select(cayenneService.newContext());

	        return result.isEmpty() ? null : result.get(0);
	    }
	 
	 	@Override
		public TaskHourLog getNewLog() {
			return cayenneService.newContext().newObject(TaskHourLog.class); 
		}

	 	@Override
	 	public void addTaskHourLog(Task task, int hours) {
	 		try {
		        ObjectContext context = task.getObjectContext();
		        TaskHourLog log = new TaskHourLog();
		        log.setTimestamp(LocalDateTime.now());
		        log.setHours(hours);
		        log.setTask(task);
		        context.registerNewObject(log);
		        context.commitChanges();
	 		}
	 		catch (Exception e) {
	 			e.printStackTrace();
	 		}
	 	}
}
