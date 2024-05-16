package app.view.splash;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SplashBackgroundImg {
    public static JPanel getImg() {
        JPanel jpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // TODO Auto-generated method stub
                super.paintComponent(g);

                // load hình ảnh từ resource
                ImageIcon image = new ImageIcon(
                        (getClass().getResource("/resource/img/background_splash_screen.png")));
                Image background = image.getImage();

                // vẽ hình ảnh làm nề
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        return jpanel;
    }
}
