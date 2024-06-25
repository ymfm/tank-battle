/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;
import java.awt.Graphics;
import java.sql.Timestamp;
import java.util.ArrayList;
/**The game manager will be used to manage the progress of the entire game
 *such as the number of enemies, whether the character is dead, whether it is 
 *a victory, and archives.
 * @author Duhang Jiang
 */
public class GameManager 
{
    private DrawGameMap dgm;
    private ArrayList<MapPoint> mp;
    private MapPoint playerPos;
    //hight and width for maze map
    private int x = 14;
    private int y = 14;
    //Select game mode by pressing the button
    private int gamemode = 2;
    private int logoMove = 160;
    //Used to determine whether to load the game or create a new game, the front means new, the negative means read
    private boolean newGameOrLoadGame = true;
    //Using the singleton pattern can ensure that there is only one object in operation
    //and other classes should operate on other classes through this object
    private static GameManager instance = new GameManager();
    private GameManager(){}
    public static GameManager getInstance(){return instance;}
    //get gamers
    private Player player;
    
    //Used to control the state of the game: in the menu or in the game.
    enum GameStatus
    {
        INGAME,INMENU,EXIT,SETTLE
    }
    
    private GameStatus gs = GameStatus.INMENU;
    
    //Pass the parameters for drawing the map into the map 
    //generator to start drawing the map
    public void drawMap()
    {   
        dgm = new DrawGameMap(x,y);
    }
    //Get each Position after the map is generated
    public ArrayList<MapPoint> getPos()
    {
        return dgm.getPos();
    }
    
    public int getGamemode()
    {
        return this.gamemode;
    }
    
    public void setGamemode(int x)
    {
        this.gamemode = x;
    }
    
    public void setGameStatus(GameStatus gs)
    {
        this.gs = gs;
    }
    
    public GameStatus getGameStatus()
    {
        return gs;
    }
    
    public int getMazeSizeX()
    {
        return this.dgm.getMazeSizeX();
    }
    
    public void setMazeSizeX(int x)
    {
        this.dgm.setMazeSizeX(x);
    }
    
    public int getMazeSizeY()
    {
        return this.dgm.getMazeSizeY();
    }
    
    public void setMazeSizeY(int y)
    {
        this.dgm.setMazeSizeY(y);
    }
    
    //To start a new game so draw a new map
    public void newMap()
    {
        drawMap();
        dgm.drawNewMaze();
        dgm.buildMaze();
        dgm.setWallList(dgm.drawGraphicsMap());
    }
    
    //Players need to print a new map every time they move
    public void printMap(Graphics g)
    {
        for(Wall wall:dgm.getWallList())
        {
            wall.drawObject(g);
        }
    }
    

    public void setLogoPosition(int pos)
    {
        this.logoMove = pos;
    }
    
    public int getLogoPosition()
    {
        return this.logoMove;
    }
    //Used to convert game mode to game state
    public GameStatus typeConverter(int i)
    {
        if(i == 1)
        {
            return GameStatus.INMENU;
        }
        else if(i == 2)
        {
            return GameStatus.INGAME;
        }
        else if(i == 3)
        {
            return GameStatus.EXIT;
        }
        return null; 
    }
    
    public void setPlayer(Player player)
    {
        this.player = player;
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
    
    public boolean getnewGameOrLoadGame()
    {
        return this.newGameOrLoadGame;
    }
    
    public void setNewGameOrLoadGame(boolean b)
    {
        this.newGameOrLoadGame = b;
    }
    
    public ArrayList<Wall> getWallList()
    {
        return dgm.getWallList();
    }
    
    public void setWallList(ArrayList<Wall> w)
    {
        this.dgm.setWallList(w);
    }
    public void setGameDate()
    {
        dbm.gameRecord();
    }
    
    //Database initialization
    private DBManager dbm = new DBManager();

    public void gameSave()
    {   
        dbm.saveGameMap();
        dbm.saveGameCharacter();
    }
    
    public void loadGame()
    {
        drawMap();
        dgm.setWallList(dbm.loadWALL());
        
        GamePanel.getInstance().setBotList(dbm.loadCHARACTER());
    }
    
    public ArrayList<Integer> loadHISTORY()
    {
        ArrayList<Integer> array = dbm.loadHISTORY();
        return array;
    }
}
