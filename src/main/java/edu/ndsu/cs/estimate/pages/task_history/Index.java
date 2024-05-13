package edu.ndsu.cs.estimate.pages.task_history;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;
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
	
	@Persist
	@Property
	private List<? extends Task> completedTasks;
	
	@Persist
	@Property
	private List<? extends Task> droppedTasks;
	
	@Property
	private Task task;
	
	@Inject
	private SecurityService securityService;

	@Inject
	private UserAccountDatabaseService userAccountDatabaseService;
	
	@Property
	@Persist
	private UserAccount	userAccount;
	
	@SuppressWarnings("unchecked")
	void setupRender() {
		if(start == null) {
			start = LocalDate.of(1970, 1, 1).atStartOfDay();
		}
		if(end == null) {
			end = LocalDate.of(2038, 1, 1).atStartOfDay();
		}
		String principal = securityService.getSubject().getPrincipal().toString(); 
		userAccount = userAccountDatabaseService.getUserAccount(principal);
		completedTasks = taskDBS.listCompleted(userAccount);
		droppedTasks = taskDBS.listDropped(userAccount);
	}
}
