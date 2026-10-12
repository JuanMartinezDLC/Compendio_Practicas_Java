import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dibujo extends JFrame {
    JComboBox<String> comboColores;
    JPanel panelDibujo, panelSuperior;
    JButton btnLimpiar;
    
    BufferedImage imagenLienzo;
    BufferedImage imagenFondo;
    Graphics2D g2;
    
    int xAnterior, yAnterior;
    
    public Dibujo() {
        setTitle("Paint");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        comboColores = new JComboBox<>();
        comboColores.addItem("Rojo");
        comboColores.addItem("Verde");
        comboColores.addItem("Azul");  
        
        btnLimpiar = new JButton("Limpiar");
        
        panelSuperior = new JPanel();
        panelSuperior.add(comboColores);
        panelSuperior.add(btnLimpiar);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // CAMBIO CLAVE 1: Usar TYPE_INT_ARGB para soportar transparencia (Alpha)
        imagenLienzo = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        g2 = imagenLienzo.createGraphics();
        
        // Cargar el fondo y dibujar
        cargarYDibujarFondo();
        
        // Color inicial del pincel
        actualizarColorPincel();
        
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenLienzo, 40, 10, null);            
            }
        };  
        panelDibujo.setBackground(Color.CYAN);
        
        // Evento cambio de color
        comboColores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarColorPincel();
            }
        });
        
        // Evento botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarYDibujarFondo();
                actualizarColorPincel();
                panelDibujo.repaint();
            }
        });
        
        // Eventos Mouse
        panelDibujo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xAnterior = e.getX() - 40;
                yAnterior = e.getY() - 10;
            }
        });
        
        panelDibujo.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX() - 40;
                int y = e.getY() - 10;                            
                
                g2.drawLine(xAnterior, yAnterior, x, y);  
                
                xAnterior = x;
                yAnterior = y;
                
                panelDibujo.repaint();
            }
        });
        
        add(panelDibujo, BorderLayout.CENTER);        
    }
    
    private void actualizarColorPincel() {
        int colorSeleccionado = comboColores.getSelectedIndex();
        switch(colorSeleccionado) {
            case 0: g2.setColor(Color.RED); break;
            case 1: g2.setColor(Color.GREEN); break;                                
            case 2: g2.setColor(Color.BLUE); break;
        }
    }
    
    private void cargarYDibujarFondo() {
        // CAMBIO CLAVE 2: Siempre pintar la base de Blanco primero para eliminar el negro de la memoria
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 300, 300);
        
        try {
            // Intenta cargar la imagen desde la carpeta del proyecto/paquete
            InputStream stream = getClass().getResourceAsStream("/fondo.png");
            if (stream == null) {
                stream = getClass().getResourceAsStream("fondo.png");
            }
            
            if (stream != null) {
                imagenFondo = ImageIO.read(stream);
                g2.drawImage(imagenFondo, 0, 0, 300, 300, null);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Dibujo d = new Dibujo();
        d.setVisible(true);
    }
}