package edu.ndsu.cs.estimate.cayenne.persistent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;

import edu.ndsu.cs.estimate.cayenne.persistent.auto._Task;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.tasks.TaskInterface;

public class Task extends _Task implements TaskInterface{

    private static final long serialVersionUID = 1L; 
    
    public Integer getPK()
    {
    	if(getObjectId() != null && !getObjectId().isTemporary())
    	{
    		return (Integer) getObjectId().getIdSnapshot().get(TASK_ID_PK_COLUMN);
    	}
    	return null; 
    }
    
    @Override
    public String toString() {
    	return name; 
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o instanceof Task) {
    		Task other = (Task) o; 
    		return this.getPK().equals(other.getPK()); 
    	}
    	return false; 
    }
    
//    @Override
//    public Integer getTimeTaken() {
//    	beforePropertyRead("timeTaken");
//        if(this.timeTaken == null) {
//        	return 0;
//        }
//        return this.timeTaken;
//    }
//    
//    @Override
//    public Integer getEstimatedHours() {
//    	beforePropertyRead("estimatedHours");
//        if(this.estimatedHours == null) {
//        	return 0;
//        }
//        return this.estimatedHours;
//    }
//    
    @Override 
    public Integer getSystemEstimate() {
    	  beforePropertyRead("systemEstimate");
          if(this.systemEstimate == null) {
        	  if(this.estimatedHours == null) {
        		  return 0;
        	  }
        	  setSystemEstimate(estimatedHours); // make sure system estimate is set before reading it
          }
          return this.systemEstimate;
    }
    
    @Override
    public void setSystemEstimate(Integer estimatedHours) {
    	if(estimatedHours != null) {
	    	// the method we're overriding has a different meaning for the argument, but here we're saying it's the user's estimate
	    	Report most_recent = ObjectSelect.query(Report.class).where(Report.USER.eq((User)getUser())).orderBy(Report.DATE_GENERATED.desc()).selectFirst(getUser().getObjectContext()); // all reports for user
			double predictionMultiplier = (most_recent == null) ? 1.0 : most_recent.getPredictionMultiplier();
			int systemEstimate = (int)(estimatedHours*predictionMultiplier + 0.5); // round
			beforePropertyWrite("systemEstimate", this.systemEstimate, systemEstimate);
	        this.systemEstimate = systemEstimate;
    	}
    }
    
    @Override
    public void setTimeTaken(Integer timeTaken) {
    	if(timeTaken != null) {
    		if(timeTaken == 0) this.timeTaken = 0;
	    	int increment = (this.timeTaken == null ) ? timeTaken : timeTaken - this.timeTaken;
	    	if(increment > 0) {
		    	try { // add TaskHourLog record every time hours are added to Task (whenever timeTaken is changed)
			        ObjectContext context = getObjectContext(); 
			        TaskHourLog log = new TaskHourLog();
			        log.setTimestamp(LocalDateTime.now());
			        log.setHours(increment);
			        log.setTask(this);
			        context.registerNewObject(log);
		 		}
		 		catch (Exception e) {
		 			e.printStackTrace();
		 		}
		        beforePropertyWrite("timeTaken", this.timeTaken, timeTaken);
		        this.timeTaken = timeTaken;
	        }
    	}
    }
    

	@Override
	public boolean getCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDropped() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUser(UserAccount user) {
		setUser((User)user);
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
		beforePropertyWrite("timestampCreated", this.timestampCreated, time);
	    this.timestampCreated = time;
	}


}
