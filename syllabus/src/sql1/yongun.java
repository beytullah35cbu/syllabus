package sql1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class yongun extends JFrame {

    private JTextField klad_txt;
    private JPasswordField sif_txt;

    private JTextField yon_txt;
    private JPasswordField sif_txt2;

    public yongun() {
        Yoneticigun();
    }

    private void Yoneticigun() {
        setTitle("Yönetici Güncelleme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnl_grs = new JPanel();
        pnl_grs.setLayout(new GridLayout(3, 2, 10, 10));//burada panelimi izgaralara bölüyorum bunuda 3 kolon 2 stun olarak


        JLabel usernameLabel = new JLabel("Yönetici adı:");
        klad_txt = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        sif_txt = new JPasswordField();
        JButton btn_gun = new JButton("Güncelle");



        pnl_grs.add(usernameLabel);
        pnl_grs.add(klad_txt);
        pnl_grs.add(passwordLabel);
        pnl_grs.add(sif_txt);
        pnl_grs.add(btn_gun);



        btn_gun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = klad_txt.getText();
                String sfir = new String(sif_txt.getPassword());
                    String sql_sorgu = "UPDATE test.sifre SET ad='" + ad + "', sifre='" + sfir + "' WHERE rutbe='yönetici'";
                    baglan.update(sql_sorgu);
                    setVisible(false);
                    Main.main(new String[]{});

            }
        });

        add(pnl_grs, BorderLayout.CENTER);


        pack();//panelin indeki değer ne kadarsa o anda sınırlandırma yapıyo bu ana yarıyo(setSize ile ayaparsanasa senin girdiğin değere göre boyutlandırma yapıyo)
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
        setVisible(true);

    }
    public static void main() {
        SwingUtilities.invokeLater(new Runnable() {//run yani burada calışacak kod var demek
            public void run() {
                new yongun();
            }
        });
    }
}