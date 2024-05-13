package edu.ndsu.cs.estimate.pages.estimation;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.cs.estimate.entities.interfaces.EstimationCategory;
import edu.ndsu.cs.estimate.entities.interfaces.EstimationSuggestion;
import edu.ndsu.cs.estimate.services.database.interfaces.EstimationCategoryDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.EstimationSuggestionDatabaseService;

@RequiresAuthentication
public class SuggestNewEvent {
	@Property
	private Integer categoryPK;
	
	@Inject 
	EstimationSuggestionDatabaseService suggestionDatabaseService;
	
	@Inject 
	EstimationCategoryDatabaseService categoryDatabaseService;
	
	@Property
	@Persist
	EstimationCategory category;
	
	@Inject
	AlertManager alertManager;
	
	@Property
	@Persist
	EstimationSuggestion suggestion;
	
	@InjectComponent
	private Form suggestionForm;
	
	void onActivate(Integer categoryPK) {
		this.categoryPK = categoryPK;
	}
	Integer onPassivate() {
		return categoryPK;
	}
	
	void setupRender() {
		if(categoryPK != null) {
			category = categoryDatabaseService.getCategory(categoryPK);
		}
		suggestion = suggestionDatabaseService.getNewSuggestionFromContext(category.getObjectContext());
	}
	
	Object onValidateFromSuggestionForm() {
		List<String> errors = suggestion.validate();
		for(String error : errors) {
			suggestionForm.recordError(error);
			alertManager.alert(Duration.TRANSIENT, Severity.ERROR, error);
		}
		
		if(errors.isEmpty()) {
			suggestionDatabaseService.updateSuggestion(suggestion);
			category.addToSuggestions(suggestion);
			categoryDatabaseService.updateCategory(category);
			alertManager.alert(Duration.TRANSIENT, Severity.SUCCESS, "Suggestion succesfully saved");
			return Index.class;
		}
		return this;
	}
	
	public String category() {
		return this.category.getName();
	}
	
}
