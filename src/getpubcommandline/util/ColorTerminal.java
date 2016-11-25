package getpubcommandline.util;

public class ColorTerminal {
    
    public String getRed() {
        if (this.isWindows()) {
            return "";
        }
        
        return "\u001B[31m";
    }
    
    public String getBlue() {
        if (this.isWindows()) {
            return "";
        }
        
        return "\u001B[34m";
    }
    
    public String getReset() {
        if (this.isWindows()) {
            return "";
        }
        
        return "\u001B[0m";
    }
    
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
