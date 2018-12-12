package Logic;

public class Node
{
    private boolean isVisited;
    private boolean belongsToPath;

    public Node()
    {
        this.isVisited = false;
        this.belongsToPath = false;
    }

    public boolean getIsVisited()
    {
        return this.isVisited;
    }

    public boolean getBelongsToPath()
    {
        return this.belongsToPath;
    }

    public void setIsVisited(boolean isVisited)
    {
        this.isVisited = isVisited;
    }

    public void setBelongsToPath(boolean belongsToPath)
    {
        this.belongsToPath = belongsToPath;
    }
}
