package ucsbCurriculum.Utility;

//import java.util.ArrayList;
import ucsbCurriculum.Utility.Time;

public class Util {
	
	// return true if those two Time have conflicts
	public static boolean have_conflict (Time t1, Time t2) {
		return t1.startTime > t2.startTime ? (t1.startTime > t2.endTime) : (t2.startTime > t1.startTime);
	}
	
    public static Time converts_to_minute(String daym, String timm){
        int[] t = new int[2];
        int time = 0;
        char a_char;
        char b_char;
        char c_char;
        char d_char;
        int addedmin;
        int minutes;
        int firstmin;
        int more;
        String hi;
        //String hello = "9:30am - 12:33am";
        String newTime = timm.replaceAll("\\s+", "");
        String[] tab = newTime.split("-");
        int timesforday=0;
    //converts day to int
        String daytomin = daym;
        if(daytomin.contains("M"))
            timesforday=0;
        if(daytomin.contains("T"))
            timesforday=(24*60);
        if(daytomin.contains("W"))
            timesforday=(48*60);
        if(daytomin.contains("R"))
            timesforday=(72*60);
        if(daytomin.contains("F"))
            timesforday=(96*60);
        //converts time to int
        for(int i=0; i<tab.length; i++){
        	
            if(tab[i].contains("pm")){
                if(tab[i].length()==6){
                hi = tab[i];
                a_char = hi.charAt(0);
                b_char = hi.charAt(2);
                c_char = hi.charAt(3);
                addedmin = Character.getNumericValue(a_char);
                minutes = Character.getNumericValue(b_char);
                more = Character.getNumericValue(c_char);
                time = (12*60) + (addedmin * 60) + (minutes*10) + (more);
                t[i]=time;
                }
                else if(tab[i].length() == 7){
                hi = tab[i];
                a_char = hi.charAt(0);
                d_char = hi.charAt(1);
                b_char = hi.charAt(3);
                c_char = hi.charAt(4);
                addedmin = Character.getNumericValue(a_char);
                firstmin = Character.getNumericValue(d_char);
                minutes = Character.getNumericValue(b_char);
                more = Character.getNumericValue(c_char);
                    if((addedmin==1)&&(firstmin==2)){
                        time = ((12 * 60) + (minutes*10) + (more));
                        t[i]=time;
                    }
                    else{
                        time = ((12*60) + ((10 + firstmin) * 60) + (minutes*10) + (more));
                        t[i]=time;
                    }
                
                }                
            }
            else if(tab[i].contains("am")){
                if(tab[i].length()==6){
                hi = tab[i];
                a_char = hi.charAt(0);
                b_char = hi.charAt(2);
                c_char = hi.charAt(3);
                addedmin = Character.getNumericValue(a_char);
                minutes = Character.getNumericValue(b_char);
                more = Character.getNumericValue(c_char);
                time =(addedmin * 60) + (minutes*10) + (more);
                t[i]=time;
                }
                else if(tab[i].length() == 7){
                hi = tab[i];
                a_char = hi.charAt(0);
                d_char = hi.charAt(1);
                b_char = hi.charAt(3);
                c_char = hi.charAt(4);
                addedmin = Character.getNumericValue(a_char);
                firstmin = Character.getNumericValue(d_char);
                minutes = Character.getNumericValue(b_char);
                more = Character.getNumericValue(c_char);
                    if((addedmin==1)&&(firstmin==2)){
                        time = 0 +(minutes*10)+more;
                        t[i]=time;
                    }
                    else{
                        time = (((10 + firstmin) * 60) + (minutes*10) + (more));
                        t[i]=time;
                    }
                
                }                
            }
 //return t;           
}
        
        
        Time ti= new Time(0,0);
        ti.day=daym;
        ti.startTime=timesforday+t[0];
        ti.endTime=timesforday+t[1];
        //System.out.println(ti.startTime + " " + ti.endTime);
        
        return ti;
 }
 
public static String convert_to_string(Time t){
	
		int starttime = t.startTime;
		int endtime = t.endTime;
        String[] names = {"M","T","W","R","F"};
        //starttime
        int startday = (starttime / (24*60));
        String strday1 = names[startday];
        
        boolean fpm = false;
        boolean spm = false;
        
        String strtime="";
        //starttime
        int fhour=0;
        int fdays = (starttime/(24*60));
        int ftenminute =0;
        int foneminute = 0;
        if(fdays==0){
            fhour = (starttime/(60));
            ftenminute = ((starttime-(fhour*60))/10);
            foneminute = (starttime-(fdays*24*60)-(fhour*60))%10;
            if(fhour>12){
                fhour=fhour-12;
                fpm = true;
            }
            if(fhour == 12){
            	fpm = true;
            }
        }
        if(fdays >0){
            fhour = (starttime-(24*60*fdays))/(60);
            ftenminute = (starttime-(24*60*fdays)-(fhour*60))/(10);
            foneminute = (starttime-(fdays*24*60)-(fhour*60))%10;
            if(fhour>12){
                fhour=fhour-12;
                fpm = true;
            }
            if(fhour == 12){
            	fpm = true;
            }
            
        }
        //endtime 
        int shour0;
        int stenminute=0;
        int shour=0;
        int soneminute=0;
        int sdays = (endtime/(24*60));
        if(sdays==0){
            shour = (endtime/(60));
            stenminute = ((endtime-(shour*60))/10);
            soneminute = (endtime-(sdays*24*60)-(shour*60))%10;
            if(shour>12){
                shour=shour-12;
                spm = true;
            }
            if(shour == 12){
            	spm = true;
            }
        }
        if(sdays > 0){
            shour = (endtime-(24*60*sdays))/(60);
            stenminute = (endtime-(24*60*sdays)-(shour*60))/(10);
            soneminute = (endtime-(sdays*24*60)-(shour*60))%10;
            if(shour>12){
                shour=shour-12;
                spm = true;
            }
            if(shour == 12){
            	spm = true;
            }
        }
            String ftime;
            String stime;
            if(fpm == true){
            	ftime = "pm";
            }
            else{
            	ftime = "am";
            }
            
            if(spm == true){
            	stime = "pm";
            }
            else{
            	stime = "am";
            }
            
        strtime = strday1 + " " + Integer.toString(fhour)+":"+Integer.toString(ftenminute)+ Integer.toString(foneminute) + ftime + " - " + Integer.toString(shour)+":"+Integer.toString(stenminute)+ Integer.toString(soneminute)+ stime + " ";
        
        return strtime;
        
    }   

