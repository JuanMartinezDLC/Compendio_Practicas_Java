import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Calculadora extends JFrame implements ActionListener {
    JButton btnSuma, btnResta, btnDivision, btnMultiplicacion, btnIgual, btnPunto, btnLimpiar;
    JTextField pantalla;
    Font letra = new Font("Arial", Font.BOLD, 24);
    JButton btnDigitos[];
    
    // Variables de estado
    private double primerNumero = 0;
    private String operacion = "";
    private boolean nuevoNumero = true;

    public Calculadora() {
        setTitle("Mi Calculadora");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Pantalla
        pantalla = new JTextField("0");
        pantalla.setFont(new Font("Arial", Font.BOLD, 32));
        pantalla.setHorizontalAlignment(SwingConstants.RIGHT);
        pantalla.setEditable(false);
        add(pantalla, BorderLayout.NORTH);

        // Panel de Botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 5, 5));

        btnSuma = crearBoton("+");
        btnResta = crearBoton("-");
        btnMultiplicacion = crearBoton("*");
        btnDivision = crearBoton("/");
        btnIgual = crearBoton("=");
        btnPunto = crearBoton(".");
        btnLimpiar = crearBoton("C");

        btnDigitos = new JButton[10];
        for (int i = 0; i < 10; i++) {
            btnDigitos[i] = crearBoton(String.valueOf(i));
        }

        // Fila 1
        panelBotones.add(btnLimpiar);
        panelBotones.add(new JPanel());
        panelBotones.add(new JPanel());
        panelBotones.add(btnDivision);

        // Fila 2
        panelBotones.add(btnDigitos[7]);
        panelBotones.add(btnDigitos[8]);
        panelBotones.add(btnDigitos[9]);
        panelBotones.add(btnMultiplicacion);

        // Fila 3
        panelBotones.add(btnDigitos[4]);
        panelBotones.add(btnDigitos[5]);
        panelBotones.add(btnDigitos[6]);
        panelBotones.add(btnResta);

        // Fila 4
        panelBotones.add(btnDigitos[1]);
        panelBotones.add(btnDigitos[2]);
        panelBotones.add(btnDigitos[3]);
        panelBotones.add(btnSuma);

        // Fila 5
        panelBotones.add(btnDigitos[0]);
        panelBotones.add(btnPunto);
        panelBotones.add(btnIgual);

        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(letra);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.charAt(0) >= '0' && comando.charAt(0) <= '9') {
            if (nuevoNumero || pantalla.getText().equals("0")) {
                pantalla.setText(comando);
                nuevoNumero = false;
            } else {
                pantalla.setText(pantalla.getText() + comando);
            }
        } else if (comando.equals(".")) {
            if (nuevoNumero) {
                pantalla.setText("0.");
                nuevoNumero = false;
            } else if (!pantalla.getText().contains(".")) {
                pantalla.setText(pantalla.getText() + ".");
            }
        } else if (comando.equals("C")) {
            pantalla.setText("0");
            primerNumero = 0;
            operacion = "";
            nuevoNumero = true;
        } else if (comando.equals("=")) {
            if (!operacion.isEmpty()) {
                double segundoNumero = Double.parseDouble(pantalla.getText());
                double resultado = calcular(primerNumero, segundoNumero, operacion);
                
                if (resultado == (long) resultado) {
                    pantalla.setText(String.format("%d", (long) resultado));
                } else {
                    pantalla.setText(String.valueOf(resultado));
                }
                operacion = "";
                nuevoNumero = true;
            }
        } else {
            if (!operacion.isEmpty() && !nuevoNumero) {
                double segundoNumero = Double.parseDouble(pantalla.getText());
                primerNumero = calcular(primerNumero, segundoNumero, operacion);
                pantalla.setText(String.valueOf(primerNumero));
            } else {
                primerNumero = Double.parseDouble(pantalla.getText());
            }
            operacion = comando;
            nuevoNumero = true;
        }
    }

    private double calcular(double num1, double num2, String op) {
        switch (op) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": 
                if (num2 == 0) {
                    JOptionPane.showMessageDialog(this, "No se puede dividir entre cero", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return num1 / num2;
            default: return num2;
        }
    }

    public static void main(String[] args) {
        Calculadora c = new Calculadora();
        c.setVisible(true);
    }
}