package edu.ndsu.cs.estimate.pages.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import edu.ndsu.cs.estimate.cayenne.persistent.Report;
import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.TaskInReport;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.interfaces.ReportDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.TaskHourLogDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;
import edu.ndsu.cs.estimate.services.tasks.CayenneTaskFactory;
import edu.ndsu.cs.estimate.services.tasks.MockTaskFactory;
import edu.ndsu.cs.estimate.services.tasks.TaskDatabaseService;

@RequiresAuthentication
public class Index {

	@SuppressWarnings("deprecation")
	@Property
	@Persist
	private LocalDate start; 
	
	@SuppressWarnings("deprecation")
	@Property 
	@Persist
	private LocalDate end;
	
	@Persist
	@Property
	private int hours;
	
	@Inject
	private ReportDatabaseService reportDBS;
	
	@Inject
	private TaskHourLogDatabaseService taskHourLogDBS;
	
	
	@Persist
	@Property
	private List<Report> reports;
	
	@Persist
	@Property
	private Report report;
	
	@Property
	private TaskInReport taskInReport;
	
	@InjectComponent
	Form dateForm;
	
	@Inject
	private SecurityService securityService;

	@Persist
	@Property
	private int numTotalReports;

	
	@Inject
	private UserAccountDatabaseService userAccountDatabaseService;
	
	@Property
	@Persist
	private UserAccount	userAccount;
	
	void setupRender() {
		if(start == null) {
			start = LocalDate.of(1970,1,1);
		}
		if(end == null) {
			end = LocalDate.of(2038,1,1);
		}
		String principal = securityService.getSubject().getPrincipal().toString(); 
		userAccount = userAccountDatabaseService.getUserAccount(principal);
		reports = reportDBS.getReports(this.start,this.end, (User)this.userAccount);
	}

	@OnEvent(component="generate")
	Object onClickGenerate() {
		reportDBS.generateReport((User)userAccount);
		numTotalReports = reportDBS.getAllReports((User)userAccount).size();
		return Index.class;
	}
	
	public String formatDate(LocalDateTime d) {
		return d.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
	}
	
	public int pk(int pk) {
		return pk-199;
	}
	
}
