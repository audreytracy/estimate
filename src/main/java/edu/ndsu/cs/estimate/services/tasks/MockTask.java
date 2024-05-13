package edu.ndsu.cs.estimate.services.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.cayenne.ObjectContext;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;

public class MockTask implements TaskInterface {


	private Integer	PK; 
	private LocalDateTime actualEndDate;
	private boolean completed;
    private boolean dropped;
    private LocalDateTime estEndDate;
    private LocalDateTime timestampCreated;
    private String name;
    private LocalDateTime startDate;
    private Integer timeTaken;
    private UserAccount user;
    private Integer estimatedHours;
    private Integer systemEstimate;
	
	private static int nextPK = 1; 
	
	private MockTask(int PK, String name, int timeTaken, LocalDateTime startDate, LocalDateTime estEndDate, LocalDateTime actualEndDate, boolean completed, boolean dropped, UserAccount user, Integer estimatedHours) {
		super();
		this.PK 			= PK; 
		this.name 			= name;
		this.timeTaken			= timeTaken;
		this.startDate		= startDate;
		this.estEndDate 			= estEndDate;
		this.actualEndDate 	= actualEndDate;
		this.completed		= completed;
		this.dropped = dropped;
		this.user = user;
		this.estimatedHours = estimatedHours;
	}
	
	public MockTask() {
		this(nextPK++, "Unknown", 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),false,false, new User(), 0);
	}
	
	public MockTask(String name, int timeTaken, LocalDateTime startDate, LocalDateTime estEndDate, LocalDateTime actualEndDate, boolean completed, boolean dropped, UserAccount user, Integer estimatedHours) {
		this(nextPK++, name, timeTaken, startDate, estEndDate, actualEndDate, completed, dropped, user, estimatedHours);
	}
	
	public Integer getPK() {
		return PK;
	} 
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	@Override
	public TaskInterface clone() {
		return new MockTask(PK, name, timeTaken, startDate, estEndDate, actualEndDate, completed, dropped, user, estimatedHours);
	}
	
	@Override
	public String toString() {
		return name; 
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof MockTask) {
			MockTask t = (MockTask)o; 
			return this.PK == t.PK;
		}
		return false; 
	}

	@Override
	public LocalDateTime getActualEndDate() {
		return this.actualEndDate;
	}

	@Override
	public void setActualEndDate(LocalDateTime actualEndDate) {
		this.actualEndDate = actualEndDate;
		
	}

	@Override
	public boolean getCompleted() {
		// TODO Auto-generated method stub
		return this.completed;
	}

	@Override
	public void setCompleted(boolean completed) {
		this.completed = completed;
		
	}

	@Override
	public boolean getDropped() {
		// TODO Auto-generated method stub
		return this.dropped;
	}

	@Override
	public void setDropped(boolean dropped) {
		this.dropped = dropped;
		
	}

	@Override
	public LocalDateTime getEstEndDate() {
		// TODO Auto-generated method stub
		return this.estEndDate;
	}

	@Override
	public void setEstEndDate(LocalDateTime estEndDate) {
		// TODO Auto-generated method stub
		this.estEndDate = estEndDate;
		
	}
	
	

	@Override
	public LocalDateTime getStartDate() {
		// TODO Auto-generated method stub
		return this.startDate;
	}

	@Override
	public void setStartDate(LocalDateTime startDate) {
		// TODO Auto-generated method stub
		this.startDate = startDate;
		
	}

	@Override
	public Integer getTimeTaken() {
		// TODO Auto-generated method stub
		return this.timeTaken;
	}

	@Override
	public void setTimeTaken(Integer timeTaken) {
		// TODO Auto-generated method stub
		this.timeTaken = timeTaken;
	}

	@Override
	public UserAccount getUser() {
		// TODO Auto-generated method stub
		return this.user;
	}

	@Override
	public void setUser(UserAccount user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setObjectContext(ObjectContext obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObjectContext getObjectContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEstimatedHours() {
		return estimatedHours;
	}

	@Override
	public void setEstimatedHours(Integer hours) {
		this.estimatedHours = hours;
	}

	@Override
	public Integer getSystemEstimate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSystemEstimate(Integer hours) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Task> listAllTasks(UserAccount user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getTimestampCreated() {
		return this.timestampCreated;
	}

	@Override
	public void setTimestampCreated(LocalDateTime time) {
		time = LocalDateTime.now();
	    this.timestampCreated = time;
	}

	
}

