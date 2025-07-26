package tt;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ImageSlider extends JPanel {
    java.util.List<Image> imgs = new ArrayList<>();
    int curr = 0, from = 0, to = 0, frame = 0;
    Timer anim, auto;
    int startX = 0;

    public ImageSlider() {
        for (int i = 1; i <= 5; i++)
            imgs.add(new ImageIcon("./datafiles/main/" + i + ".png").getImage());

        auto = new Timer(1000, e -> slide(1));
        auto.start();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { startX = e.getX(); }
            public void mouseReleased(MouseEvent e) {
                int dx = e.getX() - startX;
                if (Math.abs(dx) > 50 && anim == null)
                    slide(dx < 0 ? 1 : -1);
            }
        });
    }

    void slide(int dir) {
        if (anim != null) return;
        from = curr;
        to = (curr + dir + imgs.size()) % imgs.size();
        frame = 0;

        anim = new Timer(15, e -> {
            frame++;
            if (frame >= 20) {
                curr = to;
                anim.stop();
                anim = null;
            }
            repaint();
        });
        anim.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();
        double t = anim == null ? 1 : frame / 20.0;

        int dir = (to == (from + 1) % imgs.size()) ? 1 : -1;  // ← 수정됨
        int x = (int)(-w * t * dir);  // ← 방향대로 이동

        g.drawImage(imgs.get(from), x, 0, w, h, this);
        g.drawImage(imgs.get(to), x + w * dir, 0, w, h, this);
    }

    public static void main(String[] a) {
        JFrame f = new JFrame();
        f.add(new ImageSlider());
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
