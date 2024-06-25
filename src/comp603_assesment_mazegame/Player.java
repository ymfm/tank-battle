/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Duhang Jiang
 */
public class Player extends Characeter
{
    //用于判断玩家是否存活
    private boolean alive = true;
    private int killsNum = 0;
    
    public Player(String image, int x, int y, GamePanel gp, String up, String down, String left, String right) 
    {
        super(image, x, y, gp, up, down, left, right);
    }
    
    @Override
    public void drawObject(Graphics g) 
    {
        //绘制游戏对象的图片
        g.drawImage(this.image, posX, posY, null);
        move();
        tankDestroy();
    }
    @Override
    public Rectangle getRec() 
    {   
        //当碰到其他东西时进行反馈
        return new Rectangle(posX,posY,width,height);
    }
    
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        switch (key)
        {
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_SPACE:
                attack();
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        switch (key)
        {
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_W:
                up = false;
                break;
            default:
                break;
        }
    }
    
    //Set the player position at the start of a new game
    public void setPlayerPos(int x,int y)
    {
        this.posX = x;
        this.posY = y;
    }
    
    //Player dies after hitting an enemy tank
    public void tankDestroy()
    {
        for(Bot b:gp.getBotList())
        {
            if(getRec().intersects(b.getRec()))
            {
                alive = false;
            }
        }
    }
    
    public boolean getAlive()
    {
        return this.alive;
    }
    
    public void setAlive(boolean b)
    {
        this.alive = b;
    }
    
    //Stop player movement when entering settlement interface
    public void closePlayerMove()
    {
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
    }
    
    public void setKill(int i)
    {
        this.killsNum = i;
    }
    
    public int getKill()
    {
        return this.killsNum;
    }
    
    public void killedEnemy()
    {
        this.killsNum +=1;
    }
}
