/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp603_assesment_mazegame;
import comp603_assesment_mazegame.GameManager.GameStatus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Duhang Jiang
 */
public class GamePanel extends JFrame
{
    private int width = 910;
    private int height = 910;
    private Image logo = Toolkit.getDefaultToolkit().getImage(".\\src\\Image\\Logo.gif");
    private int logoPosY = 150;
    
    //In order to solve the problem that the game interface keeps flashing
    private Image offScreemIamge = null;
    //All pictures are drawn on this picture
    private Graphics gImage;
    //PlayerPos
    private int x,y;
    //player
    private Player player = new Player(".\\src\\Image\\playerU.gif",70,70,this,
                    ".\\src\\Image\\playerU.gif",".\\src\\Image\\playerD.gif",
                    ".\\src\\Image\\playerl.gif",".\\src\\Image\\playerR.gif");;
    
    private WinngCheck wc = new WinngCheck(".\\src\\Image\\star.gif",850,850,this);;
  
    //botlist
    private ArrayList<Bot> botList = new ArrayList<>();
    //List of bullets to remove
    private ArrayList<Bullet> removeList = new ArrayList<>();
    //number of bot tank
    int enemy = 0;
    //bot tank speed
    int countSpeed = 0;
    //bullet list
    public ArrayList<Bullet> bulletList = new ArrayList<>();
    
    //提示用户输入错误的ui的显示时间
    private int promptTime = 0;
    
    private static GamePanel instance = new GamePanel();
    private GamePanel(){}
    public static GamePanel getInstance(){return instance;}
    
    public GamePanel getGamePanel()
    {
        return this;
    }
    
    public Graphics getgImage()
    {
        return this.gImage;
    }
    
