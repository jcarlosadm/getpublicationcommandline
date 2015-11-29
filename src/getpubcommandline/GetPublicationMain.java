package getpubcommandline;

import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.ui.commands.implementations.SiteChooserCommand;

public class GetPublicationMain {
    public static void main(String[] args) {
        ContextCommand contextCommand = new ContextCommand();
        SiteChooserCommand command = new SiteChooserCommand();
        command.action(contextCommand);
    }
}
