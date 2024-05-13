package edu.ndsu.cs.estimate.services.tasks;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.CayenneService;



public class CayenneTaskDatabaseService implements TaskDatabaseService{

	private CayenneService cayenneService; 
	
	
	public CayenneTaskDatabaseService(CayenneService cayenneService) {
		this.cayenneService = cayenneService; 
	}
	
	public CayenneService getCayenneService() {
		return cayenneService;
	}
	
	@Override
	public List<? extends Task> listAllTasks(LocalDateTime start, LocalDateTime end, UserAccount user) {
		return ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(false).andExp(Task.DROPPED.eq(false).andExp(Task.EST_END_DATE.between(start, end).andExp(Task.USER.eq((User)user))))).select(cayenneService.newContext());
	}
	
	@Override
	public List<Task> listAllTasks(UserAccount user){
		return ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(false).andExp(Task.DROPPED.eq(false).andExp(Task.USER.eq((User)user)))).select(cayenneService.newContext());
	}

	@Override
	public List<? extends Task> listCompleted(UserAccount user){
		return ObjectSelect.query(Task.class).where(Task.COMPLETED.eq(true).andExp(Task.DROPPED.eq(false))).select(cayenneService.newContext());
	}

	@Override
	public Task getTask(int PK) {
		return Cayenne.objectForPK(cayenneService.newContext(), Task.class, PK);
	}

	@Override
	public Task getNewTask() {
		Task t = cayenneService.newContext().newObject(Task.class); 
		t.setTimestampCreated(LocalDateTime.now());
		return t;
	}

	@Override
	public void deleteTask(int PK) {
		ObjectContext context = cayenneService.newContext();
		Task task = Cayenne.objectForPK(context, Task.class, PK);
		context.deleteObject(task);
		context.commitChanges();
	}

	@Override
	public void updateTask(Task task) {
		// Typecast to access the context for the Cayenne object to commit changes made using it
		((Task)task).getObjectContext().commitChanges();
	}

	@Override
	public List<? extends Task> listDropped(UserAccount user) {
		return ObjectSelect.query(Task.class).where(Task.DROPPED.eq(true).andExp(Task.COMPLETED.eq(false))).select(cayenneService.newContext());

	}

}

