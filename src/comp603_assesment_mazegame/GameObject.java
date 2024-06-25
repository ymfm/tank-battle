/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 *This abstract class used to facilitate
 *the generation of all characters in the maze
 * 
 * @author Duhang Jiang
 */
public abstract class GameObject 
{
    //character image
    protected Image image;
    
    //character position
    protected int posX,posY;
    
    //senes 
    protected GamePanel gp;
    
    public GameObject(String image,int x,int y,GamePanel gp)
    {
        this.image = Toolkit.getDefaultToolkit().getImage(image);
        this.posX = x;
        this.posY = y;
        this.gp = gp;
    }
    
    public abstract void drawObject(Graphics g);
    public abstract Rectangle getRec(); 
}
