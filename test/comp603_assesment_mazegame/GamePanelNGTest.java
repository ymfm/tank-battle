/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testng.annotations.Test;

/**
 *
 * @author 28278
 */
public class GamePanelNGTest {
    
    /**
     * Test of setPromptTime method, of class GamePanel.
     */
    @Test
    public void testSetPromptTime() {
        System.out.println("setPromptTime");
        int i = 0;
        GamePanel instance = GamePanel.getInstance().getGamePanel();
        instance.setPromptTime(i);
        assertEquals(0,instance.getPrompTime());
    }

    /**
     * Test of getPrompTime method, of class GamePanel.
     */
    @Test
    public void testGetPrompTime() 
    {
        System.out.println("setPromptTime");
        int i = 0;
        GamePanel instance = GamePanel.getInstance().getGamePanel();
        instance.setPromptTime(100);
        assertEquals(100,instance.getPrompTime());
    }
    
}
