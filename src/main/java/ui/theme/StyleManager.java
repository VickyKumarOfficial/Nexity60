// StyleManager.java - Generates CSS stylesheets based on theme
package ui.theme;

/**
 * Manages CSS stylesheet generation for different themes
 * Generates complete JavaFX CSS styles dynamically
 */
public class StyleManager {
    
    /**
     * Generate CSS stylesheet for light mode
     */
    public static String getLightModeCSS() {
        return getStylesheet(new ColorPalette.Palette(Theme.LIGHT));
    }
    
    /**
     * Generate CSS stylesheet for dark mode
     */
    public static String getDarkModeCSS() {
        return getStylesheet(new ColorPalette.Palette(Theme.DARK));
    }
    
    /**
     * Generate CSS stylesheet based on palette
     */
    private static String getStylesheet(ColorPalette.Palette palette) {
        boolean isDark = palette.getBackground().equals(ColorPalette.Dark.BACKGROUND);
        return getStylesheet(palette, isDark);
    }
    
    /**
     * Generate CSS stylesheet based on palette
     */
    private static String getStylesheet(ColorPalette.Palette palette, boolean isDark) {
        return "/* Nexity60 Dynamic Stylesheet */\n" +
            
            ".root {\n" +
            "    -fx-font-family: 'Segoe UI', 'Arial', sans-serif;\n" +
            "    -fx-base: " + palette.getSurface() + ";\n" +
            "    -fx-background: " + palette.getBackground() + ";\n" +
            "    -fx-control-inner-background: " + palette.getSurface() + ";\n" +
            "    -fx-text-base-color: " + palette.getTextPrimary() + ";\n" +
            "}\n" +
            
            /* ========== CONTAINERS ========== */
            ".root-container {\n" +
            "    -fx-background-color: " + palette.getBackground() + ";\n" +
            "}\n" +
            
            ".sidebar {\n" +
            "    -fx-background-color: " + adjustBrightness(palette.getSurface(), -10) + ";\n" +
            "    -fx-border-color: " + palette.getBorder() + ";\n" +
            "}\n" +
            
            ".content-area {\n" +
            "    -fx-background-color: " + palette.getBackground() + ";\n" +
            "}\n" +
            
            /* ========== LABELS ========== */
            ".label {\n" +
            "    -fx-text-fill: " + palette.getTextPrimary() + ";\n" +
            "    -fx-font-size: 13px;\n" +
            "}\n" +
            
            ".title-label {\n" +
            "    -fx-text-fill: " + palette.getTextPrimary() + ";\n" +
            "    -fx-font-size: 24px;\n" +
            "    -fx-font-weight: bold;\n" +
            "}\n" +
            
            ".subtitle-label {\n" +
            "    -fx-text-fill: " + palette.getTextSecondary() + ";\n" +
            "    -fx-font-size: 14px;\n" +
            "}\n" +
            
            /* ========== BUTTONS ========== */
            ".button {\n" +
            "    -fx-background-color: " + palette.getPrimary() + ";\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-padding: 10px 20px;\n" +
            "    -fx-background-radius: 4px;\n" +
            "    -fx-cursor: hand;\n" +
            "    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 2);\n" +
            "}\n" +
            
            ".button:hover {\n" +
            "    -fx-background-color: " + adjustBrightness(palette.getPrimary(), -10) + ";\n" +
            "    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0, 0, 3);\n" +
            "}\n" +
            
            ".button:pressed {\n" +
            "    -fx-scale-x: 0.98;\n" +
            "    -fx-scale-y: 0.98;\n" +
            "}\n" +
            
            ".secondary-button {\n" +
            "    -fx-background-color: " + palette.getSecondary() + ";\n" +
            "}\n" +
            
            ".secondary-button:hover {\n" +
            "    -fx-background-color: " + adjustBrightness(palette.getSecondary(), -10) + ";\n" +
            "}\n" +
            
            /* ========== TOGGLE BUTTON (Theme Toggle) ========== */
            ".toggle-button {\n" +
            "    -fx-background-color: " + palette.getPrimary() + ";\n" +
            "    -fx-text-fill: white;\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-padding: 8px 16px;\n" +
            "    -fx-background-radius: 20px;\n" +
            "}\n" +
            
            ".toggle-button:selected {\n" +
            "    -fx-background-color: " + palette.getSecondary() + ";\n" +
            "}\n" +
            
            /* ========== TEXT AREAS ========== */
            ".text-area {\n" +
            "    -fx-control-inner-background: " + palette.getSurface() + ";\n" +
            "    -fx-text-fill: " + palette.getTextPrimary() + ";\n" +
            "    -fx-font-size: 13px;\n" +
            "    -fx-border-color: " + palette.getBorder() + ";\n" +
            "    -fx-border-radius: 4px;\n" +
            "}\n" +
            
            ".text-field {\n" +
            "    -fx-control-inner-background: " + palette.getSurface() + ";\n" +
            "    -fx-text-fill: " + palette.getTextPrimary() + ";\n" +
            "    -fx-border-color: " + palette.getBorder() + ";\n" +
            "    -fx-border-radius: 4px;\n" +
            "    -fx-padding: 8px;\n" +
            "}\n" +
            
            ".text-field:focused {\n" +
            "    -fx-border-color: " + palette.getPrimary() + ";\n" +
            "    -fx-border-width: 2;\n" +
            "}\n" +
            
            /* ========== COMBO BOX ========== */
            ".combo-box {\n" +
            "    -fx-background-color: " + palette.getSurface() + ";\n" +
            "    -fx-border-color: " + palette.getBorder() + ";\n" +
            "    -fx-border-radius: 4px;\n" +
            "}\n" +
            
            ".combo-box-popup .list-view {\n" +
            "    -fx-background-color: " + palette.getSurface() + ";\n" +
            "}\n" +
            
            /* ========== CARDS ========== */
            ".card {\n" +
            "    -fx-background-color: " + palette.getCardBackground() + ";\n" +
            "    -fx-border-color: " + palette.getBorder() + ";\n" +
            "    -fx-border-radius: 8px;\n" +
            "    -fx-padding: 16px;\n" +
            "    -fx-effect: dropshadow(gaussian, " + palette.getBorder() + ", 5, 0, 0, 2);\n" +
            "}\n" +
            
            ".card:hover {\n" +
            "    -fx-effect: dropshadow(gaussian, " + palette.getPrimary() + ", 8, 0, 0, 4);\n" +
            "}\n" +
            
            ".article-card-title {\n" +
            "    -fx-text-fill: " + palette.getTextPrimary() + ";\n" +
            "    -fx-font-size: 16px;\n" +
            "    -fx-font-weight: bold;\n" +
            "}\n" +
            
            ".article-card-content {\n" +
            "    -fx-text-fill: " + palette.getTextSecondary() + ";\n" +
            "    -fx-font-size: 13px;\n" +
            "    -fx-line-spacing: 2px;\n" +
            "}\n" +
            
            /* ========== BADGES ========== */
            ".badge {\n" +
            "    -fx-padding: 4px 12px;\n" +
            "    -fx-border-radius: 12px;\n" +
            "    -fx-font-size: 11px;\n" +
            "    -fx-font-weight: bold;\n" +
            "    -fx-text-fill: white;\n" +
            "}\n" +
            
            ".badge-tech {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_TECH : ColorPalette.Light.BADGE_TECH) + ";\n" +
            "}\n" +
            
            ".badge-business {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_BUSINESS : ColorPalette.Light.BADGE_BUSINESS) + ";\n" +
            "}\n" +
            
            ".badge-sports {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_SPORTS : ColorPalette.Light.BADGE_SPORTS) + ";\n" +
            "}\n" +
            
            ".badge-health {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_HEALTH : ColorPalette.Light.BADGE_HEALTH) + ";\n" +
            "}\n" +
            
            ".badge-entertainment {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_ENTERTAINMENT : ColorPalette.Light.BADGE_ENTERTAINMENT) + ";\n" +
            "}\n" +
            
            ".badge-politics {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_POLITICS : ColorPalette.Light.BADGE_POLITICS) + ";\n" +
            "}\n" +
            
            ".badge-science {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_SCIENCE : ColorPalette.Light.BADGE_SCIENCE) + ";\n" +
            "}\n" +
            
            ".badge-trending {\n" +
            "    -fx-background-color: " + (isDark ? ColorPalette.Dark.BADGE_TRENDING : ColorPalette.Light.BADGE_TRENDING) + ";\n" +
            "}\n" +
            
            /* ========== SCROLL BAR ========== */
            ".scroll-bar {\n" +
            "    -fx-background-color: " + palette.getBackground() + ";\n" +
            "}\n" +
            
            ".scroll-bar:vertical .thumb {\n" +
            "    -fx-background-radius: 5;\n" +
            "    -fx-background-color: " + palette.getBorder() + ";\n" +
            "}\n" +
            
            ".scroll-bar:vertical .thumb:hover {\n" +
            "    -fx-background-color: " + palette.getTextSecondary() + ";\n" +
            "}\n" +
            
            /* ========== PROGRESS BAR ========== */
            ".progress-bar {\n" +
            "    -fx-accent: " + palette.getPrimary() + ";\n" +
            "    -fx-background-radius: 4px;\n" +
            "}\n" +
            
            /* ========== STATUS COLORS ========== */
            ".success {\n" +
            "    -fx-text-fill: " + palette.getSuccess() + ";\n" +
            "}\n" +
            
            ".warning {\n" +
            "    -fx-text-fill: " + palette.getWarning() + ";\n" +
            "}\n" +
            
            ".error {\n" +
            "    -fx-text-fill: " + palette.getError() + ";\n" +
            "}\n" +
            
            "/* End of Dynamic Stylesheet */\n";
    }
    
    /**
     * Adjust brightness of a hex color (simplified version)
     * Note: This is a basic implementation. For production, use a proper color library.
     */
    private static String adjustBrightness(String hexColor, int amount) {
        // Extract hex values
        String hex = hexColor.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        
        // Adjust
        r = Math.max(0, Math.min(255, r + amount));
        g = Math.max(0, Math.min(255, g + amount));
        b = Math.max(0, Math.min(255, b + amount));
        
        // Convert back to hex
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
