import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Server  implements ActionListener{

    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame j=new JFrame();
    static DataOutputStream dot;
    public Server() {
        j.setLayout(null);
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        j.add(p1);
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon((i2));
        JLabel back =new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon((i5));
        JLabel profile =new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon((i8));
        JLabel video =new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon((i11));
        JLabel phone =new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon((i14));
        JLabel icon3 =new JLabel(i15);
        icon3.setBounds(420,20,10,25);
        p1.add(icon3);

        JLabel name=new JLabel("Person 1");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status=new JLabel("Active");
        status.setBounds(110,35,100,14);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);

        // middle section
        a1=new JPanel();
//        JScrollBar sb=new JScrollBar(JScrollBar.VERTICAL);
//        sb.setBounds(420,75,8,570);
//        a1.add(sb);
        a1.setBounds(5,75,440,570);
        j.add(a1);
        //end section

        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        j.add(text);

        JButton send=new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.addActionListener(this::actionPerformed);
        j.add(send);
        j.setSize(450,700);
        j.setLocation(200,50);
        j.setUndecorated(true);
        j.getContentPane().setBackground(Color.white);

        j.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            String out = text.getText();
            JPanel p2 = formateLble(out);
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dot.writeUTF(out);
            text.setText("");
            j.repaint();
            j.invalidate();
            j.validate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formateLble(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output=new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public void run(){
        try{
            ServerSocket skt=new ServerSocket(6001);
            while (true){
                Socket s= skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                dot=new DataOutputStream(s.getOutputStream());
                while (true){
                    String msg= din.readUTF();
                    JPanel panel=formateLble(msg);

                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    j.validate();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s= new Server();
        s.run();
    }

}
