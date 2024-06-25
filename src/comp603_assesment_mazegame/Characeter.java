/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author Duhang Jiang
 */
public abstract class Characeter extends GameObject
{
    //rec of image
    protected int width = 40;
    protected int height = 50;
    //move speed in maze
    protected int speed = 10;
    //direction of image
    protected Direction dir = Direction.UP;
    // life of player
    protected boolean alive = true;
    //Change the picture when the character moves
    protected String upImage;
    protected String downImage;
    protected String leftImage;
    protected String rightImage;
    
    public Characeter(String image, int x, int y, GamePanel gp,String up,String down,String left,String right) 
    {
        super(image, x, y, gp); 
        this.upImage = up;
        this.rightImage = right;
        this.leftImage = left;
        this.downImage = down;
    }
    
    @Override
    public abstract void drawObject(Graphics g);

    @Override
    public abstract Rectangle getRec();
    
    
    public enum direction
    {
        UP,LEFT,RIGHT,DOWN
    }
    
    //Get pictures in 4 directions and move them with keys
    public void leftWard()
    {
        dir = Direction.LEFT;
        setImg(leftImage);
        if(!hitWall(posX-speed, posY))
        {
            this.posX -= speed;
        }
    }
    public void rightWard()
    {
        dir = Direction.RIGHT;
        setImg(rightImage);
         
        if(!hitWall(posX+speed, posY))
        {
            this.posX += speed;
        }
    }
    public void upWard()
    {
        dir = Direction.UP;
        setImg(upImage);
         
        if(!hitWall(posX, posY-speed))
        {
            this.posY -= speed;
        }
    }
    public void downWard()
    {
        dir = Direction.DOWN;
        setImg(downImage);
        
        if(!hitWall(posX, posY+speed))
        {
            this.posY += speed;
        }
    }
        
    protected boolean up = false;
    protected boolean left = false;
    protected boolean right = false;
    protected boolean down = false;
        
    public void move()
    {
        if(left)
        {
            leftWard();
        }
        else if(right)
        {
            rightWard();
        }
        else if(up)
        {
            upWard();
        }
        else if(down)
        {
            downWard();
        }
    }
    
    //Call and import the picture through the encapsulated four functions representing directions
    public void setImg(String img)
    {
        this.image = Toolkit.getDefaultToolkit().getImage(img);
    }
    
    //Detect if it collides with the wall, if there is, it will not be able to move forward
    public boolean hitWall(int x, int y)
    {
        //All walls in the map
        ArrayList<Wall> walls = GameManager.getInstance().getWallList();                
        //if the player's tank moves forward, the rectangle formed by the next position
        Rectangle next = new Rectangle(x, y, width, height);
        //Determine whether two rectangles intersect
        for(Wall w:walls)
        {
            if(w.getRec().intersects(next))
            {                 
                return true;
            }
        }
        return false;
    }
    //Determine the head position according to the direction
    //x and y are the points in the lower left corner, which makes the fired bullet more beautiful
    public Point getHeadPoint()
    {
        switch (dir)
        {
            case UP:
                return new Point(posX + width/2, posY );
            case LEFT:
                return new Point(posX, posY + height/2);
            case DOWN:
                return new Point(posX + width/2, posY + height);
            case RIGHT:
                return new Point(posX + width, posY + height/2);
            default:
                return null;
        }
    }
    
    public void attack()
    {
        Point p = getHeadPoint();
        Bullet bullet = new Bullet(".\\src\\Image\\bulletGreen.gif",p.x,p.y,dir, this.gp);
        this.gp.bulletList.add(bullet);
    }
    
    public int getPosX()
    {
        return this.posX;
    }
    
    public int getPosY()
    {
        return this.posY;
    }
}

