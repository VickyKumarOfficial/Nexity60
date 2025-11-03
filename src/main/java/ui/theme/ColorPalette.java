// ColorPalette.java - Color palette definitions for light and dark modes
package ui.theme;

import ui.theme.Theme;

/**
 * Color palette system supporting light and dark themes
 * All colors are defined as constants for easy maintenance
 */
public class ColorPalette {
    
    /**
     * Light Mode Color Palette
     */
    public static class Light {
        // Primary Colors - Vibrant blues
        public static final String PRIMARY = "#2196F3";
        public static final String PRIMARY_DARK = "#1976D2";
        public static final String PRIMARY_LIGHT = "#64B5F6";
        
        // Secondary Colors - Fresh greens
        public static final String SECONDARY = "#4CAF50";
        public static final String SECONDARY_DARK = "#388E3C";
        
        // Accent Colors - Dynamic reds
        public static final String ACCENT = "#FF6B6B";
        public static final String ACCENT_DARK = "#E53935";
        
        // Background & Surface
        public static final String BACKGROUND = "#fafafa";
        public static final String SURFACE = "#ffffff";
        
        // Text Colors
        public static final String TEXT_PRIMARY = "#212121";
        public static final String TEXT_SECONDARY = "#666666";
        public static final String TEXT_TERTIARY = "#999999";
        
        // Border & Divider
        public static final String BORDER = "#e0e0e0";
        public static final String DIVIDER = "#d0d0d0";
        
        // Status Colors
        public static final String SUCCESS = "#4CAF50";
        public static final String WARNING = "#FFA726";
        public static final String ERROR = "#FF6B6B";
        public static final String INFO = "#2196F3";
        
        // Component-Specific
        public static final String CARD_BACKGROUND = "#ffffff";
        public static final String CARD_SHADOW = "rgba(0, 0, 0, 0.08)";
        public static final String BUTTON_HOVER = "#1976D2";
        public static final String INPUT_FOCUS = "#2196F3";
        public static final String BADGE_TECH = "#7C4DFF";
        public static final String BADGE_BUSINESS = "#2196F3";
        public static final String BADGE_SPORTS = "#FF7043";
        public static final String BADGE_HEALTH = "#66BB6A";
        public static final String BADGE_ENTERTAINMENT = "#FFA726";
        public static final String BADGE_POLITICS = "#BA68C8";
        public static final String BADGE_SCIENCE = "#4DB6AC";
        public static final String BADGE_TRENDING = "#FF6B6B";
    }
    
    /**
     * Dark Mode Color Palette - Enhanced for Better Compatibility & Aesthetics
     */
    public static class Dark {
        // Primary Colors - Bright blues for better visibility
        public static final String PRIMARY = "#4a90e2";
        public static final String PRIMARY_DARK = "#357abd";
        public static final String PRIMARY_LIGHT = "#6db3f2";
        
        // Secondary Colors - Vibrant greens
        public static final String SECONDARY = "#42c574";
        public static final String SECONDARY_DARK = "#3aa860";
        
        // Accent Colors - Enhanced reds
        public static final String ACCENT = "#ff6b6b";
        public static final String ACCENT_DARK = "#ff5252";
        
        // Background & Surface - Better contrast
        public static final String BACKGROUND = "#121212";
        public static final String SURFACE = "#1e1e1e";
        
        // Text Colors - High contrast for readability
        public static final String TEXT_PRIMARY = "#f5f5f5";
        public static final String TEXT_SECONDARY = "#b0b0b0";
        public static final String TEXT_TERTIARY = "#808080";
        
        // Border & Divider - More visible
        public static final String BORDER = "#404040";
        public static final String DIVIDER = "#535353";
        
        // Status Colors - Vibrant for better visibility
        public static final String SUCCESS = "#4caf50";
        public static final String WARNING = "#ffa726";
        public static final String ERROR = "#ff5252";
        public static final String INFO = "#4a90e2";
        
        // Component-Specific
        public static final String CARD_BACKGROUND = "#1e1e1e";
        public static final String CARD_SHADOW = "rgba(0, 0, 0, 0.5)";
        public static final String BUTTON_HOVER = "#6db3f2";
        public static final String INPUT_FOCUS = "#4a90e2";
        public static final String BADGE_TECH = "#ba68c8";
        public static final String BADGE_BUSINESS = "#64b5f6";
        public static final String BADGE_SPORTS = "#ff7043";
        public static final String BADGE_HEALTH = "#66bb6a";
        public static final String BADGE_ENTERTAINMENT = "#ffb74d";
        public static final String BADGE_POLITICS = "#ce93d8";
        public static final String BADGE_SCIENCE = "#4db6ac";
        public static final String BADGE_TRENDING = "#ff6b6b";
    }
    
    /**
     * Get color palette for a specific theme
     */
    public static class Palette {
        private Theme theme;
        
        public Palette(Theme theme) {
            this.theme = theme;
        }
        
        public String getPrimary() {
            return theme == Theme.DARK ? Dark.PRIMARY : Light.PRIMARY;
        }
        
        public String getSecondary() {
            return theme == Theme.DARK ? Dark.SECONDARY : Light.SECONDARY;
        }
        
        public String getBackground() {
            return theme == Theme.DARK ? Dark.BACKGROUND : Light.BACKGROUND;
        }
        
        public String getSurface() {
            return theme == Theme.DARK ? Dark.SURFACE : Light.SURFACE;
        }
        
        public String getTextPrimary() {
            return theme == Theme.DARK ? Dark.TEXT_PRIMARY : Light.TEXT_PRIMARY;
        }
        
        public String getTextSecondary() {
            return theme == Theme.DARK ? Dark.TEXT_SECONDARY : Light.TEXT_SECONDARY;
        }
        
        public String getAccent() {
            return theme == Theme.DARK ? Dark.ACCENT : Light.ACCENT;
        }
        
        public String getBorder() {
            return theme == Theme.DARK ? Dark.BORDER : Light.BORDER;
        }
        
        public String getCardBackground() {
            return theme == Theme.DARK ? Dark.CARD_BACKGROUND : Light.CARD_BACKGROUND;
        }
        
        public String getSuccess() {
            return theme == Theme.DARK ? Dark.SUCCESS : Light.SUCCESS;
        }
        
        public String getWarning() {
            return theme == Theme.DARK ? Dark.WARNING : Light.WARNING;
        }
        
        public String getError() {
            return theme == Theme.DARK ? Dark.ERROR : Light.ERROR;
        }
    }
}
