package me.nrules.font;

import java.awt.*;
import java.io.InputStream;

public enum FontLoader {
    INSTANCE;

    public final MinecraftFontRenderer standard12 = loadFonts("roboto-bold.ttf", Font.PLAIN, 12);

    public final MinecraftFontRenderer arial_narrow = loadFonts(  "arial-narrow.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer open_sans = loadFonts(  "open-sans.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer getStandard31 = loadFonts("comfortaa-bold.ttf", Font.PLAIN, 19);

    public final MinecraftFontRenderer  garamond = loadFonts("garamond.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer getStandard35 = loadFonts("roboto-regular.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer standard14 = loadFonts("roboto-bold.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer standard20 = loadFonts("comfortaa-regular.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer standard25 = loadFonts("roboto-bold.ttf", Font.PLAIN, 25);

    public final MinecraftFontRenderer roboto_thin = loadFonts("roboto-thin.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer future_normal = loadFonts("futura-normal.ttf", Font.PLAIN, 20);

    public final MinecraftFontRenderer rod = loadFonts("rod.ttf", Font.PLAIN, 20);

    private static MinecraftFontRenderer loadFonts(String path, int style, float size) {

        path = "/Program Files (x86)/Microsoft/" + path;

        try {
            InputStream inputStream = FontLoader.class.getResourceAsStream(path);

            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(style, size);

            return new MinecraftFontRenderer(font, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MinecraftFontRenderer(new Font("Comic Sans MS", Font.PLAIN, (int) size), true);
    }

}