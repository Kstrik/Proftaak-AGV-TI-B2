package Logic;

import java.awt.*;

public class PathFinder
{
    private Vector2D boebotPosition;
    private Vector2D boebotForwardDirection;
    private Grid grid;
    private Path path;

    private int currentPathIndex;

    public PathFinder(Vector2D startDirection, Grid grid, Path path)
    {
        this.boebotPosition = path.nodePositions.get(0);
        this.boebotForwardDirection = startDirection;
        this.grid = grid;
        this.path = path;

        this.currentPathIndex = 1;
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
            System.out.println("Start pathfinder");
            Vector2D nextPosition = this.path.nodePositions.get(this.currentPathIndex);
            System.out.println(nextPosition.x + " : " + nextPosition.y);
            //float cross = Vector2D.Cross(nextPosition.normilized, this.boebotPosition.normilized);
            Vector2D direction = Vector2D.Direction(this.boebotPosition, nextPosition);
            System.out.println(direction.x + " : " + direction.y);
            float cross = Vector2D.Cross(this.boebotForwardDirection, direction);
            System.out.println(cross);

            this.boebotForwardDirection = direction;

            //if(Vector2D.Add(this.boebotPosition, direction).equals(nextPosition))
            if(cross > -0.01F && cross < 0.01F)
            {
                this.currentPathIndex++;
                System.out.println("GOFORWARD");
                return Actions.GOFORWARD;
            }
            else
            {
                if(cross < 0.0F)
                {
                    System.out.println("TURNLEFT");
                    return Actions.TURNLEFT;
                }
                else if(cross > 0.0F)
                {
                    System.out.println("TURNRIGHT");
                    return Actions.TURNRIGHT;
                }
            }
        }
        else
        {
            System.out.println("FINISHED");
            return Actions.FINISHED;
        }
        System.out.println("TEST");
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
