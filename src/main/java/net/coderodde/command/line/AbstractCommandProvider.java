package net.coderodde.command.line;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This abstract class defines some commonalities and API for command providers.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 26, 2017)
 */
public abstract class AbstractCommandProvider {

    /**
     * The string used for separating adjacent commands.
     */
    protected String commandSeparator;

    /**
     * List of command listeners.
     */
    private final List<CommandListener> commandListenerList = new ArrayList<>();

    /**
     * Processes a line of text.
     * 
     * @param line the line of text.
     */
    public abstract void processLine(String line);

    /**
     * Returns the current command separator string.
     * 
     * @return the command separator string.
     */
    public String getCommandSeparator() {
        return commandSeparator;
    }

    /**
     * Sets the command separator string.
     * 
     * @param commandSeparator the command separator.
     */
    public void setCommandSeparator(String commandSeparator) {
        this.commandSeparator = checkCommandSeparator(commandSeparator);
    }

    /**
     * Adds a command listener to the current listener list.
     * 
     * @param commandListener the command listener to add.
     */
    public void addCommandListener(CommandListener commandListener) {
        if (commandListener != null) {
            commandListenerList.add(commandListener);
        }
    }

    /**
     * Removes a command listener from the current listener list.
     * 
     * @param commandListener the command listener to remove.
     */
    public void removeCommandListener(CommandListener commandListener) {
        commandListenerList.remove(commandListener);
    }

    /**
     * Broadcasts the command to all command listeners of this provider.
     * 
     * @param command the command to broadcast.
     */
    protected void broadcastCommand(String command) {
        for (CommandListener commandListener : commandListenerList) {
            commandListener.onCommand(command);
        }
    }

    /**
     * Checks that the command separator is not {@code null} and is not empty.
     * 
     * @param commandSeparator the command separator to check.
     * 
     * @return the input commandSeparator.
     * 
     * @throws NullPointerException if the command separator is {@code null}.
     * @throws IllegalArgumentException if the command separator is empty.
     */
    private String checkCommandSeparator(String commandSeparator) {
        Objects.requireNonNull(commandSeparator, 
                               "The input command separator is null.");

        if (commandSeparator.isEmpty()) {
            throw new IllegalArgumentException(
                    "The command separator is empty.");
        }

        return commandSeparator;
    }
}