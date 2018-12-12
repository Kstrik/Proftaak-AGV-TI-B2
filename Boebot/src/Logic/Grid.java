package Logic;

public class Grid
{
    private Node[][] gridNodes;

    public Grid(int width, int height)
    {
        initializeGridNodes(width, height);
    }

    private void initializeGridNodes(int width, int height)
    {
        this.gridNodes = new Node[height][width];

        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                this.gridNodes[y][x] = new Node();
            }
        }
    }

    public Node[][] getGridNodes()
    {
        return this.gridNodes;
    }

    public Node getNode(int x, int y)
    {
        return this.gridNodes[y][x];
    }

    public void addNodeToPath(int x, int y)
    {
        this.getNode(x, y).setBelongsToPath(true);
    }

    public void removeNodeToPath(int x, int y)
    {
        this.getNode(x, y).setBelongsToPath(false);
    }
}
