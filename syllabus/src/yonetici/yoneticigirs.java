package yonetici;
import sql1.Main;
import dersislem.dersislemleri;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class yoneticigirs extends JFrame {

    private JTextField klad_txt;
    private JPasswordField sif_txt;

    private JTextField yon_txt;
    private JPasswordField sif_txt2;

    public yoneticigirs() {
        Yoneticiekran();
    }

    private void Yoneticiekran() {
        setTitle("Program Menüsü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnl_grs = new JPanel();
        pnl_grs.setLayout(new GridLayout(1, 5, 10, 10));//burada panelimi izgaralara bölüyorum bunuda 3 kolon 2 stun olarak



        JButton btn_oduzenle = new JButton("Öğretmen Düzenleme");
        JButton btn_zaman = new JButton("Okul Zaman İşlemleri");
        JButton btn_islem = new JButton("Ders Ekleme ve Düzenleme");
        JButton btn_mus = new JButton(" Müsaitlik");
        JButton btn_dersprg = new JButton("Ders Programını Hazırla");
        JButton btn_geri = new JButton("Geri");
        pnl_grs.add(btn_oduzenle);
        pnl_grs.add(btn_zaman);
        pnl_grs.add(btn_islem);
        pnl_grs.add(btn_mus);
        pnl_grs.add(btn_dersprg);
        pnl_grs.add(btn_geri);

        pnl_grs.setLayout(new BoxLayout(pnl_grs, BoxLayout.Y_AXIS));


        btn_oduzenle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main mn = new Main();

                if ("yon".equals(Main.yetk)) { // Main.yetk kullanarak kontrol et
                    new oduzenle();
                } else {
                    JOptionPane.showMessageDialog(null, "Yetkiniz yok.");
                    dispose();
                }
            }
        });




        btn_geri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Mevcut pencereyi kapat

                new Main(); // Yeni giriş sayfası penceresini aç
            }
        });
        btn_mus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new musaitlik();


            }
        });

        btn_islem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dersislemleri frame = new dersislemleri();
                frame.setVisible(true); // Eğer bu çağrı eksikse ekleyin
            }
        });

        btn_zaman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });


        add(pnl_grs);
        pack();//panelin indeki değer ne kadarsa o anda sınırlandırma yapıyo bu ana yarıyo(setSize ile ayaparsanasa senin girdiğin değere göre boyutlandırma yapıyo)
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
        setVisible(true);

    }


    public static void main(String[] argss) {
        SwingUtilities.invokeLater(new Runnable() {//run yani burada calışacak kod var demek
            public void run() {
                new yoneticigirs();
            }
        });
    }
}


