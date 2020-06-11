package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class log extends JFrame implements ActionListener {

    Container container = getContentPane();
    JPanel mine = new JPanel();
    JPanel mine2 = new JPanel();
    JPanel mine3 = new JPanel();
    JPanel mine4 = new JPanel();
    JPanel mine5 = new JPanel();

    private final Map<String, ImageIcon> imageMap;

    Font font = new Font("helvitica", Font.BOLD, 24);

    Icon icon = new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\back.png");
    ImageIcon img = new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\group.png");
    Icon user = new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png");

    Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
    Border border2= BorderFactory.createLineBorder(Color.BLACK,1);

    JLabel people= new JLabel("Friends List");
    JLabel peop= new JLabel("");
    JLabel mes = new JLabel("Select a friend to chat with");
    JButton button1 = new JButton(icon);
    private JTextField userText;
    //JTextField textField = new JTextField();
    JButton button = new JButton("\uD83D\uDE00");
    JButton file = new JButton("FILE");
    String[] nameList = {"yaa","kojo","nii","Koopa","Prince","yaw","kofi","ama"};

    JList list = new JList(nameList);

    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message="";
    private String serverIP;
    private Socket connection;

    public log() {


        super("Main");

        serverIP = "127.0.0.1";
        userText = new JTextField();
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


        chatWindow = new JTextArea();


        imageMap = createImageMap(nameList);


        list.setCellRenderer(new MarioListRenderer());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        list.setBackground(Color.white);

        JScrollPane scrollpane1 = new JScrollPane();
        scrollpane1.setViewportView(chatWindow);
        scrollpane1.setPreferredSize(new Dimension(593,445));




        list.isSelectedIndex(0);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() >= 1){
                    JList target =(JList)e.getSource();
                    int index =target.locationToIndex(e.getPoint());
                    if(index >=0){
                        Object item = target.getModel().getElementAt(index);
                        peop.setText(item.toString());
                        peop.setIcon(user);
                        mine2.remove(mes);
                        //JOptionPane.showMessageDialog(null, item.toString());
                    }
                }
            }
        });


        button1.setBounds(5,3,30,35);
        button1.setBackground(Color.blue);


        people.setBounds(100,10,200,50);
        people.setIcon(img);
        people.setFont(font);



        peop.setBounds(550,10,550,50);
        peop.setFont(font);

        button.setBounds(500,5,80,40);
        userText.setBounds(98,5,300,40);
        file.setBounds(10,5,60,40);


        setBounds(10,10,1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mine.setBounds(0, 100, 400, 500);
        mine.setBackground(Color.white);
         mine2.setBounds(402,100,598,450);
         mine2.setBackground(Color.white);
         mine3.setBounds(0,40,400,60);
         mine3.setBackground(Color.white);
         mine3.setBorder(border);
         mine4.setBounds(402,40,598,60);
         mine4.setBorder(border);
         mine4.setBackground(Color.white);
         mine5.setBounds(402,550,598,50);
         mine5.setBackground(Color.white);
         mine5.setBorder(border);
         mine5.setLayout(null);

        container.setBackground(Color.blue);
        container.setLayout(null);









        mine.add(scrollPane);
        mine4.add(peop);
        mine2.add(scrollpane1);
        mine3.add(people);
        mine5.add(button);
        mine5.add(file);
        mine5.add(userText);

        container.add(button1);
        container.add(mine);
        container.add(mine2);
        container.add(mine3);
        container.add(mine4);
        container.add(mine5);


        button.addActionListener(this);
        file.addActionListener(this);

    }

    //setting up the server
    public void startRunning(){
        try{
            connectToServer();
            setUpStreams();
            whileChatting();
        }catch (EOFException eofException){
            showMessage("\n Client disconnected the connection");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }finally {
            closeConnnection();
        }
    }

    private void connectToServer() throws IOException {
        //showMessage("\n Attempting to connect... \n ");
        connection = new Socket(InetAddress.getByName(serverIP),1234);
        //showMessage("\n Connected to "+ connection.getInetAddress().getHostName());
        //showMessage("\n Connected to server");

        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:chatapp.db");
            Statement stmt = con.createStatement();
            ResultSet ps = stmt.executeQuery("select * from MESS ");
            while(ps.next()){
            String mes =ps.getString("message");
            chatWindow.append(mes);}
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        chatWindow.append("\nABOVE ARE OLD MESSAGES\n");
    }

    //setting up to send and receive messages
    private void setUpStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        //showMessage("\n You can now send messages \n");
    }

    //while chatting with server
    private  void whileChatting() throws IOException {
        ableToType(true);
        do {
            try{
                message = (String) input.readObject();

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
                    showMessage("\n picture received check E:\\files from server");

                    File initialImage = new File(message);
                    BufferedImage image = ImageIO.read(initialImage);

                    ImageIO.write(image, "jpg", new File("E:\\files from server\\received_image.jpg"));
                    Toolkit.getDefaultToolkit().beep();

                }
                else if (extn.equals(".pdf")){
                    showMessage("\n pdf received check E:\\files from server");
                    File sourceFile = new File(message);
                    File destinationFile = new File("E:\\files from server\\received_pdf.pdf");

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
                    Toolkit.getDefaultToolkit().beep();
                }
                else
                {
                    showMessage("\n"+message);
                    Toolkit.getDefaultToolkit().beep();
                }
            }catch (ClassNotFoundException classNotFoundException){
                showMessage("\n message isn't recognisable \n");
            }

        }while (!message.equals("SERVER - END"));
    }


    //close the streams and sockets
    private  void closeConnnection(){
        showMessage("\n Closing the chat \n");
        ableToType(false);
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException ioExceptiom){
            ioExceptiom.printStackTrace();
        }
    }

    //sending messages
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

                output.writeObject(message);
                output.flush();
                showMessage("\n Client - sent you an image" );



            }
            else if (extn.equals(".pdf")){
                output.writeObject(message);
                output.flush();
                showMessage("\n Client - sent you a pdf  " );
            }
            else{
                output.writeObject("CLIENT - "+message);
                output.flush();
                showMessage("\n CLIENT - "+ message);}
        }catch (IOException ioException){
            chatWindow.append("\n Something messed up\n");
        }
    }

    //change or update the chatwindow area
    private void showMessage(final String message) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append(message);
                    }
                }
        );
        try{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:chatapp.db");
        PreparedStatement ps = con.prepareStatement("insert into MESS values(?)");
        ps.setString(1, message);
        ps.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //gives user permission to type
    private  void ableToType(boolean tof){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            sendMessage("\uD83D\uDE00");
        }
        else if(e.getSource()==file){
            JFileChooser fc= new JFileChooser(FileSystemView.getFileSystemView());

            //fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files","*.pdf"));
            fc.showOpenDialog(null);
            String name = fc.getSelectedFile().toString();
            sendMessage(name);
        }
    }


    public class MarioListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 24);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {


            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;

        }
    }

    private Map<String, ImageIcon> createImageMap(String[] list) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
            map.put("yaa", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("kojo", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("nii", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("Koopa", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("Prince", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("yaw", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("kofi", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
            map.put("ama", new ImageIcon("C:\\Users\\Sherif\\Desktop\\pics\\cust.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        log mlog = new log();
        mlog.setVisible(true);
       mlog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mlog.startRunning();
    }


}
