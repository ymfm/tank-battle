/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Duhang Jiang
 */
public class Bot extends Characeter 
{
    int movement = 0;
    
    public Bot(String image, int x, int y, GamePanel gp, String up, String down, String left, String right) 
    {
        super(image, x, y, gp, up, down, left, right);
    }

    @Override
    public void drawObject(Graphics g) 
    {
        g.drawImage(this.image, posX, posY, null);
        botMove();
    }

    @Override
    public Rectangle getRec() 
    {
        return new Rectangle(posX,posY,width,height);
    }
    
    //bot tank random direction
    public Direction randomDirection() 
    {
        Random r = new Random();
        int rd = r.nextInt(4);
        switch(rd) 
        {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.LEFT;
            default:
                return Direction.DOWN;
        }
    }
    
    public void botMove()
    {
        if(movement>=15) 
        {
            this.dir = randomDirection();
            movement = 0;
        }
        else 
        {
            movement+=1;
        }
        
        switch(dir) 
        {
            case UP:
                upWard();
                break;
            case DOWN:
                downWard();
                break;
            case RIGHT:
                rightWard();
                break;
            case LEFT:
                leftWard();
                break;
        }
    }
}
