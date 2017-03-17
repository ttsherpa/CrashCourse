package ucsbCurriculum.Utility;

//import java.util.ArrayList;
import ucsbCurriculum.Utility.Time;

public class Conflict{
	// return true if those two Time have conflicts
	public static boolean have_conflict (Time t1, Time t2) {
 		return t1.startTime > t2.startTime ? (t1.startTime > t2.endTime) : (t2.startTime > t1.startTime);
//	    return t1.startTime > t2.startTime ? (t1.endTime > t2.startTime) : (t2.startTime > t1.startTime);
	    
	}
}