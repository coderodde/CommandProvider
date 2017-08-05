package net.coderodde.command.line;

/**
 * This interface defines the API for command line listeners.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 26, 2017)
 */
public interface CommandListener {

    /**
     * Called on any new command.
     * 
     * @param command the command text.
     */
    public void onCommand(String command);
}