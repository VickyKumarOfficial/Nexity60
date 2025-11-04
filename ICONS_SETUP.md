# PWA Icons Setup

## Required Icons

For a complete PWA experience, you need the following icon sizes:

### Required Sizes
- 192x192 pixels (Android)
- 512x512 pixels (Android)
- 180x180 pixels (iOS)
- 32x32 pixels (Favicon)
- 16x16 pixels (Favicon)

## How to Generate Icons

### Option 1: Online Tool (Recommended)
1. Go to [PWA Asset Generator](https://www.pwabuilder.com/imageGenerator)
2. Upload a square logo (at least 512x512)
3. Download the generated icon pack
4. Copy all icons to `pwa-frontend/public/` folder

### Option 2: Manual Creation
1. Create a 512x512 logo in any design tool (Figma, Canva, Photoshop)
2. Use [RealFaviconGenerator](https://realfavicongenerator.net/)
3. Upload your logo
4. Download the package
5. Extract to `pwa-frontend/public/`

### Option 3: Use Existing Logo
If you have an existing logo, resize it using:
- Online tools: [ResizeImage.net](https://resizeimage.net/)
- Desktop: GIMP, Photoshop, or Preview (Mac)

## Current Icon Configuration

The `manifest.json` references these icons:
```json
{
  "icons": [
    {
      "src": "logo192.png",
      "type": "image/png",
      "sizes": "192x192"
    },
    {
      "src": "logo512.png",
      "type": "image/png",
      "sizes": "512x512"
    }
  ]
}
```

## Placeholder Icons

For now, you can use these placeholder images:
1. Create solid color squares in your brand colors
2. Add app name text overlay
3. Use emoji as temporary icons

Example using Paint/Preview:
1. Create 512x512 canvas
2. Fill with brand color (e.g., #2196F3)
3. Add "N60" text in white
4. Save as `logo512.png`
5. Resize to 192x192 and save as `logo192.png`

## Icon Checklist

- [ ] Create/obtain base logo (512x512)
- [ ] Generate 192x192 version
- [ ] Generate 512x512 version
- [ ] Create favicon (32x32)
- [ ] Place all icons in `pwa-frontend/public/`
- [ ] Update `manifest.json` if icon names differ
- [ ] Test PWA installation on mobile device

## Testing Icons

1. Deploy the app
2. Open on mobile device
3. Look for "Add to Home Screen" prompt
4. Install app
5. Check home screen icon quality
6. Adjust if icons appear blurry

## Icon Design Tips

- **Keep it simple**: Icons should be recognizable at small sizes
- **High contrast**: Works on both light and dark backgrounds
- **No text**: Small text becomes unreadable
- **Square canvas**: Don't crop to circle (OS will handle that)
- **Padding**: Leave ~10% margin around important elements
- **Brand colors**: Use your app's primary colors

## Temporary Solution

Until you create custom icons, the app will use browser defaults. The PWA will still work, but won't look as polished when installed.

To add basic icons quickly:
1. Download any square logo/image
2. Use an online resizer to create 192x192 and 512x512 versions
3. Name them `logo192.png` and `logo512.png`
4. Place in `pwa-frontend/public/`
5. Done!

---

For questions, refer to [PWA Documentation](https://web.dev/add-manifest/)
