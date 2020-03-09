import java.util.Random;

public class Spawn {

    private Handler handler;
    private HUD hud;
    private Random r = new Random();
    private Game game;

    private int scoreKeep = 0;

    public Spawn(Handler handler, HUD hud, Game game) {
        {
            this.handler = handler;
            this.hud = hud;
            this.game = game;
        }
    }

    public void tick() {
        scoreKeep++;

        if(scoreKeep >= 200) {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);
            if(game.difficult == 0) {
                if (hud.getLevel() == 2) {
                    handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.BasicEnemy, handler));
                } else if (hud.getLevel() == 3) {
                    handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.BasicEnemy, handler));
                } else if (hud.getLevel() == 4) {
                    handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.FastEnemy, handler));
                } else if (hud.getLevel() == 5) {
                    handler.addObject(new SmartEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.SmartEnemy, handler));
                } else if (hud.getLevel() == 6) {
                    handler.clearEnemies();
                    handler.addObject(new EnemyBoss(Game.WIDTH / 2 - 32, -96, ID.EnemyBoss, handler));
                }
            } else if(game.difficult == 1){
                if (hud.getLevel() == 2) {
                    handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.BasicEnemy, handler));
                } else if (hud.getLevel() == 3) {
                    handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.BasicEnemy, handler));
                } else if (hud.getLevel() == 4) {
                    handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.FastEnemy, handler));
                } else if (hud.getLevel() == 5) {
                    handler.addObject(new SmartEnemy(r.nextInt((int) Game.WIDTH - 50), r.nextInt((int) Game.HEIGHT - 50), ID.SmartEnemy, handler));
                } else if (hud.getLevel() == 6) {
                    handler.clearEnemies();
                    handler.addObject(new EnemyBoss(Game.WIDTH / 2 - 32, -96, ID.EnemyBoss, handler));
                }
            }
        }
    }
}