/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

/**
 *This class controls the moveable range of the character, i.e. the coordinates
 * that can be reached.
 * @author 28278
 */
public class MapPoint
{
    
    //Positions are used to create a character's moveable range
    private int xPos;
    private int yPos;
    private boolean isVisited = false;//Indicates that it has not been accessed
    enum TypeOfPosition
    {
        WALL,PLAYER,AIR,ENDPOINT
    }
    //If there is a wall in front, then the character cannot go any further.
    private TypeOfPosition tp;

    //Initialisation of positions
    public MapPoint(int x,int y)
    {
        this.xPos = x;
        this.yPos = y;
    }
    
    public MapPoint(){}
    
    public int getPosX()
    {
        return this.xPos;
    }
    public int getPosY()
    {

        return this.yPos;
    }
    
    public void setTpye(TypeOfPosition t)
    {
        this.tp = t;
    }
    
    public TypeOfPosition getTpye()
    {
        return this.tp;
    }
    
    public void setisVisit(boolean b)
    {
        this.isVisited = b;
    }
    
    public boolean getVisit()
    {
        return this.isVisited;
    }
    
    public String drawType()
    {
        if(tp == TypeOfPosition.ENDPOINT)
        {
            return "☆";
        }
        else if(tp == TypeOfPosition.WALL)
        {
            return "#";
        }
        else if(tp == TypeOfPosition.PLAYER)
        {
            return "U";
        }
        else
        {
            return " ";
        }
    }
  
}
