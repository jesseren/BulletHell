import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Menu extends MouseAdapter {

    Game game;
    Handler handler;
    private Random r = new Random();
    private HUD hud;
    private File soundFile;
    private Clip clip;

    public Menu(Game game, Handler handler, HUD hud) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        soundFile = new File("menu.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioIn);
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        //play button
        if (game.gameState == Game.STATE.Menu && mouseOver(mx, my, 210, 150, 200, 64)){
            game.gameState = Game.STATE.Select;
            //help button
        } else if (game.gameState == Game.STATE.Menu && mouseOver(mx, my, 210, 250, 200, 64)){
            game.gameState = Game.STATE.Help;
        //quit button
        } else if (game.gameState == Game.STATE.Menu && mouseOver(mx, my, 210, 350, 200, 64)){
            System.exit(1);
        } else if (game.gameState == Game.STATE.Help && mouseOver(mx, my, 210, 350, 200, 64)) {
            game.gameState = Game.STATE.Menu;
        } else if (game.gameState == Game.STATE.End && mouseOver(mx, my, 210, 350, 200, 64)) {
            hud.setScore(0);
            hud.setLevel(1);
            game.gameState = Game.STATE.Menu;
        } else if (game.gameState == Game.STATE.Select && mouseOver(mx, my, 210, 150, 200, 64)){
            game.difficult = 0;
            if(clip.isRunning()) clip.stop();
            handler.clearEnemies();
            game.gameState = Game.STATE.Game;
            try {
                handler.addObject(new Player(game.WIDTH/2-32, game.HEIGHT/2-32, ID.Player, handler));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        } else if (game.gameState == Game.STATE.Select && mouseOver(mx, my, 210, 250, 200, 64)){
            game.difficult = 1;
            if(clip.isRunning()) clip.stop();
            handler.clearEnemies();
            game.gameState = Game.STATE.Game;
            try {
                handler.addObject(new Player(game.WIDTH/2-32, game.HEIGHT/2-32, ID.Player, handler));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        } else if (game.gameState == Game.STATE.Select && mouseOver(mx, my, 210, 350, 200, 64)) {
            game.gameState = Game.STATE.Menu;
        }
    }

    public void mouseReleased(MouseEvent e){

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if (mx > x && mx < x + width && my > y && my < y + height) {
            return true;
        } else return false;
    }
    public void tick(){
        if(!clip.isRunning()){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void render(Graphics g){
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", 1, 50);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Menu", 240, 70);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 260, 200);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 260, 300);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 260, 400);
        } else if (game.gameState == Game.STATE.Help){
            g.setColor(Color.white);
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 20);
            g.setFont(fnt);
            g.drawString("Help", 260, 100);
            g.setFont(fnt2);
            g.drawString("Use WASD keys to move player and dodge enemies",50,200);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 285, 390);
        } else if (game.gameState == Game.STATE.End){
            g.setColor(Color.white);
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 20);
            g.setFont(fnt);
            g.drawString("Game Over", 260, 100);
            g.setFont(fnt2);
            g.drawString("You lost with a score of: " + hud.getScore(),50,200);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 270, 390);
        } else if (game.gameState == Game.STATE.Select) {
            Font fnt = new Font("arial", 1, 50);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("SELECT DIFFICULTY", 100, 70);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Normal", 230, 200);
            g.drawRect(210, 250, 200, 64);
            g.drawString("Hard", 255, 300);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 250, 400);
        }
    }
}
