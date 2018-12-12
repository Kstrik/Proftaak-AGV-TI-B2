package Logic;

import java.awt.*;
import java.util.ArrayList;

public class Path
{
    public ArrayList<Vector2D> nodePositions;

    public Path()
    {
        this.nodePositions = new ArrayList<Vector2D>();
    }

    public void addPoint(Vector2D position)
    {
        this.nodePositions.add(position);
    }

    public ArrayList<Vector2D> getNodePositions()
    {
        return this.nodePositions;
    }
}
