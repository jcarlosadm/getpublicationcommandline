package getpubcommandline.util.pageProgress;

import getpublication.util.pageProgress.PrintStrategy;

public class PrintStrategyCommandLine extends PrintStrategy {

    private static PrintStrategy instance = null;
    
    private PrintStrategyCommandLine() {
    }
    
    public static PrintStrategy getInstance() {
        if (instance == null) {
            instance = new PrintStrategyCommandLine();
        }
        
        return instance;
    }
    
    @Override
    public void execute(int currentPages, int totalPages, int length, String message) {
        int filledCells = (int) (length
                * (((double) currentPages) / ((double) totalPages)));

        System.out.print("\r" + message + " [");
        for (int index = 0; index < filledCells; ++index) {
            System.out.print("=");
        }
        for (int index = 0; index < (length - filledCells); ++index) {
            System.out.print("_");
        }
        System.out.print("] pages = " + totalPages);
    }

}
