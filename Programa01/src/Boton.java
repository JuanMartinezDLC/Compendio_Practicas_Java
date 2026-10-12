import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Boton extends JFrame {
    
    public Boton() {
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Programa 01");
        
        //Para centrar mi ventala en la pantalla al abrirse
        setLocationRelativeTo(null);
        
        //ayuda a que la ventana sea movible y rendimensinable
        setResizable(true);
        
        JButton btnSaludar = new JButton("Saluda");
        
        btnSaludar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Juan David de la Cruz Martinez");
            }
        });
        
        add(btnSaludar);
        setVisible(true);
    }
}
