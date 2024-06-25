/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author 28278
 */
public class PlayerNGTest {

//    @Test
    public void testKeyPressed() 
    {
        System.out.println("keyPressed");
        KeyEvent e = null;
        Player instance = null;
        instance.keyPressed(e);
    }

    @Test
    public void testSetKill() {
        System.out.println("setKill");
        int i = 10;
        Player instance = new Player("",0,0,GamePanel.getInstance().getGamePanel(),"","","","");;
        instance.setKill(10);
        assertEquals(i,instance.getKill());
    }

    /**
     * Test of getKill method, of class Player.
     */
    @Test
    public void testGetKill() {
        System.out.println("getKill");
        Player instance = new Player("",0,0,GamePanel.getInstance().getGamePanel(),"","","","");
        int expResult = instance.getKill();
        int result = instance.getKill();
        assertEquals(result, expResult);
    }

    /**
     * Test of killedEnemy method, of class Player.
     */
    @Test
    public void testKilledEnemy() {
        System.out.println("killedEnemy");
        Player instance = new Player("",0,0,GamePanel.getInstance().getGamePanel(),"","","","");
        instance.killedEnemy();
        assertEquals(1,instance.getKill());
    }
    
}
