package getpubcommandline.plugin;

import getpubcommandline.ui.commands.Command;
import net.xeoh.plugins.base.Plugin;

public interface SitePlugin extends Plugin {
    public Command getCommand();
}
