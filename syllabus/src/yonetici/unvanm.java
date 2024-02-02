package yonetici;

import sql1.baglan;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class unvanm extends JFrame {

    private JComboBox<String> unvanComboBox;
    private JTextField yeniUnvanTextField;

    private ArrayList<String> unvanlar = new ArrayList<>();

    public unvanm() {
        initializeUI();
        verileriComboBoxaEkle();

    }

    private void initializeUI() {
        setTitle("Unvan Uygulaması");
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(1,3));
        JPanel formPanel1 = new JPanel(new GridLayout(1,3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        unvanComboBox = new JComboBox<>();


        JButton ekleButton = new JButton("Unvan Ekle");
        ekleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String un=yeniUnvanTextField.getText();
                baglan.sqlConnection("unvanlar");
                String sql_sorgu = "INSERT INTO unvanlar (unvan) VALUES ('"+un+"')";
                baglan.ekle(sql_sorgu);
            }
        });

        JButton geriGitButton = new JButton("Geri Git");
        geriGitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new oduzenle();
            }
        });

        JButton silButton = new JButton("Unvan Sil");
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenUnvan = (String) unvanComboBox.getSelectedItem();

                if (secilenUnvan != null) {
                    baglan.sqlConnection("unvanlar");
                    String sql_sorgu = "DELETE FROM unvanlar WHERE unvan = '" + secilenUnvan + "'";
                    baglan.delete(sql_sorgu);

                    // Combobox'ı güncelle
                    unvanComboBox.removeItem(secilenUnvan);
                    JOptionPane.showMessageDialog(unvanm.this, "Unvan başarıyla silindi: " + secilenUnvan);
                } else {
                    JOptionPane.showMessageDialog(unvanm.this, "Silinecek bir unvan seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        yeniUnvanTextField = new JTextField(20);


        formPanel.add(new JLabel("Unvan:"));
        formPanel.add(unvanComboBox);
        formPanel.add(yeniUnvanTextField);
        formPanel1.add(ekleButton);
        formPanel1.add(geriGitButton);
        formPanel1.add(silButton);

        add(formPanel, BorderLayout.CENTER);
        add(formPanel1, BorderLayout.SOUTH);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void verileriComboBoxaEkle() {
        try {
            ResultSet myRs= baglan.sqlConnection("unvanlar");
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
                new unvanm();
            }
        });
    }
}
