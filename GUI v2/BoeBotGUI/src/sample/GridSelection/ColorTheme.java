package sample.GridSelection;

import javafx.scene.paint.Color;

public class ColorTheme
{
    public Color nodeDefaultColor;
    public Color nodeSelectedColor;
    public Color nodeStartPathColor;
    public Color nodeEndPathColor;

    public Color lineDefaultColor;
    public Color lineSelectedColor;

    public ColorTheme(Color nodeDefaultColor, Color nodeSelectedColor, Color nodeStartPathColor, Color nodeEndPathColor, Color lineDefaultColor, Color lineSelectedColor)
    {
        this.nodeDefaultColor = nodeDefaultColor;
        this.nodeSelectedColor = nodeSelectedColor;
        this.nodeStartPathColor = nodeStartPathColor;
        this.nodeEndPathColor = nodeEndPathColor;

        this.lineDefaultColor = lineDefaultColor;
        this.lineSelectedColor = lineSelectedColor;
    }
}
