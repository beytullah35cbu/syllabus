package yonetici;

import sql1.baglan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class oduzenle extends JFrame {

    private String b = "Kullanicilar";
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String KULLANICI_ADI = "root";
    private static final String SIFRE = "";
    private static String[] kolon = {"id", "Yetkili", "Unvan", "Tc", "Ad", "Soyad", "Telefon"};

    private static DefaultTableModel model = new DefaultTableModel();
    private JTable table;
    private JTextField aramaTextField;
    private JButton araButton;

    public oduzenle() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Öğretmenler Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Butonları yan yana hizala

        JButton btnUnvan = new JButton("Unvan Menüsü");

        JButton btnOgretmenEkle = new JButton("Öğretmen Düzenle");

        JButton btnGeriGit = new JButton("Geri Git");

        model.setColumnIdentifiers(kolon);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(btnUnvan);

        panel.add(btnOgretmenEkle);

        panel.add(btnGeriGit);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        aramaTextField = new JTextField(15);
        araButton = new JButton("İsime Göre Ara");

        JPanel aramaPanel = new JPanel();
        aramaPanel.add(aramaTextField);
        aramaPanel.add(araButton);

        add(aramaPanel, BorderLayout.SOUTH);

        addData();

        btnGeriGit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                model.setRowCount(0); // Tabloyu temizle

                new yoneticigirs();
            }
        });

        btnUnvan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.setRowCount(0); // Tabloyu temizle
                dispose();
                new unvanm();
            }
        });

        btnOgretmenEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                model.setRowCount(0); // Tabloyu temizle
                new oekle();
            }
        });

        araButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = aramaTextField.getText();
                String sql_sorgu = "SELECT * FROM Kullanicilar WHERE ad LIKE '" + ad + "%'";
                ResultSet resultSet = baglan.bul(sql_sorgu, "Kullanicilar");

                // Tabloyu temizle
                model.setRowCount(0);

                try {
                    // ResultSet üzerinde dolaşarak tabloyu güncelle
                    while (resultSet.next()) {
                        String[] stun = new String[7];
                        stun[0] = resultSet.getString("id");
                        stun[1] = resultSet.getString("yetkili");
                        stun[2] = resultSet.getString("unvan");
                        stun[3] = resultSet.getString("tc");
                        stun[4] = resultSet.getString("ad");
                        stun[5] = resultSet.getString("soyad");
                        stun[6] = resultSet.getString("telefon");
                        model.addRow(stun);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addData() {
        ResultSet myRs = baglan.sqlConnection(b);

        try {
            while (myRs.next()) {
                String[] stun = new String[7];
                stun[0] = myRs.getString("id");
                stun[1] = myRs.getString("yetkili");
                stun[2] = myRs.getString("unvan");
                stun[3] = myRs.getString("tc");
                stun[4] = myRs.getString("ad");
                stun[5] = myRs.getString("soyad");
                stun[6] = myRs.getString("telefon");
                model.addRow(stun);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new oduzenle();
            }
        });
    }
}
