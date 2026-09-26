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
        JPanel panelEstadisticas = crearPanelEstadisticas();

        add(panelTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);
        add(panelEstadisticas, BorderLayout.SOUTH);
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
        
        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(e -> agregarEmpleadoManual());
        panel.add(botonAgregar);
        
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

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
        etiquetaTotalEmpleados = new JLabel("Total Empleados: 0");
        etiquetaNominaTotal = new JLabel("Nómina Total: $0.00");
        etiquetaSalarioPromedio = new JLabel("Salario Promedio: $0.00");
        etiquetaSalarioMayor = new JLabel("Salario Mayor: $0.00");

        panel.add(etiquetaTotalEmpleados);
        panel.add(etiquetaNominaTotal);
        panel.add(etiquetaSalarioPromedio);
        panel.add(etiquetaSalarioMayor);
        return panel;
    }

    private void agregarEmpleadoManual() {
        try {
            String id = campoId.getText();
            String nombre = campoNombre.getText();
            String depto = campoDepartamento.getText();
            String puesto = campoPuesto.getText();
            double salario = Double.parseDouble(campoSalario.getText());

            empleados.add(new Employee(id, nombre, depto, puesto, salario));
            actualizarTabla();
            actualizarEstadisticas();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un salario válido.");
        }
    }

    private void cargarEmpleadosDesdeArchivo() {
        File archivo = new File(ARCHIVO_EMPLEADOS);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String id = datos[0].trim();
                    String nombre = datos[1].trim();
                    String depto = datos[2].trim();
                    String puesto = datos[3].trim();
                    double salario = Double.parseDouble(datos[4].trim());
                    empleados.add(new Employee(id, nombre, depto, puesto, salario));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar archivo: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Employee emp : empleados) {
            modeloTabla.addRow(new Object[]{
                emp.getId(),
                emp.getNombre(),
                emp.getDepartamento(),
                emp.getPuesto(),
                emp.getSalario()
            });
        }
    }

    private void actualizarEstadisticas() {
        if (empleados.isEmpty()) {
            etiquetaTotalEmpleados.setText("Total Empleados: 0");
            etiquetaNominaTotal.setText("Nómina Total: $0.00");
            etiquetaSalarioPromedio.setText("Salario Promedio: $0.00");
            etiquetaSalarioMayor.setText("Salario Mayor: $0.00");
            return;
        }

        double nominaTotal = 0;
        double salarioMayor = 0;

        for (Employee emp : empleados) {
            double sal = emp.getSalario();
            nominaTotal += sal;
            if (sal > salarioMayor) {
                salarioMayor = sal;
            }
        }

        double promedio = nominaTotal / empleados.size();

        etiquetaTotalEmpleados.setText("Total Empleados: " + empleados.size());
        etiquetaNominaTotal.setText(String.format("Nómina Total: $%.2f", nominaTotal));
        etiquetaSalarioPromedio.setText(String.format("Salario Promedio: $%.2f", promedio));
        etiquetaSalarioMayor.setText(String.format("Salario Mayor: $%.2f", salarioMayor));
    }
}

class Employee {
    private final String id;
    private final String nombre;
    private final String departamento;
    private final String puesto;
    private final double salario;

    public Employee(String id, String nombre, String departamento, String puesto, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.puesto = puesto;
        this.salario = salario;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDepartamento() { return departamento; }
    public String getPuesto() { return puesto; }
    public double getSalario() { return salario; }
}
