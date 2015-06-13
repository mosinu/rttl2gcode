/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtttl2gcode;

/**
 *
 * @author hexman
 */
public class Settings {
    int Duration;
    int Octave;
    int Tempo; 

    public int getDuration() {
        return Duration;
    }

    public int getOctave() {
        return Octave;
    }

    public int getTempo() {
        return Tempo;
    }
    
    public int getBeatDuration()
    {
        return (60000 / Tempo);
    }
    
    ILog log;
    public Settings(ILog logger)
    {
        log = logger;
    }
    
    public boolean Parse(String settingsString)
    {
        String[] parameters = settingsString.split(",");
        if( parameters.length < 1)
        {
            return false;
        }
        log.Info("Parsing Settings");
        for(int i = 0; i < parameters.length; i++)
        {
            String[] pair = parameters[i].split("=");
            try
            {
                switch(pair[0].toLowerCase())
                {
                    case "d":
                        Duration = Integer.parseInt(pair[1]);
                        log.Info("Duration: " + Duration);
                        break;
                    case "o":
                        Octave = Integer.parseInt(pair[1]);
                        log.Info("Octave: " + Octave);
                        break;
                    case "b":
                        Tempo = Integer.parseInt(pair[1]);
                        log.Info("Tempo: " + Tempo);
                        break;
                }
            }
            catch(Exception ex)
            {
                log.Error("Error when parsing settings " + settingsString + " : " + ex.getMessage());
                return false;
            }
        }
        return true;
    }
}
