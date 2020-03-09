import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1550691097823471818L;

    public static final float WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public static boolean pause = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawn spawn;
    private Menu menu;
    public int difficult = 0;

    public enum STATE {
        Menu,
        Help,
        End,
        Select,
        Game;
    }

    public STATE gameState = STATE.Menu;

    public Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(menu);

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        spawn = new Spawn(handler, hud, this);
        r = new Random();

        if (gameState == STATE.Menu) {
            for (int i = 0; i < 6; i++) {
                handler.addObject(new MenuParticle(r.nextInt((int) WIDTH), r.nextInt((int) HEIGHT), ID.MenuParticle, handler));
            }
        }
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(){
        this.setFocusable(true);
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        float frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer+=1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        if (gameState == STATE.Game && !pause) {
            handler.tick();
            hud.tick();
            spawn.tick();
            if(HUD.HEALTH <= 0){
                handler.clearEnemies();
                gameState = STATE.End;
                HUD.HEALTH = 100;
            }
        } else if (gameState==STATE.Menu || gameState==STATE.End || gameState==STATE.Select){
            handler.tick();
            menu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,(int) WIDTH,(int) HEIGHT);
        handler.render(g);
        if(pause){
            g.setColor(Color.white);
            g.drawString("PAUSED",100,100);
        }
        if (gameState == STATE.Game) {
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState==STATE.End || gameState==STATE.Select) {
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max){
        if(var >= max) return var = max;
        else if(var <= min) return var = min;
        else return var;
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Game();
    }
}
