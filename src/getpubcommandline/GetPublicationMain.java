package getpubcommandline;

import java.io.File;
import java.util.Collection;

import getpubcommandline.plugin.SitePlugin;
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

        Collection<SitePlugin> plugins = new PluginManagerUtil(pluginManager)
                .getPlugins(SitePlugin.class);

        ContextCommand contextCommand = new ContextCommand();
        SiteChooserCommand command = new SiteChooserCommand();
        for (SitePlugin sitePlugin : plugins) {
            command.addSiteCommand(sitePlugin.getCommand());
        }

        command.action(contextCommand);
    }
}
