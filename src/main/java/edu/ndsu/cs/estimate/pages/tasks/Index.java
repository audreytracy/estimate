package edu.ndsu.cs.estimate.pages.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.TaskHourLogDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.CayenneTaskFactory;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class Index {

	@SuppressWarnings("deprecation")
	@Property
	@Persist
	private LocalDateTime start; 
	
	@SuppressWarnings("deprecation")
	@Property 
	@Persist
	private LocalDateTime end;
	
	@Persist
	@Property
	private int hours;
	
	@Inject
	private TaskDatabaseService taskDBS;
	
	@Inject
	private TaskHourLogDatabaseService taskHourLogDBS;
	
	
	@Persist
	@Property
	private List<? extends Task> tasks;
	
	@Property
	private Task task;
	
	@InjectComponent
	Form dateForm;
	
	@InjectComponent
	Form addHourForm;
	
	@Inject
	private SecurityService securityService;

	@Inject
	private UserAccountDatabaseService userAccountDatabaseService;
	
	@Property
	@Persist
	private UserAccount	userAccount;
	
	void setupRender() {
		if(start == null) {
			start = LocalDate.of(1970, 1, 1).atStartOfDay();
		}
		if(end == null) {
			end = LocalDate.of(2038, 1, 1).atStartOfDay();
		}
		String principal = securityService.getSubject().getPrincipal().toString(); 
		userAccount = userAccountDatabaseService.getUserAccount(principal);
		tasks = taskDBS.listAllTasks(this.start,this.end, this.userAccount);
	}
	
	void onDelete(int PK) {
		taskDBS.deleteTask(PK);
	}
	
	void onSubmitFromAddHourForm(int pk) {
		Task tempTask = taskDBS.getTask(pk);
		
		if (this.hours < 0) { //Prevent negative hours from being logged
			this.hours = 0;
		}
		
		int temp = tempTask.getTimeTaken() + this.hours;
		tempTask.setTimeTaken(temp);
		taskDBS.updateTask(tempTask);
		
        tasks = taskDBS.listAllTasks(this.start,this.end, this.userAccount);
	}
	
	void onSubmitFromDateForm() {
		
		try {
        tasks = taskDBS.listAllTasks(this.start,this.end, this.userAccount);
		}
		catch (Exception e) {
			
		}
	}

	@OnEvent(component="add1")
	Object onAdd1(int pk) {
		return addHours(pk, 1);
	}
	
	@OnEvent(component="add2")
	Object onAdd2(int pk) {
		return addHours(pk, 2);
	}
	
	@OnEvent(component="add5")
	Object onAdd5(int pk) {
		return addHours(pk, 5);
	}
	
	@OnEvent(component="demo")
	Object demo() {
		System.err.println("Generating tasks");
		for(int i = 0; i < 5; i++)
		{
			CayenneTaskFactory.generateInstance(taskDBS.getCayenneService().newContext(), userAccount); //Probably remove this
		}
		tasks = taskDBS.listAllTasks(this.start,this.end, this.userAccount);
		System.err.println("Amount of tasks now " + tasks.size());
		return Index.class;
	}
	
	Object addHours(int pk, int hours) {
		Task tempTask = taskDBS.getTask(pk);
		tempTask.setTimeTaken(tempTask.getTimeTaken()+hours);
		taskDBS.updateTask(tempTask);
        tasks = taskDBS.listAllTasks(this.start,this.end, this.userAccount);
		return Index.class;
	}
	
	

	@OnEvent(component="complete")
	Object onClickCloseComplete(int pk) {
		Task tempTask = taskDBS.getTask(pk);
		tempTask.setCompleted(true);
		tempTask.setActualEndDate(LocalDateTime.now());
		taskDBS.updateTask(tempTask);
		return Index.class;
	}
	
	@OnEvent(component="drop")
	Object onClickCloseDropped(int pk) {
			Task tempTask = taskDBS.getTask(pk);
			tempTask.setDropped(true);
			tempTask.setActualEndDate(LocalDateTime.now());
			taskDBS.updateTask(tempTask);
		return Index.class;
	}
	
	public String formatDate(LocalDateTime ld) {
		return ld.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
	}
	
	public String formatDateTime(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
	}
}
