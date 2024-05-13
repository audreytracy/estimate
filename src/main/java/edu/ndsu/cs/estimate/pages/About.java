package edu.ndsu.cs.estimate.pages;

import org.apache.tapestry5.annotations.PageActivationContext;

public class About
{
    @PageActivationContext
    private String learn;

    public String getLearn() {
        return learn;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }
}

//This is a comment for a test commit