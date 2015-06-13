# README #

Install Netbeans 8.02 or higher, 
Install JDK 8 from Java website

### What is this repository for? ###

* This repository is a java port from this application [http://ddrboxman.github.io/RepRapRingtone/](http://ddrboxman.github.io/RepRapRingtone/)
that was written in javascript.
The javascript source code can be found here:
[https://github.com/DDRBoxman/RepRapRingtone](https://github.com/DDRBoxman/RepRapRingtone)

This port allow to use both command line arguments are graphical interface.

The supported command line arguments are:

* -help /help -? /? to display the help page
* -input InputFilename
* -output OutputFilename

The application converts a Ring Tone Transfert Language (RTTTL) into Gcode compatible with 3D printers.
3D printers will play the tone by using the LCD screen Buzzer.

*Requirements:
So make sure your 3D printer has a LCD screen with a buzzer.
The 3D printer firmware will also need to support M300 Gcode (Repetier and Marlin firmware normally support that)

### How do I get set up? ###

* Run the application without command line
* Click on "Add Input Files" button
* Select all the files containing RTTL string format you want to convert
* Click on "Convert!" button and everything should be converted

As an example here is what the RTTL file should look like:
HauntHouse: d=4,o=5,b=108: 2a4, 2e, 2d#, 2b4, 2a4, 2c, 2d, 2a#4, 2e., e, 1f4, 1a4, 1d#, 2e., d, 2c., b4, 1a4, 1p, 2a4, 2e, 2d#, 2b4, 2a4, 2c, 2d, 2a#4, 2e., e, 1f4, 1a4, 1d#, 2e., d, 2c., b4, 1a4

It contains 3 parts separated by ":" caracter.
-First part is the name of the tone
-Second part is the settings used to play the tone, it should contain the 3 parameters d(default duration), o (octave) and b(tempo)
-Third part: all the notes separated by ",".
the detail of the format can be found on the wiki:
[https://en.wikipedia.org/wiki/Ring_Tone_Transfer_Language](https://en.wikipedia.org/wiki/Ring_Tone_Transfer_Language)

### ERRORS ###

It should compile without any need for any libraries.
When running a conversion you might get some messages telling you that there were some kind of issues with the input file.
Most of the time the following cases apply:
-The input file is empty
-The input file format is wrong (often you have 4 ":" caracter instead of 3, so check it out.
-Some of the settings format are wrong, i could see the b setting for tempo be written with "BPM" or some text like "SLOW", just remove the "BPM" text or correct the tempo value and it should run.
-the character "_" might be used instead of "#" which should be handled by the parser already.
-One of the notes syntax is wrong.


### Contribution guidelines ###

* Thanks to DDRBoxman for his javascript application!
* Thanks to Nutz95 for porting it to Java application