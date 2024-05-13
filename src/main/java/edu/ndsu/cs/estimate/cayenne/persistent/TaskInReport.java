package edu.ndsu.cs.estimate.cayenne.persistent;

import edu.ndsu.cs.estimate.cayenne.persistent.auto._TaskInReport;

public class TaskInReport extends _TaskInReport {

    private static final long serialVersionUID = 1L; 
    
    public Integer getPK()
    {
    	if(getObjectId() != null && !getObjectId().isTemporary())
    	{
    		return (Integer) getObjectId().getIdSnapshot().get(PK_PK_COLUMN);
    	}
    	return null; 
    }
	
	public String toString() {
		return getTaskInReport().getName();
	}
	
	public boolean equals(Object o) {
		if(o instanceof TaskInReport) {
			TaskInReport other = (TaskInReport) o;
			return this.getPK().equals(other.getPK());
		}
		return false; 
	}
}
