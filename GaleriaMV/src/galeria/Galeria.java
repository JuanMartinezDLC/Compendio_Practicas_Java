import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Galeria extends JFrame {
    JButton btnAtras, btnAdelante;
    JPanel panelBotones, panelImagen;
    ImageIcon[] imagenes;
    String[] titulos;
    int imagenActual;
    JLabel etiquetaImagen, etiquetaTitulo;
    
    // MÉTODO AUXILIAR: Debe ir fuera del constructor, directo en la clase
    private ImageIcon cargarImagenSegura(String ruta) {
        java.net.URL url = getClass().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println("❌ ERROR: No se encontró la imagen -> " + ruta);
            return new ImageIcon(); // Retorna un icono vacío para que el programa no colapse
        }
    }
    
    // CONSTRUCTOR
    public Galeria() {
        setTitle("Galería de Marvel Rivals");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
        
        panelBotones = new JPanel();        
        panelImagen = new JPanel();        
        btnAtras = new JButton("Atras");
        btnAdelante = new JButton("Adelante");
        
        imagenes = new ImageIcon[10];
        titulos = new String[10];
        
        // 1. Cargar las 10 imágenes llamando al método seguro
        imagenes[0] = cargarImagenSegura("/img/deadpool.png");        
        imagenes[1] = cargarImagenSegura("/img/gambito.png");
        imagenes[2] = cargarImagenSegura("/img/loki.png");
        imagenes[3] = cargarImagenSegura("/img/jeff.png");
        imagenes[4] = cargarImagenSegura("/img/luna.png");
        imagenes[5] = cargarImagenSegura("/img/invis.png");
        imagenes[6] = cargarImagenSegura("/img/cloack.png"); // Asegúrate de que el archivo se llame cynd.jpg y no c&d.jpg
        imagenes[7] = cargarImagenSegura("/img/jubilee.png");
        imagenes[8] = cargarImagenSegura("/img/wf.png");
        imagenes[9] = cargarImagenSegura("/img/dd.png");

        // 2. Cargar los 10 títulos correspondientes
        titulos[0] = "Deadpool";
        titulos[1] = "Gambito";
        titulos[2] = "Loki";
        titulos[3] = "Jeff the Land Shark";
        titulos[4] = "Luna Snow";
        titulos[5] = "Invisible Woman";
        titulos[6] = "Cloak and Dagger";
        titulos[7] = "Jubilee";
        titulos[8] = "White Fox";
        titulos[9] = "Devil Dinosaur";
        
        etiquetaImagen = new JLabel();
        panelImagen.add(etiquetaImagen);
        
        etiquetaTitulo = new JLabel();
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        
        // Se inicializa la primera imagen
        imagenActual = 0;
        mostrarImagen();
        
        btnAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagenActual == 0) {
                    imagenActual = 9;
                } else {
                    imagenActual--;
                }
                mostrarImagen();
            }
        });
        
        btnAdelante.addActionListener(e -> {
            if (imagenActual == 9) {
                imagenActual = 0;
            } else {
                imagenActual++;
            }
            mostrarImagen();
        });
        
        panelBotones.add(btnAtras);
        panelBotones.add(btnAdelante);

        add(etiquetaTitulo, BorderLayout.NORTH);
        add(panelImagen, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);        
    }

    public static void main(String[] args) {
        Galeria g = new Galeria();
        g.setVisible(true);
    }
    
    public void mostrarImagen() {
        ImageIcon icono = imagenes[imagenActual];
        
        if (icono.getImageLoadStatus() == java.awt.MediaTracker.ERRORED) {
            System.out.println("Advertencia: Hay un problema interno con la imagen " + titulos[imagenActual]);
        }

        Image imagen = icono.getImage();
        // Solo intentamos escalar si la imagen realmente tiene contenido (ancho mayor a 0)
        if (imagen.getWidth(null) > 0) {
            Image imagenEscalada = imagen.getScaledInstance(
                    400,
                    400,
                    Image.SCALE_SMOOTH
            );
            etiquetaImagen.setIcon(new ImageIcon(imagenEscalada));
        } else {
            // Si la imagen está vacía (por el error de carga), mostramos el JLabel sin icono
            etiquetaImagen.setIcon(null); 
            etiquetaImagen.setText("Imagen no encontrada");
        }
        
        etiquetaTitulo.setText(titulos[imagenActual]); 
    }
}