    public String ConverttoMilitary(Time j){
        String both = convert_to_string(j);
        both = both.replaceAll("\\s+", "");
        both = both.replaceAll(":", "");
        String[] tab = both.split("-");
        String start = tab[0];
        String end = tab[1];
        if(start.contains("pm"){
            start=start.replaceAll("pm", "");
            int resultst = Integer.parseInt(start);
            int militarystart = resultst + 1200;
        }
        if(end.contains("pm"){
            end=end.replaceAll("pm", "");
            int resultend = Integer.parseInt(end);
            int militaryend = resultend + 1200;
        }
        if(start.contains("am"){
            start=start.replaceAll("am", "");
            int resultst = Integer.parseInt(start);
            int militarystart = resultst;
        }
        if(end.contains("am"){
            end=end.replaceAll("am","");
            int resultend = Integer.parseInt(end);
            int militaryend = resultend;
        }
        String Militaryst = Integer.toString(militarystart);
        if(Militaryst.length()==3){
            Militaryst = Militaryst.substring(0,0) + ":" + Militaryst.substring(1, Militaryst.length());
        }
        if(Militaryst.length()==4){
            Militaryst = Militaryst.substring(0,1) + ":" + Militaryst.substring(2, Militaryst.length());
        }
        
        String Militaryen = Integer.toString(militaryend);
        if(Militaryen.length()==3){
            Militaryen = Militaryen.substring(0,0) + ":" + Militaryen.substring(1, Militaryen.length());
        }
        if(Militaryen.length()==4){
            Militaryen = Militaryen.substring(0,1) + ":" + Militaryen.substring(2, Militaryen.length());
        }
        
        j.startMilitary = Militaryst;
        j.endMilitary = Militaryen;
    }
}