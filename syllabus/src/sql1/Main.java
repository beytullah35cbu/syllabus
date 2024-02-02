package sql1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends JFrame {

    private JTextField klad_txt;
    private JPasswordField sif_txt;

    private JTextField yon_txt;
    private JPasswordField sif_txt2;
    public  JButton btn_grs2;
    public static String yetk;
    private String a="sifre";

    public Main() {
        kullaniciekran();
    }

    private void kullaniciekran() {
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane g_tapent=new JTabbedPane(JTabbedPane.TOP);//2 gecıslı ekrn

        JPanel pnl_grs = new JPanel();
        pnl_grs.setLayout(new GridLayout(3, 2, 10, 10));//burada panelimi izgaralara bölüyorum bunuda 3 kolon 2 stun olarak


        JLabel usernameLabel = new JLabel("Kullanıcı adı:");
        klad_txt = new JTextField();
        klad_txt.setText("Ahmet");
        JLabel passwordLabel = new JLabel("Şifre:");
        sif_txt = new JPasswordField();
        sif_txt.setText("13321");
        JButton btn_grs = new JButton("Giriş");
        JButton btn_kydol = new JButton("Kaydol");
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("kitap.png"));
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // 50x50 boyutuna getirme
        ImageIcon newImageIcon = new ImageIcon(newImage);
        JLabel url = new JLabel(newImageIcon);
        JLabel mesj = new JLabel("Ders Programı Hazırlama");
        mesj.setForeground(Color.blue);
        mesj.setFont(new Font("Yu Gothic UI semibold", Font.PLAIN, 17));
        mesj.setHorizontalAlignment(SwingConstants.CENTER);


        mesj.setFont(new Font("Yu Gothic UI semibold",Font.PLAIN,17));



        pnl_grs.add(usernameLabel);
        pnl_grs.add(klad_txt);
        pnl_grs.add(passwordLabel);
        pnl_grs.add(sif_txt);
        pnl_grs.add(url);
        pnl_grs.add(mesj);
        pnl_grs.add(btn_grs);
        pnl_grs.add(btn_kydol);




        btn_grs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = klad_txt.getText();

                String sfir = new String(sif_txt.getPassword());


                String sql_sorgu="SELECT COUNT(id) AS giriş from test.sifre where ad='"+ad+"' and sifre='"+sfir+"'";
                ResultSet myRs= baglan.sqlConnection(a);
                myRs = baglan.bul(sql_sorgu,"sifre");

                try {
                    while (myRs.next()){
                        if (myRs.getInt("giriş")==1){

                            setVisible(false); // Pencereyi gizle
                            dispose();
                            yetk ="kul";
                            new yonetici.yoneticigirs();


                        }else {
                            JOptionPane.showMessageDialog(Main.this, "Geçersiz kullanıcı adı veya şifre", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        btn_kydol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad,sfre,sql_sorgu;
                 ad=klad_txt.getText();
                 sfre=new String(sif_txt.getPassword());
                sql_sorgu = "INSERT INTO sifre(ad, sifre, rutbe) VALUES ('" + ad + "', '" + sfre + "', 'kullanici')";
                a="sifre";
                baglan.sqlConnection(a);
                baglan.ekle(sql_sorgu);


            }
        });
        JPanel gorsel = new JPanel(new BorderLayout());//burada bir panel oluşturarak
        gorsel.add(url, BorderLayout.NORTH);
        gorsel.add(mesj, BorderLayout.CENTER);

        add(gorsel,BorderLayout.NORTH);//burası url yi panelin üst bölgesine yerleştirir(NORTH)
        //add(pnl_grs, BorderLayout.CENTER);//burası paneli ortalar(CENTER)
        //add(btn_grs, BorderLayout.SOUTH);//burası paneli sola yaslar(SOUTH)

        JPanel pnl_yongrs=new JPanel();
        pnl_yongrs.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel2 = new JLabel("Yönetici adı:");
        yon_txt = new JTextField();
        yon_txt.setText("Beytullah");
        JLabel passwordLabel2 = new JLabel("Şifre:");
        sif_txt2 = new JPasswordField();
        sif_txt2.setText("3552");
        btn_grs2 = new JButton("Giriş");
        JButton btn_gun = new JButton("Güncelle");

        btn_grs2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = yon_txt.getText();
                String sfir = new String(sif_txt2.getPassword());


                String sql_sorgu="SELECT COUNT(id) AS giriş from test.sifre where ad='"+ad+"' and sifre='"+sfir+"'and rutbe = 'yönetici'";
                ResultSet myRs= baglan.sqlConnection(a);
                myRs = baglan.bul(sql_sorgu,"sifre");

                try {
                    while (myRs.next()){
                        if (myRs.getInt("giriş")==1){
                            // ekran.main();
                            setVisible(false); // Pencereyi gizle
                            yetk ="yon";
                            new yonetici.yoneticigirs();

                        }else {
                            JOptionPane.showMessageDialog(Main.this, "Geçersiz kullanıcı adı veya şifre", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        btn_gun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = yon_txt.getText();
                String sfir = new String(sif_txt2.getPassword());
                String sqlSorgu = "SELECT ad, sifre FROM test.sifre  WHERE rutbe = 'yönetici'";
                ResultSet myRs=baglan.bul(sqlSorgu,"sifre");
                try {
                    if (myRs.next()) {
                        // Doğru kullanıcı adı ve şifre
                        String dogruAd = myRs.getString("ad");
                        String dogruSifre = myRs.getString("sifre");

                        // İşlemlerinizi burada yapabilirsiniz
                        // Örneğin, eğer Fırat'ın şifresi 3636 ise şu işlemi yapabilirsiniz:
                        if (dogruAd.equals(ad) && dogruSifre.equals(sfir)) {
                            yongun.main();
                        } else  {
                            JOptionPane.showMessageDialog(Main.this, "Geçersiz kullanıcı adı veya şifre", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        pnl_yongrs.add(usernameLabel2);
        pnl_yongrs.add(yon_txt);
        pnl_yongrs.add(passwordLabel2);
        pnl_yongrs.add(sif_txt2);
        pnl_yongrs.add(btn_grs2);
        pnl_yongrs.add(btn_gun);




        g_tapent.addTab("Kullanıcı",null,pnl_grs,null);
        g_tapent.addTab("Yönetici",null,pnl_yongrs,null);
        add(g_tapent);
        //setSize(300,300);
        pack();//panelin indeki değer ne kadarsa o anda sınırlandırma yapıyo bu ana yarıyo(setSize ile ayaparsanasa senin girdiğin değere göre boyutlandırma yapıyo)
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştirme
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {//run yani burada calışacak kod var demek
            public void run() {
                new Main();
            }
        });
    }
}


