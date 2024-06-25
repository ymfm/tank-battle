/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import comp603_assesment_mazegame.GameManager.GameStatus;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *This class is mainly responsible for controlling the movement and attack of the character
 * @author Duhang Jiang
 */
    public class KeyboardEventListenner extends KeyAdapter
{
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(GameManager.getInstance().getGameStatus() == GameManager.GameStatus.INMENU)
        {
            inMenu(e);
        }
        else if(GameManager.getInstance().getGameStatus() == GameManager.GameStatus.INGAME)
        {
            //When you enter the game you will use other keys to play it
            inGame(true,e);
        }
        else if(GameManager.getInstance().getGameStatus() == GameManager.GameStatus.SETTLE)
        {
            //After winning the game, choose to continue the game and exit the game
            inSettle(e);
        }
    }
    
    public void inMenu(KeyEvent e)
    {
        //Make selections in menus with numeric keys
        if(e.getKeyCode() == KeyEvent.VK_1)
        {
            GamePanel.getInstance().getWinngCheck().setWin(false);
            GameManager.getInstance().setGamemode(2);
            GameManager.getInstance().setLogoPosition(160);
            GameManager.getInstance().setNewGameOrLoadGame(true);
        }
        else if(e.getKeyCode() == KeyEvent.VK_2)
        {
            GamePanel.getInstance().getWinngCheck().setWin(false);
            GameManager.getInstance().setGamemode(2);
            GameManager.getInstance().setLogoPosition(260);
            GameManager.getInstance().setNewGameOrLoadGame(false);
        }
        else if(e.getKeyCode() == KeyEvent.VK_3)
        {
            GameManager.getInstance().setGamemode(3);
            GameManager.getInstance().setLogoPosition(360);

        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            GameManager.getInstance().setGameStatus(GameManager.getInstance().typeConverter(GameManager.getInstance().getGamemode()));
            if(GameManager.getInstance().getGameStatus() == GameStatus.INGAME)
            {
                if(GameManager.getInstance().getnewGameOrLoadGame())
                {
                    GameManager.getInstance().setPlayer(GamePanel.getInstance().getPlayer());
                    GameManager.getInstance().newMap();
                }
                else
                {
                    GameManager.getInstance().setPlayer(GamePanel.getInstance().getPlayer());
                    GameManager.getInstance().loadGame();
                }
            }
            else if(GameManager.getInstance().getGameStatus() == GameStatus.EXIT)
            {
                System.exit(0);
            }
        }
        else
        {
            GamePanel.getInstance().setPromptTime(60);
        }
    }
    
    public void keyReleased(KeyEvent e)
    {
        if(GameManager.getInstance().getGameStatus() == GameManager.GameStatus.INGAME)
        {
            inGame(false,e);
        }
    }

    public void inGame(boolean b, KeyEvent e)
    {   
        
        if(b == true)
        {
            GameManager.getInstance().getPlayer().keyPressed(e);
        }
        else if(e.getKeyCode() == KeyEvent.VK_M)
        {
            GameManager.getInstance().gameSave();
        }
        else
        {
            GameManager.getInstance().getPlayer().keyReleased(e);
        }    
    }
    
    public void inSettle(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_1)
        {
            GameManager.getInstance().setGamemode(1);
            GameManager.getInstance().setLogoPosition(160);
        }
        else if(e.getKeyCode() == KeyEvent.VK_3)
        {
            GameManager.getInstance().setGamemode(3);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {          

            GameManager.getInstance().setGameStatus(GameManager.getInstance().typeConverter(GameManager.getInstance().getGamemode()));
            
            if(GameManager.getInstance().getGameStatus() == GameStatus.INMENU)
            {
                //After the active game is settled, the player will stay at the
                //victory point and cannot play the second game, so all the
                //values of the player need to be reset once here.
                GameManager.getInstance().getPlayer().setPlayerPos(70, 70);
                GameManager.getInstance().getPlayer().setAlive(true);
                GameManager.getInstance().getPlayer().closePlayerMove();
                GameManager.getInstance().getPlayer().setKill(0);
            }
            else if(GameManager.getInstance().getGameStatus() == GameStatus.EXIT)
            {
                System.exit(0);
            }
        }
        else
        {
            GamePanel.getInstance().setPromptTime(60);
        }
    }
}
