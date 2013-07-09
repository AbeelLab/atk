/**
 * %HEADER%
 */
package atk.util;

import java.io.Serializable;
/**
 * 
 * @author Thomas Abeel
 *
 */
public class TimeInterval implements Serializable {

    private static final long serialVersionUID = -5251967760337130846L;

    private long milliseconds, seconds, minutes, hours, days;

    public TimeInterval(String s){
    	String[] d=s.split("d ");
    	days=Long.parseLong(d[0]);
    	String[] h=d[1].split(":");
    	hours=Long.parseLong(h[0]);
    	minutes=Long.parseLong(h[1]);
    	String[]ms=h[2].split("'");
    	seconds=Long.parseLong(ms[0]);
    	milliseconds=Long.parseLong(ms[1]);
    }
    
    public long getLengthInMilliseconds(){
    	return ((((((24*days)+hours)*60)+minutes)*60)+seconds)*1000+milliseconds;
    }
    
    public TimeInterval(long ms) {
        seconds = ms / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        hours = hours % 24;
        minutes = minutes % 60;
        seconds = seconds % 60;
        milliseconds = ms % 1000;
    }

    public String toString() {
        return days + "d " + hours + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":"
                + (seconds < 10 ? "0" + seconds : seconds) + "'" + milliseconds;

    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

}

