package edu.ndsu.cs.estimate.pages;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

@RequiresAuthentication
public class Login
{
    Object onActivate() {
    	return Index.class;
    }
}
