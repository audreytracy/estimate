package edu.ndsu.cs.estimate.pages.tasks;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class AddTask {

	@Inject
	TaskDatabaseService taskDatabase;
	
	
	@Property
	@Persist
	private Task task;
	
	@Inject
	private SecurityService securityService;

	@Inject
	private UserAccountDatabaseService userAccountDatabaseService;
	
	@Property
	@Persist
	private UserAccount	userAccount;
	
	

	
	void setupRender() {
		try {
		task = taskDatabase.getNewTask();
		task.setDropped(false);
		task.setCompleted(false);
		
		
		}
		catch (Exception e) {
			
		}
		String principal = securityService.getSubject().getPrincipal().toString(); 
		userAccount = userAccountDatabaseService.getUserAccountFromContext(task.getObjectContext(),principal);
		task.setUser((User)userAccount);

	}
	
	@Inject
	AlertManager alertManager;

	
	@InjectComponent
	private Form taskForm;


	void onValidateFromTaskForm() {
		
		if (task.getEstEndDate() == null) {
			
			LocalDateTime temp = LocalDateTime.now();
			int newMonth = (temp.getMonthValue()+1)%12;
			int newYear = temp.getYear();
			if (newMonth < temp.getMonthValue()) newYear += 1;
			LocalDateTime t = LocalDate.of(newYear, newMonth, temp.getDayOfMonth()).atStartOfDay();			
			task.setEstEndDate(t);
		}
		
		
		List<String> errors = task.validate();
		for(String error : errors) {
			taskForm.recordError(error);
		}	
		
		
		
		if(!taskForm.getHasErrors()) {
			taskDatabase.updateTask(task);
		}
	}

	
	Object onSuccessFromTaskForm() {
		alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, "Task added successfully.");
		return Index.class; 
	}
	
	
	
	
}

