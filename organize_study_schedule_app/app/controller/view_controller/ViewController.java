package app.controller.view_controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import app.controller.read_data_controller.ReadDataController;
import app.model.read.ReadData;
import app.view.main.AppUI;
import app.view.splash.SplashBackgroundImg;
import app.view.splash.SplashScreen;

public class ViewController {
    public void splashScreen() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.add(SplashBackgroundImg.getImg());
        splashScreen.setVisible(true);
    }

    public void init() {
        ReadDataController.init();
        SwingUtilities.invokeLater(() -> {
            splashScreen();
            Timer timer = new Timer(2000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    AppUI appUI = new AppUI().init();
                    EventHandler eventHandler = new EventHandler(appUI);
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

    }
}
