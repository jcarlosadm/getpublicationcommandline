package getpubcommandline.plugin;

import getpubcommandline.ui.commands.Command;
import net.xeoh.plugins.base.Plugin;

public interface CommandLineSitePlugin extends Plugin {
    public Command getCommand();
}
