package me.nrules.font;

public interface FontRenderer {

    /**
     * @return The width (in pixels) of the text rendered.
     */
    int drawString(FontData fontData, String text, int x, int y, int color);

    /**
     * @return The width (in pixels) of the text rendered.
     */
    int drawString(String text, int x, int y, int color);

    /**
     * @return The {@link FontData} used by this FontRenderer.
     */
    FontData getFontData();

}