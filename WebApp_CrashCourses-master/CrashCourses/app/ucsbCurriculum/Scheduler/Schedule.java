package ucsbCurriculum.Scheduler;

import java.util.ArrayList;


import ucsbCurriculum.Utility.*;


public class Schedule {
	 
    public ArrayList<Course> courses; // ArrayList of lectures
    public ArrayList<ArrayList<Time>> sections;  // ArrayList of (ArrayList of sections)
    
    private ArrayList<Course> backupCourses;
    private ArrayList<ArrayList<Time>> backupSections;
    
    
    //schedule restraints
    public int onlySectionsAfter = 0;
    public int onlySectionsBefore = 0;
    
    public ArrayList<Calendar> calendar = new ArrayList<Calendar>();
    
    public void createCalendar(){
        for(int i = 0; i<courses.size(); i++){
            ArrayList<Time> lecture = new ArrayList<Time>();
            lecture = courses.get(i).lectureTimes;
            for(int j = 0; i<lecture.size(); j++){
                Calendar thisCourse = new Calendar();
                thisCourse.id = courses.get(i).id;
                thisCourse.startTime = lecture.get(i).startMilitary;
                thisCourse.endTime = lecture.get(i).endMilitary;
                thisCourse.day = lecture.get(i).day;
                calendar.add(thisCourse);
            }
        }
        for(int k = 0; k<sections.size(); k++){
            ArrayList<Time> currentSections = new ArrayList<Time>();
            currentSections = sections.get(k);
            for(int l = 0; l<1; l++){
                Calendar thisSection = new Calendar();
                thisSection.id = "Section";
                thisSection.startTime = currentSections.get(l).startMilitary;
                thisSection.endTime = currentSections.get(l).endMilitary;
                thisSection.day = currentSections.get(l).day;
                calendar.add(thisSection);
            }
        }
    }
    
    public Schedule(){
        courses = new ArrayList<Course>();
        sections = new ArrayList<ArrayList<Time>>();
        backupCourses = new ArrayList<Course>();
        backupSections = new ArrayList<ArrayList<Time>>();
    }

    public static boolean compare(ArrayList<Time> time1, ArrayList<Time> time2) {
      
      if(time1.size() == 0 || time2.size() == 0)
          return true;

      for (int i = 0; i < time1.size(); i++) {
        for (int j = 0; j < time2.size(); j++) {
          if (!Util.have_conflict(time1.get(i), time2.get(j))) {
            // TO-DO: delete that element in the second list
            time2.remove(j);
          }
        }
      }
      if (time2.size()==0)
        return false;
      else
        return true;
	}
    
    public void clearSchedule(){
    	while(courses.size() != 0){
    		delete(courses.get(0));
    	}
    }
    
    // if c result in conflicts, then just print a message and return
    public void add(Course c) {
      if(c == null)
        System.out.println("This is not a valid course.");
  
      ArrayList<Time> sectionTime = c.get_sectionTimes();
      for (int i = 0; i < courses.size(); i++) {
        Course temp = courses.get(i);
        if (temp.equals(c)) {	
          // Only for command line version!!!
          //System.out.println("Course already added!!!");
          return;
        }
        //if (!Course.compare(temp, c) || !compare(sections.get(i), sectionTime) || !compare(temp.get_lectureTimes(), sectionTime)) {
          // Only for command line version!!!
          //System.out.println("Conflicts detected! This course cannot be add to schedule!");
          //return;
        //}
      }

      courses.add(c);
      sections.add(sectionTime);
      backupCourses.add(c);
      backupSections.add(sectionTime);
    }
    
    public void setOnlySectionsBefore(int restraint){
    	onlySectionsBefore = restraint;
    }
    
    public void setOnlySectionsAfter(int restraint){
    	onlySectionsAfter = restraint;
    }
    
    public void setOnlySectionsBefore(String restraint){
        int answer = 0;
        if(restraint.length() == 2){
            char tens = restraint.charAt(0);
            char ones = restraint.charAt(1);
            answer = (10*Character.getNumericValue(tens)) + (Character.getNumericValue(ones));
        }
        if(restraint.length() == 1){
            char ones = restraint.charAt(0);
            answer = Character.getNumericValue(ones);
        }
    	onlySectionsBefore = answer;
    }
    
    public void setOnlySectionsAfter(String restraint){
        int answer = 0;
        if(restraint.length() == 2){
            char tens = restraint.charAt(0);
            char ones = restraint.charAt(1);
            answer = (10*Character.getNumericValue(tens)) + (Character.getNumericValue(ones));
        }
        if(restraint.length() == 1){
            char ones = restraint.charAt(0);
            answer = Character.getNumericValue(ones);
        }
    	onlySectionsAfter = answer;
    }
    
    
    
