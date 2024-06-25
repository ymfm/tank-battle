/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import comp603_assesment_mazegame.GameManager.GameStatus;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author 28278
 */
public class WinngCheck extends GameObject
{
    private boolean win = false;
    private Date date;
    
    public void setWin(boolean b)
    {
        this.win = b;
    }
    public boolean getWin()
    {
        return win;
    }
    
    public WinngCheck(String image, int x, int y, GamePanel gp) 
    {
        super(image, x, y, gp);
    }

    @Override
    public void drawObject(Graphics g) 
    {
        g.drawImage(image, posX, posY, gp);
        if(win == false)
        {
            checkWin();
        }

    }

    int lenght = 50;
    
    @Override
    public Rectangle getRec() 
    {
        return new Rectangle(posX, posY, lenght, lenght);
    }
    public void checkWin()
    {
        if(getRec().intersects(GameManager.getInstance().getPlayer().getRec()) && win == false)
        {
            GameManager.getInstance().setGameStatus(GameStatus.SETTLE);
            win = true;
            GameManager.getInstance().setGameDate();
        }
    }
    
}
