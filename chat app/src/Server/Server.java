package Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ServerSocket server;
    private Socket connection;


    public  Server(){
        super("Server");
        userText= new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
        );

        add(userText, BorderLayout.SOUTH);
        chatWindow=new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(450,200);
        setVisible(true);

    }

    //setting the server to run
    public void startRunning(){
        try{
            server= new ServerSocket(1234,100);

            while(true){
                try{
                    waitForConnection();
                    setUpStreams();
                    whileChatting();
                }catch(EOFException eofException){
                    showMessage("\n The connection ended by the server");
                }finally {
                    endProcess();
                }

            }

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //wait for connection and then display connection information
    public void waitForConnection() throws IOException {
        showMessage(" Waiting for a connection... \n");
        connection=server.accept();
        showMessage("Now connected to "+ connection.getInetAddress().getHostName());
    }

    //setting up the streams to send and receive streams
    private void setUpStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\n The streams are connected successfully \n ");
    }

    //during the chat conversation
    public  void whileChatting() throws IOException {
        String message=" ";
        //sendMessage(message);
        setAbleToType(true);

        do {
            try{
                message= (String) input.readObject();

                String extn="";
                int flag=0,i;

                for(i=0;i<message.length();i++)
                {

                    if(message.charAt(i)=='.' || flag==1)
                    {
                        flag=1;
                        extn+=message.charAt(i);
                    }
                }
                if(extn.equals(".jpg")){
                    showMessage("\n picture received check (E:\\files from client) for the image");

                    File initialImage = new File(message);
                    BufferedImage image = ImageIO.read(initialImage);

                    ImageIO.write(image, "jpg", new File("E:\\files from client\\test.jpg"));


                }
                else if (extn.equals(".pdf")){
                    showMessage("\n pdf received (check E:\\files from client) for the pdf file");
                    File sourceFile = new File(message);
                    File destinationFile = new File("E:\\files from client\\Fourier.pdf");

                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            destinationFile);

                    int bufferSize;
                    byte[] bufffer = new byte[512];
                    while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                        fileOutputStream.write(bufffer, 0, bufferSize);
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                }
                else
                {showMessage("\n"+message);}
            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\nUnable to send message");
            }

        }while (!message.equals("CLIENT - END"));

    }

    //close the socket and connection after chatting
    private void endProcess(){
        showMessage("\n Closing the connection \n");
        setAbleToType(false);

        try{
            input.close();
            output.close();
            connection.close();

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    //send a message to the client
    private  void sendMessage(String message){
        try{
            String extn="";
            int flag=0,i;

            for(i=0;i<message.length();i++)
            {

                if(message.charAt(i)=='.' || flag==1)
                {
                    flag=1;
                    extn+=message.charAt(i);
                }
            }
            if(extn.equals(".jpg")){

                output.writeObject( message);
                output.flush();
                showMessage("\n Server - sent you an image" );
            }
            else if (extn.equals(".pdf")){
                output.writeObject(message);
                output.flush();
                showMessage("\n Client - sent you a pdf  " );
            }
            else{
            output.writeObject(" Server - " + message);
            output.flush();
            showMessage("\n Server - " + message);}
        }catch (IOException ioException){
            chatWindow.append("\n Sorry, Unable to send your Message \n");
        }
    }

    //updates the chat window to show message
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(text);
                    }
                }
        );
    }

    //let the user type the message
    private  void setAbleToType(final  boolean tof){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }
}