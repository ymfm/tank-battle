/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import comp603_assesment_mazegame.MapPoint.TypeOfPosition;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 *Draw the game map here
 * @author Duhang Jiang
 */
public class DrawGameMap 
{   
    private ArrayList<MapPoint> mp = new ArrayList<MapPoint>();
    
    //the datas to build the maze
    private Stack<MapPoint> path = new Stack<MapPoint>();
    private int[][] move = 
    {
       {0,2},{0,-2},{2,0},{-2,0}
    };
    
    private int mazeSizeX;
    private int mazeSizeY;
    
    
    public DrawGameMap(int x,int y)
    {
        this.mazeSizeX = x;
        this.mazeSizeY = y;
        //Initialize the map its size is 10x10
        for(int i = 0;i<mazeSizeX;i++)
        {
            for(int j = 0;j<mazeSizeY;j++)
            {
                mp.add(new MapPoint(i,j));
            }
        }
    }
    
    public int getMazeSizeX()
    {
        return this.mazeSizeX;
    }
    
    public void setMazeSizeX(int x)
    {
        this.mazeSizeX = x;
    }
    
    public int getMazeSizeY()
    {
        return this.mazeSizeY;
    }
    
    public void setMazeSizeY(int y)
    {
        this.mazeSizeY = y;
    }
    
    public ArrayList<MapPoint> getPos()
    {
        return this.mp;
    }
    
    public void setPos(ArrayList<MapPoint> m)
    {
        this.mp = m;
    }
    
    //draw new map
    //step 1 change all pos to walls
    public void drawNewMaze()
    {
        for(int i = 0;i<mp.size();i++)
        {
            mp.get(i).setTpye(MapPoint.TypeOfPosition.WALL);
        }
    }
    //step 2 Draw the maze
    //The maze can be randomly generated using a depth-first algorithm
    public void buildMaze()
    {
        //Prioritize the position of the end point   
        mp.get(mp.size()-2).setTpye(MapPoint.TypeOfPosition.AIR);
        MapPoint curPos = mp.get(0);
        MapPoint nextPos;
        curPos.setTpye(TypeOfPosition.AIR);
        
        //push the stack, let the program start running
        path.push(curPos);
        //If the stack is empty, it means that all nodes have been traversed
        //to end the loop
        while(!path.isEmpty())
        {
            
            MapPoint[] p = notVisitedPos(curPos);
            //If there are no unvisited points around, then go back to the previous point
            if(p.length == 0)
            {
                curPos = path.pop();
                continue;
            }
            
            nextPos = p[new Random().nextInt(p.length)];
            int x = nextPos.getPosX();
            int y = nextPos.getPosY();
            
            for(int i = 0;i<mp.size();i++)
            {
                if(mp.get(i).getPosX() == x && mp.get(i).getPosY() == y)
                {
                    //If the current coordinate has already been marked as visited, then out of the stack
                    if(mp.get(i).getVisit())
                    {
                        curPos = path.pop();
                        curPos.setisVisit(true);
                    }
                    else//If not visited then use this coordinate as the next starting point
                    {
                        path.push(nextPos);
                        curPos.setisVisit(true);
                        mp.get(i).setisVisit(true);
                        mp.get(i).setTpye(MapPoint.TypeOfPosition.AIR);
                        int j = 0;
                        //Because it moves with two coordinates every time, when opening the wall, it must also open the wall in the middle of the two grids
                        pointsApart(curPos,nextPos);
                        curPos = nextPos;
                    }
                }    
            } 
        }
        mp.get(0).setTpye(MapPoint.TypeOfPosition.PLAYER);
        mp.get(mp.size()-1).setTpye(MapPoint.TypeOfPosition.ENDPOINT);
    }

    //Find coordinates that have not been visited
    MapPoint[] notVisitedPos(MapPoint p)
    {
        ArrayList<MapPoint> list = new ArrayList<MapPoint>();
        
        for(int i = 0; i < move.length; i++)
        {
            int x = p.getPosX() + move[i][0];
            int y = p.getPosY() + move[i][1];
            if( x >= 0 && x < mazeSizeX && y >= 0 && y < mazeSizeY)
            {
                for(int j = 0;j<mp.size();j++)
                {
                    if(mp.get(j).getPosX() == x && mp.get(j).getPosY() == y && !mp.get(j).getVisit())
                    {
                        list.add(mp.get(j));
                    }
                }
            }
        }
        
        MapPoint[] m = new MapPoint[list.size()];
        for(int i = 0;i<list.size();i++)
        {
            m[i] = list.get(i);
        }
        return m;
    }
    
    //Calculate the variety in the middle of two squares and knock him apart
    public void pointsApart(MapPoint cur,MapPoint next)
    {
        int x = cur.getPosX()+(next.getPosX() - cur.getPosX())/2;
        int y = cur.getPosY()+(next.getPosY() - cur.getPosY())/2;
        
        for(int i = 0; i<mp.size();i++)
        {
            if(mp.get(i).getPosX() == x && mp.get(i).getPosY() == y)
            {
                mp.get(i).setTpye(TypeOfPosition.AIR);
                mp.get(i).setisVisit(true);
            }
        }
    }
    //****************************************
    //Update this class on the original basis
    //To convert the symbols in the console into pictures, we only need to 
    //import the pictures after obtaining the type of the corresponding
    //coordinates, and then print them on the panel.
    //****************************************
    
    //Since we only need to print the walls, first remove the excess types in 
    //the original code, and convert them to arrays to save.
    
    ArrayList<Wall> wallArray;
    
    public int[][] convertMap()
    {
        int[][] newMap = new int[mazeSizeX][mazeSizeY];
        int count = 0;
        
        for(int i = 0;i<mazeSizeX;i++)
        {
            for(int j = 0;j<mazeSizeY;j++)
            {
                if(mp.get(count).getTpye() == TypeOfPosition.WALL)
                {
                    newMap[i][j] = 1;
                }
                else
                {
                    newMap[i][j] = 0;
                }
                count ++;
            }
        }
        return newMap;
    }
    
    public ArrayList<Wall> drawGraphicsMap()
    {
        int x = 50;
        int y = 50;
        
        wallArray = new ArrayList<Wall>();
        
        for(int i = 0;i<mazeSizeX;i++)
        {
            for(int j = 0;j<mazeSizeY;j++)
            {
                if(convertMap()[i][j] == 0)
                {
                    x = (i+1)*60;
                    y = 60+j*60;
                }
                else
                {
                    x = (i+1)*60;
                    y = 60+j*60;
                    wallArray.add(new Wall(".\\src\\Image\\WALL.gif",x,y,GamePanel.getInstance().getGamePanel()));
                }
            }
            wallArray.add(new Wall(".\\src\\Image\\WALL.gif",0,0,GamePanel.getInstance().getGamePanel()));
            for(int k = 0;k<mazeSizeX;k++)
            {
                wallArray.add(new Wall(".\\src\\Image\\WALL.gif",0,(k+1)*60,GamePanel.getInstance().getGamePanel()));
                wallArray.add(new Wall(".\\src\\Image\\WALL.gif",(k+1)*60,0,GamePanel.getInstance().getGamePanel()));
            }
        }
        return wallArray;
    }
    
    public ArrayList<Wall> getWallList()
    {
        return this.wallArray;
    }
    
    public void setWallList(ArrayList<Wall> w)
    {
        this.wallArray = w;
    }
}
