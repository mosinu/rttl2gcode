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
public interface ILog {
    void Error(String errorString);
    void Warning(String warningString);
    void Info(String InfoString);
}
