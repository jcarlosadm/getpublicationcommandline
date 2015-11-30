package getpubcommandline;

import java.io.File;
import java.util.Collection;

import getpubcommandline.plugin.CommandLineSitePlugin;
import getpubcommandline.ui.commands.ContextCommand;
import getpubcommandline.ui.commands.implementations.SiteChooserCommand;
import getpublication.folders.UserFolder;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

public class GetPublicationMain {
    public static void main(String[] args) {
        PluginManager pluginManager = PluginManagerFactory
                .createPluginManager();
        pluginManager.addPluginsFrom(
                new File(UserFolder.getPathToPluginFolder()).toURI());

        Collection<CommandLineSitePlugin> plugins = new PluginManagerUtil(pluginManager)
                .getPlugins(CommandLineSitePlugin.class);

        ContextCommand contextCommand = new ContextCommand();
        SiteChooserCommand command = new SiteChooserCommand();
        for (CommandLineSitePlugin sitePlugin : plugins) {
            command.addSiteCommand(sitePlugin.getCommand());
        }

        command.action(contextCommand);
    }
}
