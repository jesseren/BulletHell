import java.awt.*;

public class Trail extends GameObject {

    private float alpha = 1;
    private Handler handler;
    private Color color;

    private float width, height;
    private float life;

    public Trail(float x, float y, ID id, Color color, float width, float height, float life, Handler handler) {
        super(x, y, id);
        this.color = color;
        this.life = life;
        this.handler = handler;
        this.width = width;
        this.height = height;
    }

    public void tick() {
        if (alpha > life){
            alpha -= (life -0.01f);
        } else handler.removeObject(this);
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));
        g.setColor(color);
        g.fillRect((int) x,(int) y,(int) width,(int) height);
        g2d.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

    public Rectangle getBounds() {
        return null;
    }
}
