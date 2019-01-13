package sample.GridSelection;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Grid
{
    private Node[][] gridNodes;
    private Vector2D position;

    private float scale;

    private int gridWidth;
    private int gridHeight;

    private int pixelWidth;
    private int pixelHeight;

    private float gridNodePixelSize;

    private ColorTheme colorTheme;

    private GraphicsContext graphicsContext;

    public Grid(int gridWidth, int gridHeight, int pixelWidth, int pixelHeight, Vector2D position, ColorTheme colorTheme, GraphicsContext graphicsContext)
    {
        this.position = position;
        this.scale = 1.0F;

        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        //this.gridNodePixelSize = (pixelWidth / gridWidth);
        this.gridNodePixelSize = (Math.min(pixelWidth, pixelHeight) / Math.min(gridWidth, gridHeight));
        System.out.println(this.gridNodePixelSize);

        this.colorTheme = colorTheme;

        this.graphicsContext = graphicsContext;

        initializeGridNodes();
    }

    private void initializeGridNodes()
    {
        this.gridNodes = new Node[this.gridHeight][this.gridWidth];

        for(int y = 0; y < this.gridHeight; y++)
        {
            for(int x = 0; x < this.gridWidth; x++)
            {
                Vector2D nodePosition = new Vector2D((this.position.x + ((x * this.gridNodePixelSize) -1) + (this.gridNodePixelSize / 2)), this.position.y + (((y * this.gridNodePixelSize) -1) + (this.gridNodePixelSize / 2)));
                this.gridNodes[y][x] = new Node(nodePosition, new Vector2D(x, (this.gridHeight - y) - 1), this.scale, (this.gridNodePixelSize / 2), this.colorTheme);
            }
        }
    }

    public void updateNodePositions()
    {
        float scaledGridNodePixelSize = this.gridNodePixelSize * this.scale;

        for(int y = 0; y < this.gridHeight; y++)
        {
            for(int x = 0; x < this.gridWidth; x++)
            {
                Vector2D nodePosition = new Vector2D((this.position.x + ((x * scaledGridNodePixelSize) -1) + (scaledGridNodePixelSize / 2)), this.position.y + (((y * scaledGridNodePixelSize) -1) + (scaledGridNodePixelSize / 2)));
                this.gridNodes[y][x].setPosition(nodePosition);
            }
        }
    }

    private void updateNodeScales()
    {
        for(int y = 0; y < this.gridHeight; y++)
        {
            for(int x = 0; x < this.gridWidth; x++)
            {
                this.gridNodes[y][x].setScale(this.scale);
            }
        }

        updateNodePositions();
    }

    private void updateNodeColorThemes()
    {
        for(int y = 0; y < this.gridHeight; y++)
        {
            for(int x = 0; x < this.gridWidth; x++)
            {
                this.gridNodes[y][x].setColorTheme(this.colorTheme);
            }
        }
    }

    public void updateNodeStates(Vector2D startGridPosition, Vector2D endGridPosition)
    {
        for(Node[] nodesRow : this.gridNodes)
        {
            for(Node node : nodesRow)
            {
                node.updateState(startGridPosition, endGridPosition);
            }
        }

        this.draw();
    }

    public boolean checkNeigbourSelection(Node node)
    {


        return true;
    }

    public Node getNodeFromSelection(Vector2D cursorPosition)
    {
        for(Node[] nodesRow : this.gridNodes)
        {
            for(Node node : nodesRow)
            {
                if(node.select(cursorPosition) == true)
                {
                    return node;
                }
            }
        }

        return null;
    }

    public void draw()
    {
        for(Node[] nodesRow : this.gridNodes)
        {
            for(Node node : nodesRow)
            {
                node.draw(this.graphicsContext);
            }
        }
    }

    public Node[][] getGridNodes()
    {
        return this.gridNodes;
    }

    public Node getNode(int x, int y)
    {
        return this.gridNodes[(this.gridHeight - y) - 1][x];
    }

    public Vector2D getPosition()
    {
        return this.position;
    }

    public float getScale()
    {
        return this.scale;
    }

    public int getGridWidth()
    {
        return this.gridWidth;
    }

    public int getGridHeight()
    {
        return this.gridHeight;
    }

    public int getPixelWidth()
    {
        return this.pixelWidth;
    }

    public int getPixelHeight()
    {
        return this.pixelHeight;
    }

    public ColorTheme getColorTheme()
    {
        return this.colorTheme;
    }

    public void setPosition(Vector2D position)
    {
        this.position = position;
        this.updateNodePositions();
        this.draw();
    }

    public void setScale(float scale)
    {
        this.scale = scale;
        this.updateNodeScales();
        this.draw();
    }

    public void setColorTheme(ColorTheme colorTheme)
    {
        this.colorTheme = colorTheme;
        this.updateNodeColorThemes();
        this.draw();
    }
}
