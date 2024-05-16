package app;

import app.controller.view_controller.ViewController;

public class App {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ViewController runApp = new ViewController();
        runApp.init();
    }
}