    @Override
    public String toString() {
		String res = "Current Schedule:\n\n";
    		
	    	for(int i = 0; i < courses.size(); i++)
	    	{
	    		Course c = courses.get(i);
	    		res += c.get_id() + c.get_name() + "\t";
	    		ArrayList<Time> lectureTimes = c.get_lectureTimes();
	    		for(int j = 0; j < lectureTimes.size(); j++)
	    		{
	    			res += Util.convert_to_string(lectureTimes.get(j)) + "\t";
	    		}
	            res += "\n\t\t\t" + "Section Time" + "\t";
	            ArrayList<Time> sectionTimes = sections.get(i);
	            if(sectionTimes.size() != 0)
	                res += Util.convert_to_string(sectionTimes.get(0)) + "\n";
	            else
	                res += "\n";
	    	}
	    	return res;
    }
    
  

    //removes course c's information from lecture times and section times array lists
    public void delete(Course c) {
	    	int i = courses.indexOf(c);
	    	courses.remove(i);
	    	sections.remove(i);
	    	//this is the only place where an element of backupCourses or backupSections should be modified in any way
	    	backupCourses.remove(i);
	    	backupSections.remove(i);
	    	System.out.println(c.get_id() + " is being deleted.");
	    	//user should be given some kind of warning - course being deleted because of time conflict/or personal choice
    }
    
    //rearrange course list to be in order of increasing section list size
    //this ensures that courses with less sections are considered first, to maximize possible schedules
    public void sortOrderBySectionSize(){
    	
    	ArrayList<ArrayList<Time>> newSection = new ArrayList<ArrayList<Time>>();
    	ArrayList<Course> newCourses = new ArrayList<Course>();
    	
    	while(sections.size() != 0){
    		int pos = 0;
    		int posSize = 0;
    		for(int i = 0; i<sections.size(); i++){
    			if( ((sections.get(i)).size()) > posSize ){
    				posSize = (sections.get(i)).size();
    				pos = i;
    			}
    		}
    		Course c = courses.get(pos);
    		newCourses.add(c);
    		newSection.add(c.get_sectionTimes());
    		courses.remove(pos);
    		sections.remove(pos);
    	}
    	
    	for(int i = newCourses.size()-1; i >= 0; i--){
    		Course e = newCourses.get(i);
    		add(e);
    	}
    	
    	
    }
    
    
    //goes through array list of sections and deletes all section times that have conflicts with other times
    //courses are now rearranged to prioritize classes with less sections, to maximize available sections and classes
    //if a course has zero available section times left after comparison, it will be deleted from the list
    public void deleteConflicts() {
        //reset schedule with backup courses and sections, so that with every course added schedule is recalculated
        /*courses.clear();
    	for(int i = 0; i<backupCourses.size();i++){
    		courses.add(backupCourses.get(i));
    	}
    	sections.clear();
    	for(int i = 0; i<backupSections.size(); i++){
    		sections.add(backupSections.get(i));
    	}*/
    	//first rearrange courses by order of increasing section list size
    	sortOrderBySectionSize();
    	//check all sections against conflicting lecture times
    	for (int i = 0; i < sections.size(); i++) {
    		boolean sectionToLectureConflict = true;
    		for (int j = 0; j < courses.size(); j++) {
    			sectionToLectureConflict = compare((courses.get(j)).get_lectureTimes(), sections.get(i));
    			if (sectionToLectureConflict == false) {
    				//false will be returned if all section times for a lecture have been removed due to conflict
    				delete(courses.get(i));
    			}
    		}
    	}
    	
    	//now check all sections against previous sections
    	//start at 1 because course at position 0 has priority
    	for (int i = 1; i < sections.size(); i++) {
    		ArrayList<Time> currentSection = sections.get(i);
    		//now compare current section list with all previous section lists
    		boolean sectionToSectionConflict = true;
    		for (int j = i-1; j >= 0; j--) {
				sectionToSectionConflict = (compare(sections.get(j), currentSection));
				if (sectionToSectionConflict == false) {
					//no available section times due to conflict; remove course c
					delete(courses.get(i));
				}
			}
		}
	} 
    
    //takes parameters given from setOnlySectionsAfter/setOnlySectionsBefore
    //deletes any section that does not fit within user given constraints.
    //At least one section will remain in the section list, even if it does not fit within the restraints. 
    //this method should ONLY be called after delete conflicts is called. It is optionally called, and only if the user inputs restraints.
    public void deleteRestraintConflicts(){
    	for (int i = 0; i<sections.size(); i++){
    		ArrayList<Time> currentSection = sections.get(i);
    		for (int j = 0; j<currentSection.size(); j++){
    			int startH = currentSection.get(j).startHour;
    			if(onlySectionsBefore != 0){
    				if(!(startH < onlySectionsBefore)){
    					if(currentSection.size() > 1){
    						currentSection.remove(j);
    					}
    				}
    			}
    			if(onlySectionsAfter != 0){
    				if(!(startH >= onlySectionsAfter)){
    					if(currentSection.size() > 1){
    						currentSection.remove(j);
    					}
    				}
    			}
    			
    		}
    	}
    }
    
    
}