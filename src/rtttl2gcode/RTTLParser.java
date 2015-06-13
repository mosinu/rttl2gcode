/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtttl2gcode;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hexman
 */
public class RTTLParser {
    
    
    private final String[] notes = {"a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#"};

    public String Parse(String SongString, Settings settings, ILog log)
    {
        //HauntHouse: d=4,o=5,b=108: 2a4, 2e, 2d#, 2b4, 2a4, 2c, 2d, 2a#4, 2e., e, 1f4, 1a4, 1d#, 2e., d, 2c., b4, 1a4, 1p, 2a4, 2e, 2d#, 2b4, 2a4, 2c, 2d, 2a#4, 2e., e, 1f4, 1a4, 1d#, 2e., d, 2c., b4, 1a4
        //The three parts are separated by a colon.
        //Part 1: name of the ringtone (here: "HauntHouse"), a string of characters represents the name of the ringtone
        //Part 2: settings (here: d=4,o=5,b=108), where "d=" is the default duration of a note. In this case, the "4" means that each note with no duration specifier (see below) is by default considered a quarter note. "8" would mean an eighth note, and so on. Accordingly, "o=" is the default octave. There are four octaves in the Nokring/RTTTL format. And "b=" is the tempo, in "beats per minute".
        //Part 3: the notes. Each note is separated by a comma and includes, in sequence: a duration specifier, a standard music note, either a, b, c, d, e, f or g, and an octave specifier. If no duration or octave specifier are present, the default applies.
        //format: Durée|Note|Octave

        
        String GcodeSong = "";
        try
        {
            String regexPattern = "^(\\d*?)([A-Ga-gPp]+#?)(\\d*|\\.*)?$";//"^(\\d*)?|([a-gA-GPp]+#?)|(\\d|\\.*)$";//original ^(\d*?)([A-Ga-gPp]+#?)(\d*|\.*)?$
            Pattern pattern = Pattern.compile(regexPattern);


            int beatDuration = settings.getBeatDuration();
            
            String[] SongData = SongString.split(",");

            for(int i = 0; i < SongData.length; i++)
            {
                double frequency;
                double duration   = settings.getDuration();
                int octave        = settings.getOctave();

                String songNote = SongData[i].replace(".", "");
                songNote = songNote.replace("\r", "");
                songNote = songNote.replace("\n", "");
                String[] regexStringOutput = songNote.split(regexPattern);
                log.Info("Parsing note " + songNote  + " " + i + " / " + SongData.length);
                Matcher matcher = pattern.matcher(songNote);
                //log.Info("Parsing note " + songNote + " found " + matcher.groupCount() + " matching groups");
                List<String> matches = new LinkedList<String>();
                if( matcher.find())
                {
                    for(int j = 1; j < matcher.groupCount()+1; j++)
                    {
                        matches.add(matcher.group(j));
                    }
                }
                if( matches.size() <= 1)
                {
                    break;
                }
                
                String[] match = matches.toArray(new String[matches.size()-1]);//songNote.split(pattern);
                int dot = 0;

                if( SongData[i].indexOf(".") != -1)
                {
                    dot = 1;
                }

                if(match.length > 0 &&  match[0].equals("") == false)
                {
                    duration = getDuration(beatDuration, match[0]);
                }
                else
                {
                    duration = getDuration(beatDuration, String.valueOf(settings.getDuration()));
                }
                if(dot == 1) 
                {
                    duration += duration / 2;
                }

                if (match.length > 2 && match[2].equals("") == false) 
                {
                    octave = Integer.parseInt(match[2]);
                }

                frequency = getFrequency(match[1], octave);
                String GCodeResult = "M300 S" + (int)Math.floor(frequency) + " P" + (int)Math.floor(duration) + "\n";
                GcodeSong += GCodeResult;
                log.Info("Parsing note result: " + GCodeResult);
            }
        }
        catch(Exception ex)
        {
            log.Error("Unexpected error when parsin notes: " + ex.getMessage());
            return "";
        }
        
        return GcodeSong;
    }

    private double getDuration(int beatDuration, String durationData) 
    {
        if (durationData == ".") 
        {
		return ((float)beatDuration * 1.5); 
	}
	switch (Integer.parseInt(durationData)) 
        {
		case 1:
			return (float)beatDuration * 4.0;
		case 2:
			return (float)beatDuration * 2.0;
		case 4:
			return (float)beatDuration;
		case 8:
			return (float)beatDuration / 2;
		case 16:
			return (float)beatDuration / 4;
		case 32:
			return (float)beatDuration / 8;
	}
        return 0;
    }

    private double getFrequency(String note, int octave) 
    {
        if (note.equals("p") || note.equals("P"))
        {
		return 0;
        }

	int halfsteps = 0;
	halfsteps += (octave - 4) * 12;

	for (int i=0; i < notes.length; i++) 
        {
            if (note.toLowerCase().equals(notes[i]))
            {
                    halfsteps += i;
                    break;
            }	
	}

	return ((double)440 * Math.pow(Math.pow((double)2.0, (1.0/12.0)),  (double)halfsteps));
    }
}
