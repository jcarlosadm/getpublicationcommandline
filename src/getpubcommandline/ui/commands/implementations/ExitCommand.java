package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;

public class ExitCommand implements Command{

    @Override
    public void action(ContextCommand context) {
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

}
