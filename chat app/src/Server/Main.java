package Server;

import javax.swing.*;


public class Main {
    public static void main(String args[]){
        Server serverObject = new Server();
        serverObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverObject.startRunning();
    }
}
