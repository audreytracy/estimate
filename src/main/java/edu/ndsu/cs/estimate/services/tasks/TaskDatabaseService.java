package edu.ndsu.cs.estimate.services.tasks;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.CayenneService;



public interface TaskDatabaseService {
	public List<? extends Task> listAllTasks(LocalDateTime start, LocalDateTime end, UserAccount user);  
	public List<? extends Task> listCompleted(UserAccount user);
	public List<? extends Task> listDropped(UserAccount user);
	
	public Task	getTask(int PK);
	
	public Task	getNewTask(); 
	
	public void deleteTask(int PK); 
	
	public void	updateTask(Task task);
	
	public CayenneService getCayenneService();
	List<Task> listAllTasks(UserAccount user);
}

