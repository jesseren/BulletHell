import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends GameObject{

    Handler handler;
    private File soundFile;
    private Clip clip;

    public Player(float x, float y, ID id, Handler handler) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        super(x,y,id);
        this.handler = handler;
        soundFile = new File("Collision.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioIn);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y,32,32);
    }

    public void tick(){
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH-44);
        y = Game.clamp(y, 0, Game.HEIGHT-66);

        handler.addObject(new Trail(x,y,ID.Trail,Color.white,32,32,(float) 0.1,handler));
        collision();
    }

    private void collision(){
        for(int i = 0; i < handler.object.size();i++){

            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.EnemyBoss){
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH -=2;
                    clip.start();
                    clip.setFramePosition(0);
                }
            }
        }
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.fillRect((int) x,(int) y,32,32);
    }
}

