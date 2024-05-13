package edu.ndsu.cs.estimate.cayenne.persistent;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.cayenne.exp.Property;

import edu.ndsu.cs.estimate.cayenne.persistent.auto._TaskHourLog;

public class TaskHourLog extends _TaskHourLog {

    private static final long serialVersionUID = 1L; 
  
    public Integer getTaskID()
    {
        return getTask().getPK(); 
    }

    @Override
    public String toString() {
    	return "hours: " + hours + ", date: " + getTimestamp() ; 
    }
    
    public String getPrettyTimestamp() {
    	LocalDateTime ts = getTimestamp();
        String prettyTimestamp = ts.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return prettyTimestamp;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o instanceof TaskHourLog) {
    		TaskHourLog other = (TaskHourLog) o; 
    		return this.getTaskID().equals(other.getTaskID()) && this.getTimestamp().equals(other.getTimestamp()); 
    	}
    	return false; 
    }
}
