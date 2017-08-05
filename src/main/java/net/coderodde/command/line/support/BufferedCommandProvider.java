package net.coderodde.command.line.support;

import java.util.ArrayList;
import java.util.List;
import net.coderodde.command.line.AbstractCommandProvider;
import net.coderodde.command.line.AbstractCommandProvider;

/**
 * This class implements a command provider that caches the command lines until
 * they form at least one full command. The commands are assumed to be separated
 * via semicolons. The user may set other command separator.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 26, 2017)
 */
public final class BufferedCommandProvider extends AbstractCommandProvider {

    /**
     * The default command separator.
     */
    private static final String DEFAULT_COMMAND_SEPARATOR = ";";

    /**
     * Buffers the lines that do not yet form a single command.
     */
    private final List<String> bufferedLineList = new ArrayList<>();

    /**
     * An auxiliary string buffer for manipulating the lines in the buffer list.
     */
    private final StringBuffer stringBuffer = new StringBuffer();

    /**
     * Constructs a new buffered command provider using the given command 
     * separator.
     * 
     * @param commandSeparator the string used for separating the commands.
     */
    public BufferedCommandProvider(String commandSeparator) {
        setCommandSeparator(commandSeparator);
    }

    /**
     * Constructs a new buffered command provider using the default command
     * separator.
     */
    public BufferedCommandProvider() {
        this(DEFAULT_COMMAND_SEPARATOR);
    }

    /**
     * Processes the line of text.
     * 
     * @param line the line to handle.
     */
    @Override
    public void processLine(String line) {
        bufferedLineList.add(line);
        clearStringBuffer();

        for (String bufferedLine : bufferedLineList) {
            stringBuffer.append(bufferedLine.trim()).append(' ');
        }

        String bufferContent = stringBuffer.toString().trim();
        String[] bufferCommands = bufferContent.split(commandSeparator);
        boolean lastCommandTerminated = 
                bufferContent.endsWith(commandSeparator);

        if (!lastCommandTerminated && bufferCommands.length < 2) {
            // We don't yet have any full command, so it remains stored in the
            // line list.
            return;
        }

        if (lastCommandTerminated) {
            handleTerminated(bufferCommands);
        } else {
            handleNonTerminated(bufferCommands);
        }
    }

    private void clearStringBuffer() {
        stringBuffer.delete(0, stringBuffer.length());
    }

    private void handleTerminated(String[] bufferCommands) {
        for (String bufferedCommand : bufferCommands) {
            String command = bufferedCommand.trim();
            broadcastCommand(command);
        }

        bufferedLineList.clear();
    }

    private void handleNonTerminated(String[] bufferCommands) {
        for (int i = 0; i < bufferCommands.length - 1; ++i) {
            String command = bufferCommands[i].trim();
            broadcastCommand(command);
        }

        bufferedLineList.clear();
        bufferedLineList.add(bufferCommands[bufferCommands.length - 1]);
    }
}