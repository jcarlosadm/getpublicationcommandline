package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.util.ColorTerminal;
import getpublication.json.publication.JsonPublication;
import getpublication.project.Project;
import getpublication.util.UserInput;

public class StoreLastChapter implements Command {

    private String lastChapter = "";

    public StoreLastChapter(ContextCommand context) {
        this.loadLastChapter(context);
    }

    public boolean loadLastChapter(ContextCommand context) {
        JsonPublication jsonPublication = context.getJsonPublication();
        Project project = context.getProject();
        if (jsonPublication == null || project == null) {
            return false;
        }

        jsonPublication.load();

        lastChapter = jsonPublication.getLastChapter(project.getName());
        lastChapter = (lastChapter == null ? "" : lastChapter);

        return true;
    }

    @Override
    public void action(ContextCommand context) {
        if (!this.loadLastChapter(context))
            return;

        if (lastChapter == null || lastChapter.equals("")) {
            System.out.println("last chapter not stored for the project "
                    + context.getProject().getName());
        } else {
            System.out.println("last chapter: " + lastChapter);
        }

        System.out.println("Change last Chapter? (type \"yes\" or \"no\")");
        if (!UserInput.getInput().toLowerCase().equals("yes")) {
            return;
        }

        System.out.print("type the last chapter: ");
        lastChapter = UserInput.getInput();

        context.getJsonPublication()
                .setLastChapter(context.getProject().getName(), lastChapter);
        context.getJsonPublication().save();
    }

    @Override
    public String getCommandName() {
        ColorTerminal c = new ColorTerminal();

        return "Store Last Chapter" + (this.lastChapter.isEmpty() ? ""
                : " (last: " + c.getBlue() + this.lastChapter + c.getReset()
                        + ")");
    }

}
