package edu.ndsu.cs.estimate.pages.tasks;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class EditTask{

	@Inject
	private TaskDatabaseService taskDatabase;
	
	@Inject
	private AlertManager alertManager; 
	
	@InjectComponent
	private Form taskForm;
	
	@Property
	@Persist
	private Task task;
	
	@Property
	private Integer taskPK;
	
	void onActivate(Integer taskPK) {
		this.taskPK = taskPK; 
	}
	
	Integer onPassivate() {
		return taskPK; 
	}
	
	void setupRender() {
		if(taskPK != null) {
			task = taskDatabase.getTask(taskPK);
		}
	}
	
	 void onValidateFromTaskForm() {
		 List<String> errors = task.validate();
			for(String error : errors) {
				taskForm.recordError(error);
			}	
			if(!taskForm.getHasErrors()) {
				taskDatabase.updateTask(task);
			}
		
	}
	
	Object onSuccess() {
		alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, "Update Successful");
		return Index.class;
	}
}

