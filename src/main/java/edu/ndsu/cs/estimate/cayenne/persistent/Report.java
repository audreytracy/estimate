package edu.ndsu.cs.estimate.cayenne.persistent;

import edu.ndsu.cs.estimate.cayenne.persistent.auto._Report;

public class Report extends _Report {

    private static final long serialVersionUID = 1L; 
    
    public Integer getPK()
    {
    	if(getObjectId() != null && !getObjectId().isTemporary())
    	{
    		return (Integer) getObjectId().getIdSnapshot().get(REPORT_ID_PK_COLUMN);
    	}
    	return null; 
    }
    
	public String toString() {
		return getDateGenerated().toString();
	}
	
	public boolean equals(Object o) {
		if(o instanceof Report) {
			Report other = (Report) o;
			return this.getPK().equals(other.getPK());
		}
		return false; 
	}
}
