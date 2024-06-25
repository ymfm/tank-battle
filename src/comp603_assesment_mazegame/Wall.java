/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *Since we have already written the map generation script, we only need to
 * draw it in the form of a picture
 * @author 28278
 */
public class Wall extends GameObject 
{
    public Wall(String image, int x, int y, GamePanel gp)
    {
        super(image, x, y, gp);
    }

    @Override
    public void drawObject(Graphics g)
    {
        g.drawImage(image, posX, posY, gp);
    }

    int length = 60;
    @Override
    public Rectangle getRec()
    {
        return new Rectangle(posX,posY,length,length);
    }    
    
    public void setPos(int x,int y)
    {
        this.posX = x;
        this.posY = y;
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
