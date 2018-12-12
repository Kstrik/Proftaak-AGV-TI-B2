package Logic;

import java.awt.*;

public class PathFinder
{
    private Vector2D boebotPosition;
    private Vector2D boebotForwardDirection;
    private Grid grid;
    private Path path;

    private int currentPathIndex;

    public PathFinder(Vector2D startPosition, Vector2D startDirection, Grid grid, Path path)
    {
        this.boebotPosition = startPosition;
        this.boebotForwardDirection = startDirection.normilized;
        this.grid = grid;
        this.path = path;
    }

    public boolean hasPath()
    {
        if(this.path != null)
        {
            return true;
        }

        return false;
    }

    public Actions getNextAction()
    {
        if(this.currentPathIndex <= (this.path.nodePositions.size() - 1))
        {
            Vector2D nextPosition = this.path.nodePositions.get(this.currentPathIndex);
            //float cross = Vector2D.Cross(nextPosition.normilized, this.boebotPosition.normilized);
            Vector2D direction = Vector2D.Direction(this.boebotPosition, nextPosition);
            float cross = Vector2D.Cross(this.boebotForwardDirection.normilized, direction);

            this.boebotForwardDirection = direction;

            if(Vector2D.Add(this.boebotPosition, direction).equals(nextPosition))
            {
                this.currentPathIndex++;
                return Actions.GOFORWARD;
            }
            else
            {
                if(cross < 0.0F)
                {
                    return Actions.TURNLEFT;
                }
                else if(cross > 0.0F)
                {
                    return Actions.TURNRIGHT;
                }
            }
        }
        else
        {
            return Actions.FINISHED;
        }

        return null;
    }

    public enum Actions
    {
        TURNLEFT,
        TURNRIGHT,
        GOFORWARD,
        FINISHED
    }
}
