/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import comp603_assesment_mazegame.GameManager.GameStatus;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Duahng Jiang
 */
public class COMP603_Assesment_RPGgame 
{

    public static void main(String[] args)
    {        
        GamePanel.getInstance().drawPanel();
        GameManager.getInstance().setGameStatus(GameStatus.INMENU);
    }
}
