/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chingo
 */
public class Debug {

    public final static boolean debugmode = true;
    private static final Logger logger;

    static {
        logger = Logger.getLogger("debug");
    }

    public static void info(String message) {
        if (debugmode) {
            logger.log(Level.INFO, "[CCraft]:{0}", message);
        }
    }

}
