import java.util.Objects;
import java.util.Scanner;
import net.coderodde.command.line.CommandListener;
import net.coderodde.command.line.AbstractCommandProvider;
import net.coderodde.command.line.support.BufferedCommandProvider;

/**
 * This class implements a command reader via standard input.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 26, 2017)
 */
public final class Demo {

    /**
     * The quit command.
     */
    private static final String QUIT_COMMAND = "quit";

    /**
     * The command line prompt.
     */
    private static final String PROMPT = "> ";

    /**
     * The command provider.
     */
    private final AbstractCommandProvider commandProvider;

    /**
     * Indicates whether the user requested the exit from the loop.
     */
    private boolean done = false;

    /**
     * The scanner for reading the lines.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a command line reader with given command provider.
     * 
     * @param commandProvider the command provider.
     */
    public Demo(AbstractCommandProvider commandProvider) {
        this.commandProvider = Objects.requireNonNull(
                               commandProvider,
                               "The input command provider is null."); 
    }

    /**
     * Runs the program.
     */
    public void run() {
        CommandListener commandListener = (String command) -> {
            System.out.println(
                    command + commandProvider.getCommandSeparator());

            if (command.equals(QUIT_COMMAND)) {
                done = true;
            }
        };

        commandProvider.addCommandListener(commandListener);

        while (!done) {
            System.out.print(PROMPT);
            String line = scanner.nextLine();
            commandProvider.processLine(line);
        }

        System.out.println("Bye!");
    }

    @Override
    protected void finalize() {
        scanner.close();
    }

    public static void main(String[] args) {
        Demo demo = new Demo(new BufferedCommandProvider());
        demo.run();
    }
}