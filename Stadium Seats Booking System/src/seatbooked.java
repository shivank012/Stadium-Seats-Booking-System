import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class seatbooked extends JFrame {

    JLabel lblthank,lblniceday;
    JButton btnLogout;

    public seatbooked(){

        setTitle("Thank You");
        getContentPane().setBackground(Color.PINK);

        lblthank = new JLabel("THANK YOU FOR BOOKING!!");
        lblthank.setBounds(20,40,300,50);
        lblthank.setFont(new Font("Comic Sans MS",Font.BOLD,20));

        lblniceday = new JLabel("HAVE A NICE DAY :)");
        lblniceday.setBounds(20,80,300,50);
        lblniceday.setFont(new Font("Comic Sans MS",Font.BOLD,20));

        btnLogout = new JButton("LOGOUT");
        btnLogout.setBounds(20,140,100,30);

        add(lblthank);
        add(btnLogout);
        add(lblniceday);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginpage().setVisible(true);
                dispose();
            }
        });

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
    }

    public static void main(String[] args) {
        new seatbooked();
    }
}