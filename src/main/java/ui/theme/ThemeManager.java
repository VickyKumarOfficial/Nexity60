// ThemeManager.java - Manages theme switching and persistence
package ui.theme;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for managing application themes
 * Handles theme switching and persistence to local storage
 */
public class ThemeManager {
    private static ThemeManager instance;
    private Theme currentTheme;
    private final String THEME_CONFIG_FILE = "theme_preference.conf";
    private List<ThemeChangeListener> listeners = new ArrayList<>();
    
    /**
     * Private constructor for singleton pattern
     */
    private ThemeManager() {
        loadThemePreference();
    }
    
    /**
     * Get singleton instance of ThemeManager
     */
    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }
    
    /**
     * Load theme preference from file
     */
    private void loadThemePreference() {
        try (BufferedReader reader = new BufferedReader(new FileReader(THEME_CONFIG_FILE))) {
            String themeName = reader.readLine();
            if (themeName != null && !themeName.isEmpty()) {
                currentTheme = Theme.valueOf(themeName);
            } else {
                currentTheme = Theme.LIGHT;
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read, use default
            currentTheme = Theme.LIGHT;
            System.out.println("Theme preference file not found, using default LIGHT mode");
        }
    }
    
    /**
     * Save theme preference to file
     */
    private void saveThemePreference() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(THEME_CONFIG_FILE))) {
            writer.write(currentTheme.name());
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to save theme preference: " + e.getMessage());
        }
    }
    
    /**
     * Get current active theme
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * Switch to a new theme
     */
    public void switchTheme(Theme newTheme) {
        if (newTheme != currentTheme) {
            currentTheme = newTheme;
            saveThemePreference();
            notifyListeners();
        }
    }
    
    /**
     * Toggle between light and dark modes
     */
    public void toggleTheme() {
        Theme newTheme = currentTheme == Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
        switchTheme(newTheme);
    }
    
    /**
     * Get color palette for current theme
     */
    public ColorPalette.Palette getPalette() {
        return new ColorPalette.Palette(currentTheme);
    }
    
    /**
     * Register a listener for theme changes
     */
    public void addThemeChangeListener(ThemeChangeListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Unregister a listener
     */
    public void removeThemeChangeListener(ThemeChangeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Notify all listeners of theme change
     */
    private void notifyListeners() {
        for (ThemeChangeListener listener : listeners) {
            listener.onThemeChanged(currentTheme);
        }
    }
    
    /**
     * Interface for theme change listeners
     */
    public interface ThemeChangeListener {
        void onThemeChanged(Theme newTheme);
    }
}
