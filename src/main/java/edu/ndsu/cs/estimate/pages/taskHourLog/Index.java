package edu.ndsu.cs.estimate.pages.taskHourLog;

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
import edu.ndsu.cs.estimate.cayenne.persistent.TaskHourLog;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.TaskHourLogDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class Index {

	@Persist
	@Property
	private TaskHourLog log;
	
	@Inject
	private TaskHourLogDatabaseService taskHourLogDBS;
	
	@Persist
	@Property
	private List<TaskHourLog> hoursLog;
	
	@Inject
	private TaskDatabaseService taskDatabase;
		
	@Property
	private Task task;
	
	@Property
	private Integer taskPK;
	
	@Inject
	private SecurityService securityService;

	@Inject
	private UserAccountDatabaseService userAccountDatabaseService;
	
	@Property
	@Persist
	private UserAccount	userAccount;
	
	void onActivate(Integer taskPK) {
		this.taskPK = taskPK; 
	}
	
	void setupRender() {
		if(taskPK != null) {
			task = taskDatabase.getTask(taskPK);
		}
		hoursLog = taskHourLogDBS.getTaskHourLogs(task);
	}

}
