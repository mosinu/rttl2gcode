/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtttl2gcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author hexman
 */
public class Converter {
    public static String readFile(String path, Charset encoding) 
    throws IOException 
    {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }
    
    public  static boolean writeInFile(String outputFile, String dataToWrite, ILog logger)
    {
        try (FileOutputStream fop = new FileOutputStream(outputFile)) 
        {
            File file = new File(outputFile);
            // if file doesn't exists, then create it
            if (!file.exists())
            {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = dataToWrite.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
 
	} 
        catch (IOException e) 
        {
            logger.Error(e.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean convertFile(String inputFile, String outputFile, ILog logger)
    {
        try
        {
            if(inputFile.isEmpty() || outputFile.isEmpty())
            {
                logger.Error("Invalid Input or output file path arguments");
            }
            Settings setting = new Settings(logger);

            String RTTLRawData  = readFile(inputFile,StandardCharsets.UTF_8); //"B5MainTheme:d=4,o=5,b=180:1f#,1a,1b,2g,g,8g,8a,1b,1f#,1b4,1d,1c#,1e,1f#,2d,d,8d,8e,1f#";//"B5MainTheme:d=4,o=5,b=180:2b4,1e,1g,1f#,1a,1b,2g,g,8g,8a,1b,1f#,1b4,1d,1c#,1e,1f#,2d,d,8d,8e,1f#";
            String Data         = RTTLRawData.replace(' ', ',');
            Data = Data.replace("_","#");
            String[] DataArray  = Data.split(":");
            String Output = ";" + DataArray[0] + "\n";


            if(setting.Parse(DataArray[1]) == false)
            {
                logger.Error("Incorrect input data, settings could not be parsed correctly");
                return false;
            }

            RTTLParser parser = new RTTLParser();
            Output += parser.Parse(DataArray[2], setting, logger);
            return writeInFile(outputFile, Output, logger);
        }
        catch(Exception ex)
        {
            logger.Error("Unexpected error: " + ex.getMessage());
            return false;
        }
    }
}
