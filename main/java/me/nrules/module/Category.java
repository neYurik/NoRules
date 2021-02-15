package me.nrules.module;

public enum Category {
    COMBAT("COMBAT"), MOVEMENT("MOVEMENT"), RENDER("RENDER"), PLAYER("PLAYER"), FUN("FUN"), GHOST("GHOST"), MISC("MISC");

    public String name;
    public int moduleIndex;
    public double x;
    public double y;
    public double width;
    public double height;

    Category(String name)
    {
        this.name = name;
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return isHovered(mouseX, mouseY);
    }

    public boolean isHovered(int mouseX, int mouseY)
    {

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
