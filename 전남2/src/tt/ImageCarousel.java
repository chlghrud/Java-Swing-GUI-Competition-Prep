package tt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ImageCarousel extends JPanel {
    java.util.List<Image> imgs = new ArrayList<>();
    int idx, off, dir, sx;
    Timer anim = new Timer(10, e -> {
        if(dir==0) {
            if(off>0) off = Math.max(0, off-20);
            else         off = Math.min(0, off+20);
            
            if(off==0) ((Timer)e.getSource()).stop();
        } else {
            off += dir*20;
            if(Math.abs(off)>=getWidth()) {
                idx = (idx - dir + imgs.size()) % imgs.size();
                off = 0; ((Timer)e.getSource()).stop();
            }
        }
        repaint();
    });
    Timer autoPlay = new Timer(1000, e -> { if(!anim.isRunning()) slide(-1); });

    public ImageCarousel(String[] paths) throws Exception {
        for(String p: paths) imgs.add(ImageIO.read(new File(p)));
        setPreferredSize(new Dimension(800,600));
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){ sx = e.getX(); if(anim.isRunning()) anim.stop(); }
            public void mouseReleased(MouseEvent e){
                int d = e.getX() - sx;
                slide(Math.abs(d)>50 ? (d>0?1:-1) : 0);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                off = e.getX() - sx;
                off = Math.max(-getWidth(), Math.min(off, getWidth()));
                repaint();
            }
        });
        autoPlay.start();
    }

    void slide(int d) {
        dir = d; anim.restart();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth(), h = getHeight();
        // 현재
        g.drawImage(imgs.get(idx), off, 0, w, h, this);
        // 전환 이미지
        if(dir!=0 || off!=0) {
            int adj = (dir==0 ? (off<0?1:-1) : -dir);
            int oi  = (idx + adj + imgs.size()) % imgs.size();
            int x   = off - (dir==0 ? adj*w : dir*w);
            g.drawImage(imgs.get(oi), x, 0, w, h, this);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] ps = {
                "./datafiles/main/1.png", "./datafiles/main/2.png",
                "./datafiles/main/3.png", "./datafiles/main/4.png",
                "./datafiles/main/5.png"
            };
        JFrame f = new JFrame("ShortCarousel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ImageCarousel(ps));
        f.pack(); f.setLocationRelativeTo(null); f.setVisible(true);
    }
}
