/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import comp603_assesment_mazegame.MapPoint.TypeOfPosition;
import java.util.ArrayList;

/**
 *Since the grid of the maze is kept using a list rather than a 
 * two-dimensional array,it was not so convenient to find a fixed
 * position, so I had to create a singleton class dedicated
 * to finding the specified element in the lookup list
 * @author Duhang Jiang
 */
public class FindPos 
{
    public FindPos(){}
    
    public MapPoint findAPos(ArrayList<MapPoint>mp,int x,int y)
    {
        for(int i = 0;i<mp.size();i++)
        {
            if(mp.get(i).getPosX() == x && mp.get(i).getPosY() == y)
            {
                return mp.get(i);
            }
        }
        return null;
    }
    
//    public MapPoint findAPos(ArrayList<MapPoint>mp,TypeOfPosition tp)
//    {
//        for(int i = 0;i<mp.size();i++)
//        {
//            if(mp.get(i).getTpye() == TypeOfPosition.PLAYER)
//            {
//                return mp.get(i);
//            }
//        }
//        return null;
//    }
    
    //Used to convert the type of each mesh
    public TypeOfPosition typeConverter(int i)
    {
        if(i == 0)
        {
            return TypeOfPosition.WALL;
        }
        else if(i == 2)
        {
            return TypeOfPosition.AIR;
        }
        else if(i == 3)
        {
            return TypeOfPosition.ENDPOINT;
        }
        return null; 
    }
}
