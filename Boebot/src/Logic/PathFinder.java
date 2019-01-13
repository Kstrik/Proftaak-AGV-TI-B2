package Logic;

public class PathFinder
{
    private Vector2D boebotPosition;
    private Vector2D boebotForwardDirection;
    private Vector2D boebotStartDirection;
    private Grid grid;
    private Path path;

    private int currentPathIndex;
    private int currentPositionIndex;

    public PathFinder(Vector2D startDirection, Grid grid, Path path)
    {
        this.boebotPosition = path.nodePositions.get(0);
        this.boebotForwardDirection = new Vector2D(startDirection.x, startDirection.y);
        this.boebotStartDirection = startDirection;
        this.grid = grid;
        this.path = path;

        this.currentPathIndex = 1;
        this.currentPositionIndex = 0;
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
        if(this.currentPositionIndex < (this.path.nodePositions.size() - 1))
        {
            Vector2D nextPosition = this.path.nodePositions.get(this.currentPathIndex);
            Vector2D direction = Vector2D.Direction(this.boebotPosition, nextPosition);
            float cross = Vector2D.Cross(this.boebotForwardDirection, direction);

            //this.boebotForwardDirection = direction;

            this.currentPositionIndex++;
            this.boebotPosition = this.path.nodePositions.get(this.currentPositionIndex);
            this.currentPathIndex++;

//            System.out.println("Direction: " + direction.x + " : " + direction.y);
//            System.out.println("Cross: " + cross);

            if(cross > -0.1F && cross < 0.1F)
            {
                this.boebotForwardDirection = direction;
                return Actions.GOFORWARD;
            }
            else
            {
//                if(cross > -0.9F && cross < 0.0F)
//                {
//                    //double radian = 90.0 * Math.PI / 180.0;
//                    //this.boebotForwardDirection = Vector2D.Multiply(this.boebotForwardDirection, Vector2D.Right());
//                    System.out.println("Right");
//                    return Actions.TURNRIGHT;
//                }
//                else if(cross < 0.9F && cross > 0.0F)
//                {
//                    //this.boebotForwardDirection = Vector2D.Multiply(this.boebotForwardDirection, Vector2D.Left());
//                    System.out.println("Left");
//                    return Actions.TURNLEFT;
//                }
//                else
//                {
                    if(cross < 0.0F)
                    {
                        this.boebotForwardDirection = direction;
                        return Actions.TURNRIGHT;
                    }
                    else if(cross > 0.0F)
                    {
                        this.boebotForwardDirection = direction;
                        return Actions.TURNLEFT;
                    }
//                }
            }
        }
        else
        {
            reset();

            return Actions.FINISHED;
        }
        return null;
    }

    public void reset()
    {
        this.currentPathIndex = 1;
        this.currentPositionIndex = 0;
        this.boebotForwardDirection = new Vector2D(this.boebotStartDirection.x, this.boebotStartDirection.y);
        this.boebotPosition = this.path.nodePositions.get(0);
    }

    public void setPath(Path path)
    {
        this.path = path;
    }

    public enum Actions
    {
        TURNLEFT,
        TURNRIGHT,
        GOFORWARD,
        FINISHED
    }
}
