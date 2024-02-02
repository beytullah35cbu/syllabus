package yonetici;

import sql1.baglan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class oekle extends JFrame {

    private String b = "Kullanicilar";
    private static String[] kolon = {"id", "Yetkisi", "Unvan", "Tc", "Ad", "Soyad", "Telefon"};

    private static DefaultTableModel model = new DefaultTableModel();
    private JTable table;

    private JComboBox<String> unvanComboBox;

    private JTextField txtYetkili;

    private JTextField txtTc;
    private JTextField txtAd;
    private JTextField txtSoyad;
    private JTextField txtTelefon;

    public oekle() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Öğretmenler Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout()); // Butonları yan yana hizala

        JButton btnekle = new JButton("Öğretmen Ekle");
        JButton btnsil = new JButton("Öğretmen Sil");
        JButton btnguncelle = new JButton("Öğretmen Güncelle");

        JButton btnGeriGit = new JButton("Geri Git");

        model.setColumnIdentifiers(kolon);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        unvanComboBox = new JComboBox<>();
        verileriComboBoxaEkle();

        panel.add(btnekle);
        panel.add(btnsil);
        panel.add(btnguncelle);

        panel.add(btnGeriGit);




        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        panel.add(unvanComboBox);
        addData();
        btnekle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean a = txtYetkili.getText().isEmpty() || txtTc.getText().isEmpty()
                        || txtAd.getText().isEmpty() || txtSoyad.getText().isEmpty() || txtTelefon.getText().isEmpty();
                if (a) {
                    JOptionPane.showMessageDialog(null, "Boş değer girilemez.");


                    // Text alanlarını temizle
                    clearTextFields();
                } else {
                    String yetkili = txtYetkili.getText();
                    String unvan = (String) unvanComboBox.getSelectedItem();
                    String tc = txtTc.getText();
                    String ad = txtAd.getText();
                    String soyad = txtSoyad.getText();
                    String telefon = txtTelefon.getText();

                    String sql_sorgu = "INSERT INTO Kullanicilar (Yetkili, Unvan, Tc, Ad, Soyad, Telefon) VALUES ('" + yetkili + "','" + unvan + "','" + tc + "','" + ad + "','" + soyad + "','" + telefon + "')";
                    baglan.ekle(sql_sorgu);

                    // Tabloyu güncelle
                    model.addRow(new Object[]{getMaxId() + 1, yetkili, unvan, tc, ad, soyad, telefon});
                }
            }
        });

        btnsil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

                    // Tablodan seçilen satırı sil
                    model.removeRow(selectedRow);

                    String sql = "DELETE FROM Kullanicilar WHERE id =" + id;
                    baglan.delete(sql);

                    // Text alanlarını temizle
                    clearTextFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin.");
                }
            }
        });

        btnguncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                    String yetkili = txtYetkili.getText();
                    String unvan = (String) unvanComboBox.getSelectedItem();
                    String tc = txtTc.getText();
                    String ad = txtAd.getText();
                    String soyad = txtSoyad.getText();
                    String telefon = txtTelefon.getText();

                    String sql_sorgu = "UPDATE Kullanicilar SET Yetkili='" + yetkili + "', Unvan='" + unvan + "', Tc='" + tc + "', Ad='" + ad + "', Soyad='" + soyad + "', Telefon='" + telefon + "' WHERE id=" + id;
                    // Veritabanında güncelleme işlemi
                    baglan.update(sql_sorgu);

                    // Tablodan seçilen satırın verilerini güncelle
                    model.setValueAt(yetkili, selectedRow, 1);
                    model.setValueAt(unvan, selectedRow, 2);
                    model.setValueAt(tc, selectedRow, 3);
                    model.setValueAt(ad, selectedRow, 4);
                    model.setValueAt(soyad, selectedRow, 5);
                    model.setValueAt(telefon, selectedRow, 6);

                    // Text alanlarını temizle
                    clearTextFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir satır seçin.");
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tablodan seçilen satırın verilerini text alanlarına yerleştir
                txtYetkili.setText((String) model.getValueAt(table.getSelectedRow(), 1));
                unvanComboBox.setSelectedItem(model.getValueAt(table.getSelectedRow(), 2));
                txtTc.setText((String) model.getValueAt(table.getSelectedRow(), 3));
                txtAd.setText((String) model.getValueAt(table.getSelectedRow(), 4));
                txtSoyad.setText((String) model.getValueAt(table.getSelectedRow(), 5));
                txtTelefon.setText((String) model.getValueAt(table.getSelectedRow(), 6));
            }
        });

        btnGeriGit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                model.setRowCount(0); // Tabloyu temizle

                new oduzenle();
            }
        });

        // Text alanları için panel oluştur
        JPanel textFieldPanel = new JPanel(new GridLayout(6, 2));
        textFieldPanel.add(new JLabel("Yetkili:"));
        txtYetkili = new JTextField();
        textFieldPanel.add(txtYetkili);
        txtYetkili.setText("Kullanici");
        textFieldPanel.add(new JLabel("TC:"));
        txtTc = new JTextField();
        textFieldPanel.add(txtTc);

        textFieldPanel.add(new JLabel("Ad:"));
        txtAd = new JTextField();
        textFieldPanel.add(txtAd);

        textFieldPanel.add(new JLabel("Soyad:"));
        txtSoyad = new JTextField();
        textFieldPanel.add(txtSoyad);

        textFieldPanel.add(new JLabel("Telefon:"));
        txtTelefon = new JTextField();
        textFieldPanel.add(txtTelefon);

        textFieldPanel.add(new JLabel("Unvan:"));
        textFieldPanel.add(unvanComboBox);
        add(textFieldPanel, BorderLayout.SOUTH);

        // İçeriği paketle
        pack();

        // Boyutu ayarla
     //   setSize(getWidth() + 100, getHeight() + 100);

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

    private int getMaxId() {
        int maxId = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            int currentId = Integer.parseInt(model.getValueAt(i, 0).toString());
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        return maxId;
    }

    private void clearTextFields() {
        txtYetkili.setText("");
        txtTc.setText("");
        txtAd.setText("");
        txtSoyad.setText("");
        txtTelefon.setText("");
    }

    private void verileriComboBoxaEkle() {
        try {
            ResultSet myRs = baglan.sqlConnection("unvanlar");
            while (myRs.next()) {
                String unvan = myRs.getString("unvan");
                unvanComboBox.addItem(unvan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new oekle();
            }
        });
    }
}
