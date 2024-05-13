package edu.ndsu.cs.estimate.services.tasks;
import java.util.ArrayList;

import java.util.List;

import org.apache.cayenne.ObjectContext;

import edu.ndsu.cs.estimate.cayenne.persistent.Task;
import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TaskInterface {
	
    public Integer 	getPK(); 

    public List<Task> listAllTasks(UserAccount user);
    
    public String 	getName();
	public void	  	setName(String name);

    public LocalDateTime getActualEndDate();
	public void   	 setActualEndDate(LocalDateTime actualEndDate); 

    public boolean 	getCompleted();
	public void   	setCompleted(boolean completed); 

    public boolean 	getDropped();
	public void   	setDropped(boolean dropped);

    public LocalDateTime      getEstEndDate();
	public void   	 setEstEndDate(LocalDateTime estEndDate); 

    public LocalDateTime      getStartDate();
	public void   	 setStartDate(LocalDateTime startDate); 

    public Integer       getTimeTaken();
	public void   	 setTimeTaken(Integer timeTaken);
	
    public Integer       getEstimatedHours();
	public void   	 setEstimatedHours(Integer hours);
	
    public Integer       getSystemEstimate();
	public void   	 setSystemEstimate(Integer hours);

    public LocalDateTime       getTimestampCreated();
	public void   	 setTimestampCreated(LocalDateTime time);

	
    public UserAccount     getUser();
	public void   	setUser(UserAccount user);  
	
	public void setObjectContext(ObjectContext obj);
    public ObjectContext getObjectContext();

	@SuppressWarnings("deprecation")
	public default List<String> 		validate() {
		ArrayList<String> errors = new ArrayList<String>();
		
		if(getName() == null || getName().trim().length() == 0) {
			errors.add("Name must be included.");
		}
		else if(getName().length() > 50) {
			errors.add("Name cannot contain more than 50 characters."); 
		}
		else if(getEstimatedHours() == null) {
			errors.add("Estimated hours must be included.");
		}
		else if(getTimeTaken() == null) {
			errors.add("Time taken (in hours) must be included.");
		}
		else if(getEstEndDate() == null) {
			errors.add("Estimated end date must be included.");
		}
		else if(getStartDate() == null) {
			errors.add("Start date must be included.");
		}
		else if(getEstEndDate().compareTo(getStartDate())<0) {
			errors.add("Estimated end date must be after start date.");
		}
		else if(getTimeTaken() > 999999999999L) {
			errors.add("Time cannot contain more than 12 digits.");
		}
		else if(getStartDate().compareTo(LocalDate.of(1900, 1, 1).atTime(LocalTime.MIDNIGHT))<0 || 
				getEstEndDate().compareTo(LocalDate.of(1900, 1, 1).atTime(LocalTime.MIDNIGHT))<0) {
			errors.add("Dates cannot be before 1/1/1900.");
		}

		return errors; 
	}


	
}
