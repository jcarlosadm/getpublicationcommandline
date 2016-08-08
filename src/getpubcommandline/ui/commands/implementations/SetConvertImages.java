package getpubcommandline.ui.commands.implementations;

import getpubcommandline.ui.StringChooser;
import getpubcommandline.ui.commands.Command;
import getpubcommandline.ui.commands.ContextCommand;
import getpublication.json.publication.JsonPublication;

public class SetConvertImages implements Command {

    @Override
    public void action(ContextCommand context) {
        // TODO Auto-generated method stub
        JsonPublication json = context.getJsonPublication();
        if (json == null) {
            return;
        }
        json.load();

        boolean convertImages = json.getConvertImagesProperty();

        System.out.println("current convert images value = " + convertImages);

        StringChooser chooser = new StringChooser();
        chooser.setTitle("Select an value:");
        chooser.addString("true");
        chooser.addString("false");

        String newValue = chooser.run();
        if (newValue.equals("")) {
            return;
        }

        try {
            convertImages = Boolean.parseBoolean(newValue);
        } catch (Exception e) {
            return;
        }

        json.setConvertImagesProperty(convertImages);
        json.save();

        System.out.println("selected value: " + newValue);

    }

    @Override
    public String getCommandName() {
        return "set convert or not convert image";
    }

}
