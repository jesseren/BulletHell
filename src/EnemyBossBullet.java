import java.awt.*;
import java.util.Random;

public class EnemyBossBullet extends GameObject {

    private Handler handler;
    Random r = new Random();

    public EnemyBossBullet(float x, float y, ID id, Handler handler){
        super(x,y,id);

        this.handler = handler;

        if(r.nextBoolean()) velX = r.nextInt(5);
        else velX = -r.nextInt(5);
        velY = 5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x,(int) y,16,16);
    }

    public void tick(){
        x += velX;
        y += velY;


        if(y >= Game.HEIGHT) handler.removeObject(this);

        handler.addObject(new Trail(x,y,ID.Trail,Color.white,16,16,0.1f,handler));
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.fillRect((int) x,(int) y,16,16);
    }
}
