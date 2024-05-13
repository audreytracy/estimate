package edu.ndsu.cs.estimate.cayenne.persistent;

import org.apache.shiro.crypto.hash.Sha512Hash;

import org.apache.shiro.util.SimpleByteSource;

import edu.ndsu.cs.estimate.cayenne.persistent.auto._User;
import edu.ndsu.cs.estimate.entities.interfaces.RoleInterface;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;

public class User extends _User implements UserAccount{

    private static final long serialVersionUID = 1L; 
	
    @Override
    public Integer getPK()
    {
    	if(getObjectId() != null && !getObjectId().isTemporary())
    	{
    		return (Integer) getObjectId().getIdSnapshot().get(USER_ID_PK_COLUMN);
    	}
    	return null; 
    }

    @Override
    public Integer getUserID()
    {
		return getPK();
    }

	@Override
	public void setPassword(String password) {
		if(password != null && !password.trim().isEmpty()) {
			SimpleByteSource salt = new SimpleByteSource(getPasswordSalt());
			setPasswordHash(new Sha512Hash(password, salt).toHex());
		}
	}

	@Override
	public void addRole(RoleInterface role) {
		this.addToRoles((Role)role);
	}

	@Override
	public void deleteRole(RoleInterface role) {
		this.removeFromRoles((Role)role);
	} 

	@Override
	public String toString() {
		return this.getUserID().toString();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof User)) return false;
	    User other = (User) o;
	    return java.util.Objects.equals(getPK(), other.getPK());
	}

}
