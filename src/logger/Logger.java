package logger;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Logger {
    public static File CONFIG_FILE = new File("./logger.cfg");
    public static LogLevel LOG_LEVEL = LogLevel.INFO;
    public static boolean PRINT_TO_STD_OUT = true;
    public static String LOG_FILE_PATH = "./log";

    public static void log(String message, LogLevel level){
        if(level.value > LOG_LEVEL.value)
            return;

        // create log entry message
        String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
        String logEntry = ("[" + timestamp + "]" + "[" + level + "]:" + message + '\n');

        // print to STD out if necessary
        if(PRINT_TO_STD_OUT)
            System.out.print(logEntry);

        // write log message to file
        try {
            FileWriter writer = new FileWriter(LOG_FILE_PATH,true);
            writer.write(logEntry);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean readConfig(){
        try {
            // CREATE PROPERTIES OBJECT
            Properties prop = new Properties();
            prop.load(new FileReader(CONFIG_FILE));

            // READ PROPERTIES
            LOG_LEVEL = LogLevel.valueOf(prop.getProperty("LOG_LEVEL", String.valueOf(LOG_LEVEL)));
            PRINT_TO_STD_OUT = Boolean.parseBoolean(prop.getProperty("PRINT_TO_STD_OUT", String.valueOf(PRINT_TO_STD_OUT)));
            LOG_FILE_PATH = prop.getProperty("LOG_FILE_PATH",LOG_FILE_PATH);

            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
