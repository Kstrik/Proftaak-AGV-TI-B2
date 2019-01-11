package sample.GridSelection;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GridSelectionComponent extends Canvas
{
    private Grid grid;

    private ArrayList<Node> path;

    private boolean isdragging;
    private boolean firstDrag;
    private Vector2D difference;

    private GridEventHandler gridEventHandler;

    public GridSelectionComponent(int width, int height, GridEventHandler gridEventHandler)
    {
        this(width, height);

        this.gridEventHandler = gridEventHandler;
    }

    public GridSelectionComponent(int width, int height)
    {
        ColorTheme colorTheme = new ColorTheme(Color.BLACK, Color.RED, Color.BLUE, Color.LIME, Color.BLACK, Color.RED);
        this.grid = new Grid(10, 10, width, height, Vector2D.Zero(), colorTheme, this.getGraphicsContext2D());
        this.setWidth(width);
        this.setHeight(height);
        this.isdragging = false;
        this.firstDrag = true;
        //System.out.println(this.getWidth());
        //System.out.println(this.getHeight());

        this.path = new ArrayList<Node>();

        this.setOnMouseClicked(event -> {
            if(!this.isdragging)
            {
                onMouseClick(new Vector2D((float)event.getX(), (float)event.getY()));
            }
            else
            {
                this.isdragging = false;
                this.firstDrag = true;
            }
        });

        this.setOnMouseDragged(event -> {
            this.isdragging = true;
            onMouseDrag(new Vector2D((float)event.getX(), (float)event.getY()));
        });

//        this.setOnMouseDragReleased(event -> {
//            System.out.println("Released");
//            onMouseDrag(new Vector2D((float)event.getX(), (float)event.getY()));
//            this.isdragging = false;
//            this.firstDrag = true;
//        });

        this.setOnMouseReleased(event -> {
            if(this.isdragging)
            {
                System.out.println("Released");
                onMouseDrag(new Vector2D((float)event.getX(), (float)event.getY()));
            }
        });

        this.setOnMouseMoved(event -> {
            onMouseMove(new Vector2D((float)event.getX(), (float)event.getY()));
        });

        this.setOnScroll(event -> {
            onScroll((float)event.getDeltaY());
        });

        this.grid.draw();
    }

    private void clear()
    {
        this.getGraphicsContext2D().setFill(Color.WHITE);
        this.getGraphicsContext2D().fillRect(-this.getWidth(), -this.getHeight(), this.getWidth() * 3, this.getHeight() * 3);
    }

    public void changeGrid(int gridWidth, int gridHeight, ColorTheme colorTheme)
    {
        this.grid = new Grid(gridWidth, gridHeight, (int)this.getWidth(), (int)this.getHeight(), Vector2D.Zero(), colorTheme, this.getGraphicsContext2D());
        this.clear();
        this.reset();
        this.grid.draw();
    }

    public void reset()
    {
        this.path.clear();
        this.isdragging = false;
        this.firstDrag = true;
    }

    public void clearPath()
    {
        for(Node node : this.path)
        {
            node.setSelected(false);
            node.setNodeState(Node.NodeState.DEFAULT);
        }
        this.path.clear();
        this.grid.draw();
    }

    public void onMouseClick(Vector2D cursorPosition)
    {
        //this.grid.setPosition(cursorPosition);
        //this.grid.setScale(this.grid.getScale() / 2);

        Node node = this.grid.getNodeFromSelection(cursorPosition);

        if(this.path.size() != 0)
        {
            if(node != null)
            {
                if(node.isSelected())
                {
                    if(node.getNodeState() == Node.NodeState.ENDNODE || this.path.size() == 1)
                    {
                        node.toggleIsSelected();
                        this.path.remove(this.path.size() - 1);

                        if(this.path.size() != 0)
                        {
                            this.grid.updateNodeStates(this.path.get(0).getGridPosition(), this.path.get(this.path.size() -1).getGridPosition());
                        }
                        else
                        {
                            this.grid.updateNodeStates(null, null);
                        }
                    }
                }
                else
                {
                    boolean foundSelectedNeighbour = false;

                    for(Vector2D gridPosition : node.getNeigbourNodesGridPositions())
                    {
                        if(gridPosition.x >= 0 && gridPosition.x <= this.grid.getGridWidth() && gridPosition.y >= 0 && gridPosition.y <= this.grid.getGridHeight())
                        {
                            if(this.grid.getNode((int)gridPosition.x, (int)gridPosition.y).isSelected())
                            {
                                Node neighbourNode = (this.grid.getNode((int)gridPosition.x, (int)gridPosition.y));

                                if(neighbourNode.getGridPosition().equals(this.path.get(this.path.size() - 1).getGridPosition()))
                                {
                                    this.path.add(node);
                                    foundSelectedNeighbour = true;
                                    break;
                                }
                            }
                        }
                    }

                    if(foundSelectedNeighbour)
                    {
                        clear();
                        node.toggleIsSelected();

                        if(this.path.size() != 0)
                        {
                            this.grid.updateNodeStates(this.path.get(0).getGridPosition(), this.path.get(this.path.size() -1).getGridPosition());
                        }
                        else
                        {
                            this.grid.updateNodeStates(null, null);
                        }
                    }
                }
            }
        }
        else
        {
            if(node != null)
            {
                clear();
                this.path.add(node);
                node.toggleIsSelected();
                this.grid.updateNodeStates(this.path.get(0).getGridPosition(), this.path.get(this.path.size() -1).getGridPosition());
            }
        }
    }

    public void onMouseDrag(Vector2D cursorPosition)
    {
        clear();

        //this.grid.setPosition(Vector2D.Subtract(cursorPosition, Vector2D.Subtract(this.grid.getPosition(), cursorPosition)));

//        this.grid.setPosition(Vector2D.Add(cursorPosition, new Vector2D(Math.max(this.grid.getPosition().x, cursorPosition.x) - Math.max(this.grid.getPosition().x, cursorPosition.x),
//                                                                            Math.max(this.grid.getPosition().y, cursorPosition.y) - Math.max(this.grid.getPosition().y, cursorPosition.y))));
        if(this.firstDrag)
        {
            this.firstDrag = false;
            Vector2D direction = Vector2D.Direction(cursorPosition, this.grid.getPosition());
            float distance = Vector2D.Distance(cursorPosition, this.grid.getPosition());
            this.difference = Vector2D.Multiply(direction, distance);
        }
        //System.out.println("Difference X: " + difference.x + " Y: " + difference.y);
        this.grid.setPosition(Vector2D.Add(cursorPosition, this.difference));
    }

    public void onMouseMove(Vector2D cursorPosition)
    {
        if(this.gridEventHandler != null)
        {
            Node node = this.grid.getNodeFromSelection(cursorPosition);

            if(node != null)
            {
                this.gridEventHandler.onNodeHover(node);
            }
        }
    }

    public void onScroll(float value)
    {
        clear();

        if(value < 0)
        {
            this.grid.setScale(this.grid.getScale() * 0.75F);
        }
        else
        {
            this.grid.setScale(this.grid.getScale() * 1.25F);
        }
    }

    public ArrayList<Node> getPath()
    {
        return this.path;
    }
}
