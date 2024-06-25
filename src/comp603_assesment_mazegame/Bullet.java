package comp603_assesment_mazegame;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.*;
import java.util.ArrayList;
//Bullets are used to destroy enemy tanks
public class Bullet extends GameObject
{

    private int width = 10;
    private int height = 10;
    //speed
    private int speed = 12;
    Direction direction;
    //Initialize and generate bullets
    public Bullet(String img, int x, int y, Direction direction,GamePanel gamePanel) 
    {
        super(img, x,  y, gamePanel);
        this.direction = direction;
    }

    //Must be called on every repaint to keep moving
    public void move()
    {
        switch (direction)
        {
            case UP:
                upWard();
                break;
            case LEFT:
                leftWard();
                break;
            case DOWN:
                downWard();
                break;
            case RIGHT:
                rightWard();
                break;
        }
    }

    public void leftWard()
    {
        posX -= speed;
    }
    public void rightWard()
    {
        posX += speed;
    }
    public void upWard()
    {
        posY -= speed;
    }
    public void downWard()
    {
        posY += speed;
    }

    @Override
    public Rectangle getRec()
    {
        return new Rectangle(posX, posY, width, height);
    }

    @Override
    public void drawObject(Graphics g) 
    {
        g.drawImage(image, posX, posY, null);
        move();
        hitBot();
        hitWall();
    }
    
    //Bullet and Tank Collision Detection
    public void hitBot()
    {
        Rectangle next= this.getRec();
        ArrayList<Bot> bots = this.gp.getBotList();
       
        for(Bot bot: bots)
        {
            if(bot.getRec().intersects(next))
            {
                this.gp.getBotList().remove(bot);
                this.gp.getRemoveList().add(this);
                
                this.gp.getPlayer().killedEnemy();
                break;
            }
        }
    }
    //Bullet and wall Collision Detection
    public void hitWall()
    {
        Rectangle next = this.getRec();
        ArrayList<Wall> walls = GameManager.getInstance().getWallList();
        for(Wall w: walls) 
        {
            if (w.getRec().intersects(next))
            {
                this.gp.getRemoveList().add(this);
                break;
            }
        }
    }
}
