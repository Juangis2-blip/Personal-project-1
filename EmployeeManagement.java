import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EmployeeManagementSystem {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
                
            }

            EmployeeManagementFrame ventana = new EmployeeManagementFrame();
            ventana.setVisible(true);
        });
    }
}

class EmployeeManagementFrame extends JFrame {
    private static final String ARCHIVO_EMPLEADOS = "empleados.csv";
    private final ArrayList<Employee> empleados = new ArrayList<>();

    private JTextField campoId;
    private JTextField campoNombre;
    private JTextField campoDepartamento;
    private JTextField campoPuesto;
    private JTextField campoSalario;
    private JTextField campoBusqueda;

    private DefaultTableModel modeloTabla;
    private JTable tablaEmpleados;

    private JLabel etiquetaTotalEmpleados;
    private JLabel etiquetaNominaTotal;
    private JLabel etiquetaSalarioPromedio;
    private JLabel etiquetaSalarioMayor;

    public EmployeeManagementFrame() {
        setTitle("Sistema de Control de Empleados");
        setSize(1000, 680);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        crearInterfaz();
        cargarEmpleadosDesdeArchivo();
        actualizarTabla();
        actualizarEstadisticas();
    }

    private void crearInterfaz() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelTitulo = crearPanelTitulo();
        JPanel panelFormulario = crearPanelFormulario();
        JPanel panelTabla = crearPanelTabla();

        
        add(panelTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);
    }

    

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Control de Empleados"));
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        campoId = new JTextField();
        campoNombre = new JTextField();
        campoDepartamento = new JTextField();
        campoPuesto = new JTextField();
        campoSalario = new JTextField();
        
        panel.add(new JLabel("ID:")); panel.add(campoId);
        panel.add(new JLabel("Nombre:")); panel.add(campoNombre);
        panel.add(new JLabel("Departamento:")); panel.add(campoDepartamento);
        panel.add(new JLabel("Puesto:")); panel.add(campoPuesto);
        panel.add(new JLabel("Salario:")); panel.add(campoSalario);
        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        campoBusqueda = new JTextField();
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Depto", "Puesto", "Salario"}, 0);
        tablaEmpleados = new JTable(modeloTabla);
        
        panel.add(campoBusqueda, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);
        return panel;
    }

    private void cargarEmpleadosDesdeArchivo() {
       
    }

    private void actualizarTabla() {
        
    }

    private void actualizarEstadisticas() {
        
    }
}


class Employee {
    private String id;
    private String nombre;
    private String departamento;
    private String puesto;
    private double salario;

    
}
