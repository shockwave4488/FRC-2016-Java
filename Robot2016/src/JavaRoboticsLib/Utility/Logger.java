package JavaRoboticsLib.Utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
* Static class that can log all information to a text file or SmartDashboard variable
*/
public class Logger   
{
    private static class TimeStampedMessage   
    {
        private double m_timestamp;
        private String m_message;
        public TimeStampedMessage(String message) {
            m_message = message;
            m_timestamp = _offset.get();
        }

        @Override
		public String toString() {
            return "[ " + (int)(m_timestamp) / 60 + ":" + m_timestamp % 60 + "]\t" + m_message + "\n";
        }
    
    }

    private static Timer _offset;
    private static ArrayList<TimeStampedMessage> _messages;
    private static int _level;
    private static boolean _printToConsole;
    private static String m_smartDashboardName;
    
    static {
            setSmartDashboardName(null);
            setPrintToConsole(false);
            setLevel(-1);
            _offset = new Timer();
            _offset.start();
            _messages = new ArrayList<TimeStampedMessage>();
            addMessage("Logger Initialized", 0);
    }

    /**
    * The minimum logger level required to actually log the data - any messages lower leveled than this will be ignored
    */
    public static void setLevel(int value) {
        _level = value;
    }

    /**
    * Print the {@link #Logger.AddMessage} caller, source file, and line number along with the message
    */
    
    /*
     * Not sure if this can work in java
     * 
    private static boolean _showDetails;
    public static boolean getShowDetails() {
        return _showDetails;
    }

    public static void setShowDetails(boolean value) {
        _showDetails = value;
    }
    */

    /**
    * Set to true to print logs to the NetConsole window
    */
    public static void setPrintToConsole(boolean value) {
        _printToConsole = value;
    }

    /**
    * {@link #SmartDashboard} variable name to automatically write to
    */
    public static String getSmartDashboardName() {
        return m_smartDashboardName;
    }

    public static void setSmartDashboardName(String value) {
        m_smartDashboardName = value;
    }

    /**
    * Resets the time value to zero
    */
    public static void resetTimer() {
        addMessage("Timer Reset", _level);
        _offset.reset();
    }

    /**
    * Adds a message at the current time
    */
    public static void addMessage(String message, int messageLevel) {
        if (messageLevel < _level)
            return ;
         
        TimeStampedMessage toAdd = new TimeStampedMessage(message);
        if (_printToConsole)
            System.out.print(toAdd);
         
        _messages.add(toAdd);
        //updateSmartDashboard();
    	
    }

    public static void addMessage(String message){
    	addMessage(message, 0);
    }
    
    /**
    * Returns the contents of the {@link #Logger}
    */
    public static String getLogs() {
            StringBuilder s = new StringBuilder();
            for (TimeStampedMessage t : _messages)
            {
                s.append(t + "\n");
            }
            return s.toString();
    	
    }

    /**
    * Saves the log as a text file
    * 
    *  @param filePath
     * @throws FileNotFoundException 
    */
    public static void save(String filePath) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filePath);
        out.print(getLogs());
        out.close();
    }

    /**
    * Clears all messages
    */
    public static void clear() {
        _messages.clear();
    }

    /**
    * Updates the related 
    *  {@link #SmartDashboard}
    *  variable if applicable
    */
    public static void updateSmartDashboard() {
        if (null != getSmartDashboardName())
            SmartDashboard.putString(getSmartDashboardName(), getLogs());
         
    }

}


