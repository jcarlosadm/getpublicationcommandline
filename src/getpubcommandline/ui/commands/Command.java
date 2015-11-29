package getpubcommandline.ui.commands;

public interface Command {
    public void action(ContextCommand context);
    public String getCommandName();
}