    public void drawPanel()
    {
        setTitle("maze escape");
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setResizable(false);
        setVisible(true);
        this.addKeyListener(new KeyboardEventListenner());
        
        while(true)
        {
            //Redraw the interface every frame
            repaint();
            try
            {
                Thread.sleep(25);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
 
    @Override
    public void paint(Graphics g)
    {
        //Create an Image of the same size as the panel
        if(offScreemIamge == null)
        {
            offScreemIamge = this.createImage(width,height);
        }
        gImage = offScreemIamge.getGraphics();
        //change scence color
        //by a square that fills the screen
        gImage.setColor(Color.WHITE);
        gImage.fillRect(0,0,width,height);
       
        //Star scenc
        gImage.setColor(Color.BLACK);
        gImage.setFont(new Font("Dialog",Font.BOLD,30));
        
        gameStatus();

        g.drawImage(offScreemIamge, 0, 0, null);
    }
    
    public void gameStatus()
    {
        if(GameManager.getInstance().getGameStatus() == GameStatus.INMENU)
        {
            gImage.drawString("Press 1 to star new game",260,200);
            gImage.drawString("Press 2 to load old game",260,300);
            gImage.drawString("Press 3 to close the game",260,400);
            gImage.drawString("Press enter to confirm selection",220,840);
            
            
            gImage.setFont(new Font("Dialog",Font.BOLD,15));
            gImage.drawString("Player Operation Manual:",260,470);
            gImage.drawString("Press:W/A/S/D to move",370,500);
            gImage.drawString("Press Space to shoot",370,530);
            gImage.drawString("Press M to save the game",370,560);
            gImage.drawString("Press 3 to exit the game",370,590);
                    
            //select game modle
            logoPosY = GameManager.getInstance().getLogoPosition();
            gImage.drawImage(logo, 150, logoPosY,null);
            wc.setWin(false);
            
            //如果提示时间大于0就显示提示，并且让提示时间减少
            gImage.setColor(Color.red);
            if(this.promptTime != 0)
            {
                 gImage.setFont(new Font("Dialog",Font.BOLD,30));
                gImage.drawString("Please press correct key",350,700);
                promptTime --;
            }
        }
        else if(GameManager.getInstance().getGameStatus() == GameStatus.INGAME)
        {            
            gImage.setColor(Color.gray);
            gImage.fillRect(0,0,width,height);

            //Generate player
            if(GameManager.getInstance().getPlayer().getAlive() == true)
            {
                GameManager.getInstance().getPlayer().drawObject(gImage);
            }
            else
            {
                GameManager.getInstance().setGameStatus(GameStatus.SETTLE);
            }
            //only draw if haven't won
            //victory point
            if(wc.getWin() == false)
            {
                wc.drawObject(gImage);
            }

            //bot tank
            for(Bot bot:botList)
            {
                bot.drawObject(gImage);
            }
            countSpeed++;
            if(countSpeed%10 == 1&&botList.size() <10)
            {
                botCreate();
            }
            //wall
            GameManager.getInstance().printMap(gImage);
            for(Bullet bullet: bulletList)
            {
                bullet.drawObject(gImage);
            }
            bulletList.removeAll(removeList);
            
            gImage.setFont(new Font("Dialog",Font.BOLD,45));
            gImage.setColor(Color.red);
            gImage.drawString("Kill: "+ player.getKill(),750,90);
        }
        else if(GameManager.getInstance().getGameStatus() == GameStatus.SETTLE)
        {
           gImage.setFont(new Font("Dialog",Font.BOLD,30));
            botList.removeAll(botList);
            bulletList.removeAll(botList);
            gImage.setFont(new Font("Dialog",Font.BOLD,30));
            if(player.getAlive() == false)
            {
                gImage.drawString("YOU LOSE THE GAME",290,200);
            }
            else if(player.getAlive() == true)
            {
                gImage.drawString("YOU WIN THE GAME",290,200);
            }
            gImage.drawString("Press 1 back to main menu",260,300);
            gImage.drawString("Press 3 to close the game",260,400);
            
            gImage.setColor(Color.yellow);
            gImage.drawString("Highest kill:",160,550);
            gImage.drawString(GameManager.getInstance().loadHISTORY().get(0)+"",220,580);
            gImage.setColor(Color.GRAY);
            gImage.drawString("Second kill:",300,600);
            gImage.drawString(GameManager.getInstance().loadHISTORY().get(1)+"",340,650);
            gImage.setColor(Color.blue);
            gImage.drawString("Third kill:",440,650);
            gImage.drawString(GameManager.getInstance().loadHISTORY().get(2)+"",500,700);
            
            gImage.setColor(Color.BLACK);
            gImage.drawString("Press enter to confirm selection",220,840);
            
            gImage.setColor(Color.red);
            //如果提示时间大于0就显示提示，并且让提示时间减少
            if(this.promptTime != 0)
            {
                gImage.drawString("Please press correct key",290,760);
                promptTime --;
            }
        }
    }
    
    public int getWidth()
    {
        return this.width;    
    }
    
    public int getHeight()
    {
        return this.height;    
    }
    
    //Control where the obstacles are generated, the bot tank is stuck in the wall
    public void botCreate()
    {
        Random random = new Random();       
            
        Bot b = new Bot(".\\src\\Image\\botD.gif",random.nextInt(width),random.nextInt(height)
            ,this,".\\src\\Image\\botU.gif",".\\src\\Image\\botD.gif",
            ".\\src\\Image\\botL.gif",".\\src\\Image\\botR.gif");
        
        ArrayList<Wall> walls = GameManager.getInstance().getWallList();
        botList.add(b);
        for(Wall w:walls)
        {
            if(w.getRec().intersects(b.getRec()))
            {
                botList.remove(b);
                enemy = botList.size(); 
            }
        } 
    }
    
    public WinngCheck getWinngCheck()
    {
        return this.wc;
    }
    
    public ArrayList<Bot> getBotList()
    {
        return this.botList;
    }
    
    public ArrayList<Bullet> getRemoveList()
    {
        return this.removeList;
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
    
    public void setBotList(ArrayList<Bot> b)
    {
        this.botList = b;
    }
    
    public void setPromptTime(int i)
    {
        this.promptTime = i;
    }
    
    public int getPrompTime()
    {
        return this.promptTime;
    }
}
