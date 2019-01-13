package sample.GridSelection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Node
{
    private Vector2D[] neigbourNodesGridPositions;
    private boolean isSelected;

    private Vector2D gridPosition;
    private Vector2D position;

    private float scale;
    private float radius;

    private ColorTheme colorTheme;

    private NodeState nodeState;

    public Node(Vector2D position, Vector2D gridPosition, float scale, float radius, ColorTheme colorTheme)
    {
        this.neigbourNodesGridPositions = new Vector2D[4];
        this.isSelected = false;

        this.gridPosition = gridPosition;
        this.position = position;

        this.scale = scale;
        this.radius = radius;

        this.colorTheme = colorTheme;

        this.nodeState = NodeState.DEFAULT;

        findNeighbourGridPositions();
    }

    private void findNeighbourGridPositions()
    {
        this.neigbourNodesGridPositions[0] = Vector2D.Add(this.gridPosition, Vector2D.Up());
        this.neigbourNodesGridPositions[1] = Vector2D.Add(this.gridPosition, Vector2D.Left());
        this.neigbourNodesGridPositions[2] = Vector2D.Add(this.gridPosition, Vector2D.Down());
        this.neigbourNodesGridPositions[3] = Vector2D.Add(this.gridPosition, Vector2D.Right());
    }

    private Color getColorFromState()
    {
        switch(this.nodeState)
        {
            case DEFAULT:
            {
                return this.colorTheme.nodeDefaultColor;
            }
            case SELECTED:
            {
                return this.colorTheme.nodeSelectedColor;
            }
            case STARTNODE:
            {
                return this.colorTheme.nodeStartPathColor;
            }
            case ENDNODE:
            {
                return this.colorTheme.nodeEndPathColor;
            }
            default:
{
                return this.colorTheme.nodeDefaultColor;
            }
        }
    }

    public boolean select(Vector2D cursorPosition)
    {
        if(Vector2D.Distance(this.position, cursorPosition) < this.getRadiusWithScale())
        {
            return true;
        }
        return false;
    }

    public void updateState(Vector2D startGridPosition, Vector2D endGridPosition)
    {
        if(this.isSelected && startGridPosition != null)
        {
            if(this.gridPosition.equals(startGridPosition))
            {
                this.nodeState = NodeState.STARTNODE;
            }
            else if(this.gridPosition.equals(endGridPosition))
            {
                this.nodeState = NodeState.ENDNODE;
            }
            else
            {
                this.nodeState = NodeState.SELECTED;
            }
        }
        else
        {
            this.nodeState = NodeState.DEFAULT;
        }

//        if(this.gridPosition.equals(startGridPosition))
//        {
//            this.nodeState = NodeState.STARTNODE;
//        }
//        else if(this.gridPosition.equals(endGridPosition))
//        {
//            this.nodeState = NodeState.ENDNODE;
//        }
//        else
//        {
//            if(this.isSelected)
//            {
//                this.nodeState = NodeState.SELECTED;
//            }
//            else
//            {
//                this.nodeState = NodeState.DEFAULT;
//            }
//        }
    }

    public void toggleIsSelected()
    {
        this.isSelected = !this.isSelected;
    }

    public void draw(GraphicsContext graphicsContext)
    {
        graphicsContext.setFill(getColorFromState());
        graphicsContext.fillArc(this.position.x, this.position.y, (this.radius * this.scale), (this.radius * this.scale), 0, 360, ArcType.ROUND);
    }

    public Vector2D[] getNeigbourNodesGridPositions()
    {
        return this.neigbourNodesGridPositions;
    }

    public boolean isSelected()
    {
        return this.isSelected;
    }

    public Vector2D getGridPosition()
    {
        return this.gridPosition;
    }

    public Vector2D getPosition()
    {
        return this.position;
    }

    public float getScale()
    {
        return this.scale;
    }

    public float getRadius()
    {
        return this.radius;
    }

    public float getRadiusWithScale()
    {
        return (this.radius * this.scale);
    }

    public ColorTheme getColorTheme()
    {
        return this.colorTheme;
    }

    public NodeState getNodeState()
    {
        return this.nodeState;
    }

    public void setSelected(boolean selected)
    {
        this.isSelected = selected;
    }

    public void setPosition(Vector2D position)
    {
        this.position = position;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public void setColorTheme(ColorTheme colorTheme)
    {
        this.colorTheme = colorTheme;
    }

    public void setNodeState(NodeState nodeState)
    {
        this.nodeState = nodeState;
    }

    protected enum NodeState
    {
        DEFAULT,
        SELECTED,
        STARTNODE,
        ENDNODE
    }
}
