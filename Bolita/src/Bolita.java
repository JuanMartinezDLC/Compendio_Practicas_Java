import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bolita extends JPanel {

    int x, y;
    Image img;

    public Bolita() {
        setSize(450, 430);
        x = 150;
        y = 150;
        setBackground(Color.DARK_GRAY); // Este es el background de nuesta ventana

        // Cargamos el archivo de nuestra imagen
        java.net.URL urlImagen = getClass().getResource("/img/miNombre.png");
        
        if (urlImagen != null) {
            img = new ImageIcon(urlImagen).getImage();
        } else {
            System.out.println("❌ ERROR: No se encontró la imagen. Revisa el nombre y la carpeta.");
        }

        //este es el evento para que siga al mouse SOLO con posicionarlo encima (no se necesita dar click)
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (img != null) {
                    // Centramos la imagen en la punta del cursor
                    x = e.getX() - (img.getWidth(null) / 2);
                    y = e.getY() - (img.getHeight(null) / 2);
                } else {
                    x = e.getX();
                    y = e.getY();
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (img != null) {
            // Dibuja nuestar imagen en la posicion del mouse
            g.drawImage(img, x, y, this);
        } else {
            // Si hay un error con la imagen, dibuja un cuadro rojo para avisar
            g.setColor(Color.RED);
            g.fillRect(x, y, 50, 50);
            g.setColor(Color.WHITE);
            g.drawString("Falta img", x, y + 25);
        }
    }
}

class Ventana extends JFrame {

    public Ventana() {
        setSize(450, 430);
        setTitle("Imagen de Juan David");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Bolita bolita = new Bolita();
        add(bolita);
    }

    public static void main(String[] args) {
        Ventana v = new Ventana();
        v.setVisible(true);
    }
}