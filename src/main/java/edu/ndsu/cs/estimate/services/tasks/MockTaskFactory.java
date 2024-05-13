package edu.ndsu.cs.estimate.services.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.cs.estimate.cayenne.persistent.User;
import edu.ndsu.cs.estimate.entities.interfaces.UserAccount;
import edu.ndsu.cs.estimate.services.database.implementations.CayenneUserAccountDatabaseService;
import edu.ndsu.cs.estimate.services.database.interfaces.UserAccountDatabaseService;


public class MockTaskFactory {

private static Random rand = new Random();
	
	private static String[] names1 = {"Gadgetron", "Thingamabopper", "WizBang", "Fantabulator"}; 
	private static String[] names2 = {"10", "20", "30", "40", "50"};
	private static String[] names3 = {"00", "20", "50", "55", "75", "00X", "50X", "50Z"}; 
	 @SuppressWarnings("deprecation")
	private static LocalDateTime[] enddates = {LocalDate.of(2023, 11, 5).atStartOfDay(), LocalDate.of(2023, 11, 1).atStartOfDay(), LocalDate.of(2023, 10, 20).atStartOfDay(), LocalDate.of(2023, 9, 7).atStartOfDay(),};
	 @SuppressWarnings("deprecation")
	private static LocalDateTime[] sdates = {LocalDate.of(2023, 5, 7).atStartOfDay(),LocalDate.of(2023, 4, 1).atStartOfDay(),LocalDate.of(2023, 5, 20).atStartOfDay(),LocalDate.of(2023, 1, 7).atStartOfDay(),};
	
	public static MockTask generateInstance(UserAccount newUser) {
		String 	name 		= oneOf(names1) + " " + oneOf(names2) + oneOf(names3); 
		int		timeTaken 	= rand.nextInt(8) + 1;
		int estimatedHours = timeTaken + rand.nextInt(30) + 1;
		LocalDateTime	startDate = oneOf(sdates);
		LocalDateTime	actualEndDate = LocalDateTime.now();
		LocalDateTime	estEndDate = oneOf(enddates);
		boolean dropped = false;
		boolean completed = false;
		UserAccount user = newUser;
		return new MockTask(name, timeTaken, startDate, estEndDate, actualEndDate, dropped, completed, user, estimatedHours); 
	}
	
	private static<T> T oneOf(T[] values) {
		return values[rand.nextInt(values.length)];
	}
	
}
