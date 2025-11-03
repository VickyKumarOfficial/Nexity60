// Theme.java - Theme enumeration
package ui.theme;

/**
 * Enumeration for available themes in the application
 */
public enum Theme {
    LIGHT("Light Mode", "#f8f9fa"),
    DARK("Dark Mode", "#1a1a1a"),
    AUTO("Auto (System Default)", "");
    
    private final String displayName;
    private final String backgroundColor;
    
    Theme(String displayName, String backgroundColor) {
        this.displayName = displayName;
        this.backgroundColor = backgroundColor;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getBackgroundColor() {
        return backgroundColor;
    }
}