package dersislem;

import sql1.baglan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dersislemleri extends JFrame {
    private JLabel lblad;
    private JLabel lblsoyad;
    private JLabel lblhocaAd;
    private JLabel lblHaftalikSaat; // Yeni label
    private JTextField txtad;
    private JTextField txtsoyad;
    private JTextField txthocaAd;
    private JTextField txtHaftalikSaat; // Yeni text field
    private JTextField txtArama;
    private JTable Derstable;

    private DefaultTableModel modelim = new DefaultTableModel();
    private Object[] kolonlar = {"idDers", "Ders Adı", "Ders Kodu", "Hoca Adı", "Haftalık Saat"};
    private Object[] satirlar = new Object[5];

    public void tablocagırma() {
        modelim.setRowCount(0);
        ResultSet myrs = baglan.sqlConnection("ders");
        try {
            while (myrs.next()) {
                satirlar[0] = myrs.getString("idDers");
                satirlar[1] = myrs.getString("dersAd");
                satirlar[2] = myrs.getString("dersKodu");
                satirlar[3] = myrs.getString("hocaAd");
                satirlar[4] = myrs.getString("haftaliksaat");
                modelim.addRow(satirlar);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        Derstable.setModel(modelim);
    }

    public dersislemleri() {
        setTitle("Ders İşlemleri");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(22, 10, 15, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btndersekle = new JButton("Ders Ekle");
        JButton btndersduzenle = new JButton("Ders Düzenle");
        JButton btndersil = new JButton("Ders Sil");
        JButton btngerigit = new JButton("Geri Git");

        lblHaftalikSaat = new JLabel("Haftalık Saat:"); // Yeni label
        txtHaftalikSaat = new JTextField(); // Yeni text field

        txtArama = new JTextField("", 5);
        JButton btnAra = new JButton("Ara");

        topPanel.add(btndersekle);
        topPanel.add(btndersduzenle);
        topPanel.add(btndersil);
        topPanel.add(btngerigit);
        topPanel.add(lblHaftalikSaat);
        topPanel.add(txtHaftalikSaat);
        topPanel.add(txtArama);
        topPanel.add(btnAra);

        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10)); // 5 satır, 2 sütunlu bir GridLayout

        lblad = new JLabel("Ders Adı Giriniz:");
        lblsoyad = new JLabel("Ders Kodu Giriniz:");
        lblhocaAd = new JLabel("Hoca Adı Giriniz:");

        txtad = new JTextField();
        txtsoyad = new JTextField();
        txthocaAd = new JTextField();

        inputPanel.add(lblad);
        inputPanel.add(txtad);
        inputPanel.add(lblsoyad);
        inputPanel.add(txtsoyad);
        inputPanel.add(lblhocaAd);
        inputPanel.add(txthocaAd);
        inputPanel.add(lblHaftalikSaat); // Yeni label
        inputPanel.add(txtHaftalikSaat); // Yeni text field

        contentPane.add(inputPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();

        Derstable = new JTable();
        modelim.setColumnIdentifiers(kolonlar);
        Derstable.setFont(new Font("Arial", Font.PLAIN, 14));
        Derstable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int satirIndex = Derstable.getSelectedRow();
                String dersAd = (String) Derstable.getValueAt(satirIndex, 1);
                String dersKodu = (String) Derstable.getValueAt(satirIndex, 2);
                String hocaAd = (String) Derstable.getValueAt(satirIndex, 3);
                String haftalikSaat = (String) Derstable.getValueAt(satirIndex, 4);

                txtad.setText(dersAd);
                txtsoyad.setText(dersKodu);
                txthocaAd.setText(hocaAd);
                txtHaftalikSaat.setText(haftalikSaat);
                super.mouseClicked(e);
            }
        });
        scrollPane.setViewportView(Derstable);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        JButton btndersatama = new JButton("Listeyi Göster");
        contentPane.add(btndersatama, BorderLayout.WEST);
        btndersatama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablocagırma();
            }
        });

        btnAra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aramaKelimesi = txtArama.getText().trim();

                if (!aramaKelimesi.isEmpty()) {
                    DefaultTableModel model = (DefaultTableModel) Derstable.getModel();
                    TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(model);
                    Derstable.setRowSorter(rowSorter);
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + aramaKelimesi));
                } else {
                    Derstable.setRowSorter(null);
                }
            }
        });

        btndersduzenle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dersAd, dersKodu, hocaAd, haftalikSaat, sql_sorgu;

                dersAd = txtad.getText();
                dersKodu = txtsoyad.getText();
                hocaAd = txthocaAd.getText();
                haftalikSaat = txtHaftalikSaat.getText();

                baglan.sqlConnection("ders ");
             //   sql_sorgu = "UPDATE ders (dersAd,dersKodu,hocaAd,haftaliksaat) VALUES ('" + dersAd + "','" + dersKodu + "','" + hocaAd + "','" + haftalikSaat + "')";
           sql_sorgu = "UPDATE ders SET dersad=('" + dersAd + "', derskodu='" + dersKodu + "', hocaAd='" + hocaAd + "', haftaliksaat='" + haftalikSaat + "')";

                baglan.update(sql_sorgu);
             //   tablocagırma();


            }
        });

        btndersekle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dersAd, dersKodu, hocaAd, haftalikSaat, sql_sorgu;

                dersAd = txtad.getText();
                dersKodu = txtsoyad.getText();
                hocaAd = txthocaAd.getText();
                haftalikSaat = txtHaftalikSaat.getText();

                baglan.sqlConnection("ders ");
                sql_sorgu = "INSERT INTO ders (dersAd,dersKodu,hocaAd,haftaliksaat) VALUES ('" + dersAd + "','" + dersKodu + "','" + hocaAd + "','" + haftalikSaat + "')";

                baglan.update(sql_sorgu);
                tablocagırma();
            }
        });

        btndersil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dersAd, sql_sorgu;

                dersAd = txtad.getText();
                baglan.sqlConnection("ders ");

                sql_sorgu = "DELETE FROM ders WHERE dersAd='" + dersAd + "'";

                baglan.delete(sql_sorgu);
                tablocagırma();
            }
        });

        btngerigit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new yonetici.yoneticigirs();
            }
        });

        // Bu satır tablonun görünür olmasını sağlar
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    dersislemleri frame = new dersislemleri();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
