/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtttl2gcode;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author hexman
 */
public class ConsoleLogger implements ILog,AutoCloseable{

    Logger log;
    ConsoleHandler handler;
    boolean LogToConsole = false;

    public boolean isLogToConsole() {
        return LogToConsole;
    }

    public void setLogToConsole(boolean LogToConsole) {
        this.LogToConsole = LogToConsole;
    }
    public ConsoleLogger ()
    {
        log = Logger.getLogger(ConsoleLogger.class.getName());
        log.setLevel(Level.ALL);
        handler = new ConsoleHandler();
        handler.setLevel(Level.SEVERE);
        log.addHandler(handler);
    }
    

        
    @Override
    public void Error(String errorString) {
        if (log.isLoggable(Level.SEVERE)) 
        {
            log.severe(errorString);
        }
    }

    @Override
    public void Warning(String warningString) 
    {
        if(LogToConsole == false)
        {
            return;
        }
        if (log.isLoggable(Level.WARNING)) 
        {
            log.warning(warningString);
        }
    }

    @Override
    public void Info(String InfoString ) 
    {
        if(LogToConsole == false)
        {
            return;
        }
        if (log.isLoggable(Level.INFO)) 
        {
            log.info(InfoString);
        }
    }

    @Override
    public void close() throws Exception {
        log.removeHandler(handler);
        log = null;
        handler.flush();
        handler.close();
        handler = null;
    }
    
}
