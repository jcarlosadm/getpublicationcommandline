package getpubcommandline.ui;

import java.util.ArrayList;
import java.util.List;

import getpublication.util.UserInput;

public class StringChooser {
    
    private List<String> stringList = new ArrayList<>();
    
    private String title = "";
    
    public void addString(String string){
        this.stringList.add(string);
    }
    
    public void setStringList(List<String> stringList){
        this.stringList = stringList;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String run() {
        String selectedString = "";
        boolean exit = false;
        
        while (!exit) {
            if (!this.title.equals("")) {
                System.out.println(this.title);
            }
            
            for (int index = 0; index < this.stringList.size(); index++) {
                System.out.println(index+" - "+this.stringList.get(index));
            }
            System.out.println(this.stringList.size()+" - exit");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(UserInput.getInput());
            } catch (Exception e) {
                choice = -1;
            }
            
            if (choice >= 0 && choice < this.stringList.size()) {
                System.out.println("\n"+this.stringList.get(choice)
                        +" selected");
                selectedString = this.stringList.get(choice);
                exit = true;
            } else if (choice == this.stringList.size()){
                selectedString = "";
                System.out.println("\nexit selected");
                exit = true;
            } else {
                System.out.println("error choice. try again\n");
            }
        }
        
        return selectedString;
    }
    
}
