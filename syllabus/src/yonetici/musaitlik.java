package yonetici;

import sql1.baglan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class musaitlik extends JFrame {
    private JTable musaitTable;
    private JTable musaitDegilTable;
    private DefaultTableModel musaitTableModel;
    private DefaultTableModel musaitDegilTableModel;

    public musaitlik() {
        initializeUI();
        fetchDataFromDatabase();
        addData();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Musaitlik Tablosu");

        musaitTableModel = new DefaultTableModel();
        musaitTableModel.addColumn("DersID");
        musaitTableModel.addColumn("DersAdi");
        musaitTableModel.addColumn("OgretmenAdi");

        musaitDegilTableModel = new DefaultTableModel();
        musaitDegilTableModel.addColumn("DersID");
        musaitDegilTableModel.addColumn("DersAdi");
        musaitDegilTableModel.addColumn("OgretmenAdi");

        musaitTable = new JTable(musaitTableModel);
        musaitDegilTable = new JTable(musaitDegilTableModel);

        JScrollPane musaitScrollPane = new JScrollPane(musaitTable);
        JScrollPane musaitDegilScrollPane = new JScrollPane(musaitDegilTable);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new GridLayout(1, 2));
        tablesPanel.add(musaitScrollPane);
        tablesPanel.add(musaitDegilScrollPane);

        // Label'lar tablo ısmı
        JLabel lblMusait = new JLabel("Müsait");
        JLabel lblMusaitDegil = new JLabel("Müsait Değil");

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.add(lblMusait);
        labelPanel.add(lblMusaitDegil);

        // Geri git, müsaitlikleri göster ve kontrol butonları
        JButton btngos = new JButton("Müsaitlikleri Göster");
        JButton btngeri = new JButton("Geri git");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btngos);
        buttonPanel.add(btngeri);

        add(labelPanel, BorderLayout.NORTH);
        add(tablesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btngeri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new yoneticigirs();
            }
        });

        btngos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTables();

                addData();
            }
        });

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearTables() {
        musaitTableModel.setRowCount(0);
        musaitDegilTableModel.setRowCount(0);
    }

    private void addData() {
        ResultSet myRs = baglan.sqlConnection("Pazartesi");

        try {
            while (myRs.next()) {
                String[] row = new String[3];
                row[0] = myRs.getString("id");
                row[1] = myRs.getString("ogretmen");
                row[2] = myRs.getString("gun");

                if (row[2].equals("0")) {
                    musaitTableModel.addRow(row);
                } else {
                    musaitDegilTableModel.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fetchDataFromDatabase() {
        // Veritabanından veri çekme işlemleri burada gerçekleştirilebilir
        // Örneğin, yukarıda verdiğim veri çekme örneğini buraya ekleyebilirsiniz
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new musaitlik();
        });
    }
}
