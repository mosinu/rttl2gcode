/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtttl2gcode;

import com.sun.media.jfxmedia.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author hexman
 */
public class RTTTL2Gcode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        ConsoleLogger logger = new ConsoleLogger();
        
        String inputFilepath = "";
        String outputFilepath = "";
        if(args.length < 1)
        {
            MainUI ui = new MainUI();
            ui.setTitle("RTTTL 2 Gcode Converter");
            ui.setVisible(true);
        }
        else
        {
            for (int i = 0; i < args.length; i++)
            {
                if( args[i].toLowerCase().equals("-input") && (args.length >= (i + 1)))
                {
                    inputFilepath = args[i + 1];
                    File inputFile = new File(inputFilepath);
                    if(inputFile.exists() == false || inputFile.isDirectory() == true)
                    {
                        inputFilepath = "";
                    }
                }
                else if ( args[i].toLowerCase().equals("-output") && (args.length >= (i + 1)))
                {
                    outputFilepath = args[i + 1];
                }
                else if ( args[i].toLowerCase().equals("-help") 
                        || args[i].toLowerCase().equals("-?") 
                        || args[i].toLowerCase().equals("/help") 
                        || args[i].toLowerCase().equals("/?"))
                {
                    logger.Info("Help page:\n\r");
                    logger.Info("-input InputFILENAME");
                    logger.Info("-output OutputFILENAME");
                    logger.Info("This application will take a txt file as input containing RTTTL string formats and convert it into GCODE output file format");
                    logger.Info("The produced output gcode will play music notes on the 3D printer LCD buzzer.");
                    logger.Info("Take the generated gcode file and \"print\" it. It should play music");
                }
            }
            logger.setLogToConsole(true);
            Converter.convertFile(inputFilepath, outputFilepath, logger);
        }
        
    }
    
}
