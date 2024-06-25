/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Duhang
 */
public class DBManager 
{
    private static final String URL = "jdbc:derby://localhost:1527/MazeGame" ;
    private static final String USER_Name = "Duhang";
    private static final String PASSWORD = "123";
    private Statement statement;
    ResultSet rs;
    Connection conn;
    
    public DBManager()
    {
        establelishConnection();
    }
    
    public void establelishConnection()
    {
        if(this.conn == null)
        {
            try
            {
                conn = DriverManager.getConnection(URL,USER_Name,PASSWORD);
                System.out.println("Connection Succeeded");
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    
     public void checkExistedTable(String name) 
     {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) 
            {
                String table_name = rs.getString("TABLE_NAME");
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name)) 
                {
                    statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }    
    }
     
    public void saveGameMap()
    {   
        try 
        {
          this.statement = conn.createStatement();
          //Delete the previous archive first to ensure the effect of overwriting
          this.statement.executeUpdate("DELETE FROM WALL");
        }           
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        
        int [] wallsPosX = new int[GameManager.getInstance().getWallList().size()];
        int [] wallsPosY = new int[GameManager.getInstance().getWallList().size()];
        try
        {       
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) 
        {
            System.out.println(ex.getMessage());
        }
        int i = 0;
        for(Wall w:GameManager.getInstance().getWallList())
        {
           wallsPosX[i] = w.getPosX();
           wallsPosY[i] = w.getPosY();
           i++;
        }

        for(int j = 0;j<wallsPosX.length;j++)
        {
            String posX = wallsPosX[j]+"" ;
            String posY = wallsPosY[j]+"" ;
            String ID = j+"";
            try
            {
                String sql = "INSERT INTO WALL VALUES ("+ID+","+posX+","+posY+")";
                this.statement.executeUpdate(sql);
            }
            catch (SQLException ex) 
            {
               System.out.println(ex.getMessage());
            }
       }
    }

    public void saveGameCharacter()
    {
        try 
        {
          this.statement = conn.createStatement();
          //same
          this.statement.executeUpdate("DELETE FROM CHARACTERS");
        }           
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        int [] botPosX = new int[GamePanel.getInstance().getBotList().size()];
        int [] botPosY = new int[GamePanel.getInstance().getBotList().size()];
        
        int x = GameManager.getInstance().getPlayer().getPosX();
        int y = GameManager.getInstance().getPlayer().getPosY();
        
        try
        {            
            String sql = "INSERT INTO CHARACTERS VALUES ("+999+","+x+","+y+")";
            this.statement = conn.createStatement();
            this.statement.executeUpdate(sql);
        }
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        int i = 0;
        for(Bot bot:GamePanel.getInstance().getBotList())
        {
            botPosX[i] = bot.getPosX();
            botPosY[i] = bot.getPosY();
            i++;
        }
                
        for(int j = 0;j<botPosY.length;j++)
        {
            String posX = botPosX[j]+"" ;
            String posY = botPosY[j]+"" ;
            String ID = j+"";
            try
            {
                String sql = "INSERT INTO CHARACTERS VALUES ("+ID+","+posX+","+posY+")";
                this.statement = conn.createStatement();
                this.statement.executeUpdate(sql);
            }
            catch (SQLException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    //Save the kill record during the customs clearance
    public void gameRecord()
    {
        int kill = GamePanel.getInstance().getPlayer().getKill();
        try
        {
            String sql = "INSERT INTO HISTORY VALUES ("+kill+")";
            this.statement = conn.createStatement();
            this.statement.executeUpdate(sql);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    
    //load game
    public ArrayList<Wall> loadWALL()
    {
        ArrayList<Wall> wall = new ArrayList<Wall>();
        try
        {
            String sql = "SELECT * FROM WALL";
            this.statement = conn.createStatement();
            rs = this.statement.executeQuery(sql);
            int i = 0;
            while(rs.next())
            {
                wall.add(new Wall(".\\src\\Image\\WALL.gif",rs.getInt("POSX"),rs.getInt("POSY"),GamePanel.getInstance().getGamePanel()));
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        } 
        
        return wall;
    }
     
    public ArrayList<Integer> loadHISTORY()
    {   
        ArrayList<Integer> array = new ArrayList<Integer>();
        try
        {            
            String sql = "SELECT * FROM HISTORY";
            this.statement = conn.createStatement();
            rs = this.statement.executeQuery(sql);
            while(rs.next())
            {
                array.add(rs.getInt("KILLNUM"));
            }
            
            Collections.sort(array,Collections.reverseOrder());
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        } 
        
        return array;
    }
    
    public ArrayList<Bot> loadCHARACTER()
    {
        ArrayList<Bot> b = new ArrayList<Bot>();
        try
        {
            String sql = "SELECT * FROM CHARACTERS";
            this.statement = conn.createStatement();
            rs = this.statement.executeQuery(sql);
            while(rs.next())
            {
                if(rs.getInt("CHARACTERID") == 999)
                {
                    GamePanel.getInstance().getPlayer().setPlayerPos(rs.getInt("POSX"),rs.getInt("POSX"));
                }
                else
                {            
                    b.add(new Bot(".\\src\\Image\\botD.gif",rs.getInt("POSX"),rs.getInt("POSX")
                    ,GamePanel.getInstance().getGamePanel(),
                    ".\\src\\Image\\botU.gif",".\\src\\Image\\botD.gif",
                    ".\\src\\Image\\botL.gif",".\\src\\Image\\botR.gif"));
                }
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        } 
        
        return b;
    }
}
