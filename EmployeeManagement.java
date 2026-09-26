import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.Standard*harsets;
import java.util.ArrayLis*;

public class EmployeeManagement*ystem {

    public static void ma*n(String[] args) {

        SwingU*ilities.invokeLater(() -> {

     *      try {
                UIMana*er.setLookAndFeel(
               *        UIManager.getSystemLookAnd*eelClassName()
                );
*           } catch (Exception igno*ed) {
                // Si no se *uede aplicar el estilo del sistema*
                // Java utilizará*el estilo predeterminado.
        *   }

            EmployeeManageme*tFrame ventana =
                 *  new EmployeeManagementFrame();

*           ventana.setVisible(true*;
        });
    }
}

class Emplo*eeManagementFrame extends JFrame {*
    private static final String A*CHIVO_EMPLEADOS = "empleados.csv";*
    private final ArrayList<Emplo*ee> empleados =
            new Ar*ayList<>();

    private JTextFiel* campoId;
    private JTextField c*mpoNombre;
    private JTextField *ampoDepartamento;
    private JTex*Field campoPuesto;
    private JTe*tField campoSalario;
    private J*extField campoBusqueda;

    priva*e DefaultTableModel modeloTabla;
 *  private JTable tablaEmpleados;

*   private JLabel etiquetaTotalEmp*eados;
    private JLabel etiqueta*ominaTotal;
    private JLabel eti*uetaSalarioPromedio;
    private J*abel etiquetaSalarioMayor;

    pu*lic EmployeeManagementFrame() {

 *      setTitle("Sistema de Control*de Empleados");
        setSize(10*0, 680);
        setMinimumSize(ne* Dimension(900, 600));
        set*ocationRelativeTo(null);
        s*tDefaultCloseOperation(JFrame.EXIT*ON_CLOSE);

        crearInterfaz(*;
        cargarEmpleadosDesdeArch*vo();
        actualizarTabla();
 *      actualizarEstadisticas();
  * }

    private void crearInterfaz*) {

        setLayout(new BorderL*yout(10, 10));

        JPanel pan*lTitulo = crearPanelTitulo();
    *   JPanel panelFormulario = crearP*nelFormulario();
        JPanel pa*elTabla = crearPanelTabla();
     *  JPanel panelInferior = crearPane*Inferior();

        add(panelTitu*o, BorderLayout.NORTH);
        ad*(panelFormulario, BorderLayout.WES*);
        add(panelTabla, BorderL*yout.CENTER);
        add(panelInf*rior, BorderLayout.SOUTH);
    }

*   private JPanel crearPanelTitulo*) {

        JPanel panel = new JP*nel(new BorderLayout());
        p*nel.setBackground(new Color(25, 55* 90));
        panel.setBorder(
  *             BorderFactory.createE*ptyBorder(18, 20, 18, 20)
        *;

        JLabel titulo = new JLa*el(
                "SISTEMA DE CO*TROL DE EMPLEADOS"
        );

   *    titulo.setForeground(Color.WHI*E);
        titulo.setFont(
      *         new Font("Arial", Font.BO*D, 24)
        );

        JLabel *ubtitulo = new JLabel(
           *    "Administración de personal"
 *      );

        subtitulo.setFor*ground(new Color(210, 220, 235));
*       subtitulo.setFont(
        *       new Font("Arial", Font.PLAI*, 14)
        );

        JPanel t*xtos = new JPanel();
        texto*.setOpaque(false);
        textos.*etLayout(
                new BoxL*yout(textos, BoxLayout.Y_AXIS)
   *    );

        textos.add(titulo)*
        textos.add(Box.createVert*calStrut(5));
        textos.add(s*btitulo);

        panel.add(texto*, BorderLayout.WEST);

        ret*rn panel;
    }

    private JPane* crearPanelFormulario() {

       *JPanel panel = new JPanel(
       *        new GridBagLayout()
      * );

        panel.setPreferredSiz*(
                new Dimension(31*, 0)
        );

        panel.set*order(
                BorderFacto*y.createCompoundBorder(
          *             BorderFactory.createT*tledBorder(
                      *         "Información del empleado*
                        ),
      *                 BorderFactory.cre*teEmptyBorder(
                   *            10, 10, 10, 10
       *                )
                *
        );

        GridBagConstr*ints gbc =
                new Gri*BagConstraints();

        gbc.ins*ts = new Insets(6, 6, 6, 6);
     *  gbc.fill = GridBagConstraints.HO*IZONTAL;
        gbc.weightx = 1.0*
        gbc.gridx = 0;

        i*t fila = 0;

        gbc.gridy = f*la++;
        panel.add(new JLabel*"ID:"), gbc);

        campoId = n*w JTextField();
        gbc.gridy * fila++;
        panel.add(campoId* gbc);

        gbc.gridy = fila++*
        panel.add(new JLabel("Nom*re:"), gbc);

        campoNombre * new JTextField();
        gbc.gri*y = fila++;
        panel.add(camp*Nombre, gbc);

        gbc.gridy =*fila++;
        panel.add(new JLab*l("Departamento:"), gbc);

       *campoDepartamento = new JTextField*);
        gbc.gridy = fila++;
   *    panel.add(campoDepartamento, g*c);

        gbc.gridy = fila++;
 *      panel.add(new JLabel("Puesto*"), gbc);

        campoPuesto = n*w JTextField();
        gbc.gridy * fila++;
        panel.add(campoPu*sto, gbc);

        gbc.gridy = fi*a++;
        panel.add(new JLabel(*Salario:"), gbc);

        campoSa*ario = new JTextField();
        g*c.gridy = fila++;
        panel.ad*(campoSalario, gbc);

        JBut*on botonAgregar =
                *rearBoton("Agregar empleado",
    *                   new Color(34, 1*9, 94));

        botonAgregar.add*ctionListener(
                e -* agregarEmpleado()
        );

   *    gbc.gridy = fila++;
        gb*.insets = new Insets(16, 6, 5, 6);*        panel.add(botonAgregar, gb*);

        JButton botonActualiza* =
                crearBoton("Act*alizar empleado",
                *       new Color(40, 110, 180));

*       botonActualizar.addActionLi*tener(
                e -> actual*zarEmpleado()
        );

        *bc.gridy = fila++;
        gbc.ins*ts = new Insets(5, 6, 5, 6);
     *  panel.add(botonActualizar, gbc);*
        JButton botonEliminar =
 *              crearBoton("Eliminar*empleado",
                       *new Color(190, 60, 60));

        *otonEliminar.addActionListener(
  *             e -> eliminarEmpleado*)
        );

        gbc.gridy = *ila++;
        panel.add(botonElim*nar, gbc);

        JButton botonL*mpiar =
                crearBoton*"Limpiar campos",
                *       new Color(100, 100, 100));
*        botonLimpiar.addActionList*ner(
                e -> limpiarC*mpos()
        );

        gbc.gri*y = fila++;
        panel.add(boto*Limpiar, gbc);

        gbc.gridy * fila;
        gbc.weighty = 1.0;
*       panel.add(Box.createVertica*Glue(), gbc);

        return pane*;
    }

    private JPanel crearP*nelTabla() {

        JPanel panel*= new JPanel(
                new *orderLayout(8, 8)
        );

    *   panel.setBorder(
              * BorderFactory.createEmptyBorder(
*                       5, 5, 5, 10*                )
        );

    *   JPanel panelBusqueda = new JPan*l(
                new BorderLayou*(8, 8)
        );

        campoBu*queda = new JTextField();

       *JButton botonBuscar =
            *   crearBoton("Buscar", new Color(*0, 100, 160));

        botonBusca*.addActionListener(
              * e -> buscarEmpleado()
        );
*        JButton botonMostrarTodos *
                crearBoton("Mostr*r todos",
                        *ew Color(95, 95, 95));

        bo*onMostrarTodos.addActionListener(e*-> {
            campoBusqueda.set*ext("");
            actualizarTab*a();
        });

        JPanel b*tonesBusqueda = new JPanel(
      *         new FlowLayout(FlowLayout*RIGHT, 5, 0)
        );

        b*tonesBusqueda.add(botonBuscar);
  *     botonesBusqueda.add(botonMost*arTodos);

        panelBusqueda.a*d(
                new JLabel("Bus*ar por ID o nombre:"),
           *    BorderLayout.WEST
        );

*       panelBusqueda.add(
        *       campoBusqueda,
            *   BorderLayout.CENTER
        );
*        panelBusqueda.add(
       *        botonesBusqueda,
         *      BorderLayout.EAST
        );*
        String[] columnas = {
   *            "ID",
                *Nombre",
                "Departam*nto",
                "Puesto",
  *             "Salario"
        };
*        modeloTabla = new DefaultT*bleModel(
                columnas* 0
        ) {
            @Overri*e
            public boolean isCel*Editable(
                    int *ow,
                    int column*            ) {
                re*urn false;
            }
        }*

        tablaEmpleados = new JTa*le(modeloTabla);
        tablaEmpl*ados.setRowHeight(26);
        tab*aEmpleados.setSelectionMode(
     *          ListSelectionModel.SINGL*_SELECTION
        );

        tab*aEmpleados.getTableHeader().setFon*(
                new Font("Arial"* Font.BOLD, 13)
        );

      * tablaEmpleados.addMouseListener(
*               new MouseAdapter() *
                    @Override
   *                public void mouseC*icked(
                           *MouseEvent e
                    )*{
                        cargarEm*leadoSeleccionado();
             *      }
                }
        *;

        JScrollPane scrollPane *
                new JScrollPane(t*blaEmpleados);

        panel.add(*anelBusqueda, BorderLayout.NORTH);*        panel.add(scrollPane, Bord*rLayout.CENTER);

        return p*nel;
    }

    private JPanel cre*rPanelInferior() {

        JPanel*panel = new JPanel(
              * new GridLayout(1, 4, 10, 10)
    *   );

        panel.setBorder(
  *             BorderFactory.createE*ptyBorder(
                       *10, 10, 15, 10
                )
 *      );

        etiquetaTotalEmp*eados =
                crearTarje*aEstadistica(
                    *   "Total empleados",
            *           "0"
                );
*        etiquetaNominaTotal =
    *           crearTarjetaEstadistica*
                        "Nómina t*tal",
                        "$0.*0"
                );

        eti*uetaSalarioPromedio =
            *   crearTarjetaEstadistica(
      *                 "Salario promedio*,
                        "$0.00"
*               );

        etiquet*SalarioMayor =
                cre*rTarjetaEstadistica(
             *          "Salario más alto",
    *                   "$0.00"
       *        );

        panel.add(etiq*etaTotalEmpleados.getParent());
  *     panel.add(etiquetaNominaTotal*getParent());
        panel.add(et*quetaSalarioPromedio.getParent());*        panel.add(etiquetaSalarioM*yor.getParent());

        return *anel;
    }

    private JLabel cr*arTarjetaEstadistica(
            *tring titulo,
            String v*lor
    ) {

        JPanel tarjet* = new JPanel();
        tarjeta.s*tLayout(
                new BoxLa*out(tarjeta, BoxLayout.Y_AXIS)
   *    );

        tarjeta.setBackgro*nd(
                new Color(235,*240, 247)
        );

        tarj*ta.setBorder(
                Bord*rFactory.createCompoundBorder(
   *                    BorderFactory.*reateLineBorder(
                 *              new Color(200, 210, *20)
                        ),
   *                    BorderFactory.*reateEmptyBorder(
                *               10, 10, 10, 10
    *                   )
             *  )
        );

        JLabel eti*uetaTitulo =
                new J*abel(titulo);

        etiquetaTit*lo.setAlignmentX(
                *omponent.CENTER_ALIGNMENT
        *;

        etiquetaTitulo.setFont(*                new Font("Arial", *ont.PLAIN, 12)
        );

       *JLabel etiquetaValor =
           *    new JLabel(valor);

        et*quetaValor.setAlignmentX(
        *       Component.CENTER_ALIGNMENT
*       );

        etiquetaValor.s*tFont(
                new Font("A*ial", Font.BOLD, 18)
        );

 *      etiquetaValor.setForeground(*                new Color(25, 55, *0)
        );

        tarjeta.add*etiquetaTitulo);
        tarjeta.a*d(Box.createVerticalStrut(5));
   *    tarjeta.add(etiquetaValor);

 *      return etiquetaValor;
    }
*    private JButton crearBoton(
  *         String texto,
           *Color color
    ) {

        JButt*n boton = new JButton(texto);

   *    boton.setBackground(color);
  *     boton.setForeground(Color.WHI*E);
        boton.setFocusPainted(*alse);
        boton.setFont(
    *           new Font("Arial", Font.*OLD, 12)
        );

        retur* boton;
    }

    private void ag*egarEmpleado() {

        Employee*nuevoEmpleado =
                ob*enerEmpleadoDelFormulario();

    *   if (nuevoEmpleado == null) {
  *         return;
        }

      * if (buscarEmpleadoPorId(
        *       nuevoEmpleado.getId()) != n*ll) {

            mostrarError(
 *                  "Ya existe un em*leado con el ID "
                *           + nuevoEmpleado.getId()*+ "."
            );

            *eturn;
        }

        empleado*.add(nuevoEmpleado);

        guar*arEmpleadosEnArchivo();
        ac*ualizarTabla();
        actualizar*stadisticas();
        limpiarCamp*s();

        JOptionPane.showMess*geDialog(
                this,
  *             "Empleado agregado co*rectamente.",
                "Reg*stro exitoso",
                JOp*ionPane.INFORMATION_MESSAGE
      * );
    }

    private void actual*zarEmpleado() {

        String te*toId = campoId.getText().trim();

*       if (textoId.isEmpty()) {

 *          mostrarError(
          *         "Selecciona un empleado d* la tabla."
            );

      *     return;
        }

        in* id;

        try {
            id*= Integer.parseInt(textoId);

    *   } catch (NumberFormatException *) {

            mostrarError(
   *                "El ID debe ser un*número entero."
            );

  *         return;
        }

      * Employee empleadoExistente =
    *           buscarEmpleadoPorId(id)*

        if (empleadoExistente ==*null) {

            mostrarError(*                    "No se encontr* un empleado con ese ID."
        *   );

            return;
       *}

        String nombre =
       *        campoNombre.getText().trim*);

        String departamento =
*               campoDepartamento.g*tText().trim();

        String pu*sto =
                campoPuesto.*etText().trim();

        String t*xtoSalario =
                campo*alario.getText().trim();

        *f (nombre.isEmpty()
              * || departamento.isEmpty()
       *        || puesto.isEmpty()
      *         || textoSalario.isEmpty()* {

            mostrarError(
    *               "Todos los campos s*n obligatorios."
            );

 *          return;
        }

     *  double salario;

        try {
 *          salario = Double.parseDo*ble(
                    textoSala*io.replace(",", ".")
            )*

        } catch (NumberFormatExc*ption e) {

            mostrarErr*r(
                    "El salario*debe ser un número válido."
      *     );

            return;
     *  }

        if (salario < 0) {

 *          mostrarError(
          *         "El salario no puede ser *egativo."
            );

        *   return;
        }

        empl*adoExistente.setNombre(nombre);
  *     empleadoExistente.setDepartam*nto(
                departamento
*       );

        empleadoExisten*e.setPuesto(puesto);
        emple*doExistente.setSalario(salario);

*       guardarEmpleadosEnArchivo()*
        actualizarTabla();
      * actualizarEstadisticas();
       *limpiarCampos();

        JOptionP*ne.showMessageDialog(
            *   this,
                "Empleado*actualizado correctamente.",
     *          "Actualización exitosa",*                JOptionPane.INFORM*TION_MESSAGE
        );
    }

   *private void eliminarEmpleado() {
*        int filaSeleccionada =
   *            tablaEmpleados.getSele*tedRow();

        if (filaSelecci*nada == -1) {

            mostrar*rror(
                    "Selecci*na un empleado de la tabla."
     *      );

            return;
    *   }

        int id = (int) model*Tabla.getValueAt(
                *ilaSeleccionada, 0
        );

   *    Employee empleado =
          *     buscarEmpleadoPorId(id);

   *    if (empleado == null) {

     *      mostrarError(
              *     "No se encontró el empleado."*            );

            return*
        }

        int respuesta *
                JOptionPane.showC*nfirmDialog(
                     *  this,
                        "¿*eseas eliminar a "
               *                + empleado.getNomb*e()
                              * + "?",
                        "C*nfirmar eliminación",
            *           JOptionPane.YES_NO_OPTI*N,
                        JOption*ane.WARNING_MESSAGE
              * );

        if (respuesta == JOpt*onPane.YES_OPTION) {

            *mpleados.remove(empleado);

      *     guardarEmpleadosEnArchivo();
*           actualizarTabla();
    *       actualizarEstadisticas();
 *          limpiarCampos();

      *     JOptionPane.showMessageDialog*
                    this,
       *            "Empleado eliminado co*rectamente.",
                    *Eliminación exitosa",
            *       JOptionPane.INFORMATION_MES*AGE
            );
        }
    }*
    private void buscarEmpleado()*{

        String criterio =
     *          campoBusqueda.getText()
*                       .trim()
   *                    .toLowerCase()*

        if (criterio.isEmpty()) *

            actualizarTabla();
 *          return;
        }

     *  modeloTabla.setRowCount(0);

   *    for (Employee empleado : emple*dos) {

            boolean coinci*eId =
                    String.v*lueOf(empleado.getId())
          *                 .equals(criterio)*

            boolean coincideNomb*e =
                    empleado.g*tNombre()
                        *   .toLowerCase()
                *           .contains(criterio);

 *          if (coincideId || coinci*eNombre) {

                agrega*EmpleadoATabla(empleado);
        *   }
        }

        if (modelo*abla.getRowCount() == 0) {

      *     JOptionPane.showMessageDialog*
                    this,
       *            "No se encontraron emp*eados.",
                    "Resu*tado de búsqueda",
               *    JOptionPane.INFORMATION_MESSAG*
            );
        }
    }

 *  private Employee obtenerEmpleado*elFormulario() {

        String t*xtoId =
                campoId.ge*Text().trim();

        String nom*re =
                campoNombre.g*tText().trim();

        String de*artamento =
                campoD*partamento.getText().trim();

    *   String puesto =
               *campoPuesto.getText().trim();

   *    String textoSalario =
        *       campoSalario.getText().trim*);

        if (textoId.isEmpty()
*               || nombre.isEmpty()*                || departamento.is*mpty()
                || puesto.i*Empty()
                || textoSa*ario.isEmpty()) {

            mos*rarError(
                    "Tod*s los campos son obligatorios."
  *         );

            return nu*l;
        }

        int id;
    *   double salario;

        try {
*           id = Integer.parseInt(t*xtoId);

        } catch (NumberFo*matException e) {

            mos*rarError(
                    "El *D debe ser un número entero."
    *       );

            return null*
        }

        try {
        *   salario = Double.parseDouble(
 *                  textoSalario.rep*ace(",", ".")
            );

    *   } catch (NumberFormatException *) {

            mostrarError(
   *                "El salario debe s*r un número válido."
            )*

            return null;
       *}

        if (id <= 0) {

       *    mostrarError(
                *   "El ID debe ser mayor que cero.*
            );

            retur* null;
        }

        if (sala*io < 0) {

            mostrarErro*(
                    "El salario *o puede ser negativo."
           *);

            return null;
     *  }

        return new Employee(
*               id,
               *nombre,
                departamen*o,
                puesto,
       *        salario
        );
    }

    private Employee buscarEmpleadoPorId(int id) {

        for (Employee empleado : empleados) {

            if (empleado.getId() == id) {
                return empleado;
            }
        }

        return null;
    }

    private void cargarEmpleadoSeleccionado() {

        int fila =
                tablaEmpleados.getSelectedRow();

        if (fila == -1) {
            return;
        }

        campoId.setText(
                modeloTabla.getValueAt(
                        fila, 0
                ).toString()
        );

        campoNombre.setText(
                modeloTabla.getValueAt(
                        fila, 1
                ).toString()
        );

        campoDepartamento.setText(
                modeloTabla.getValueAt(
                        fila, 2
                ).toString()
        );

        campoPuesto.setText(
                modeloTabla.getValueAt(
                        fila, 3
                ).toString()
        );

        campoSalario.setText(
                modeloTabla.getValueAt(
                        fila, 4
                ).toString()
        );

        campoId.setEditable(false);
    }

    private void limpiarCampos() {

        campoId.setText("");
        campoNombre.setText("");
        campoDepartamento.setText("");
        campoPuesto.setText("");
        campoSalario.setText("");

        campoId.setEditable(true);
        tablaEmpleados.clearSelection();
        campoId.requestFocus();
    }

    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Employee empleado : empleados) {
            agregarEmpleadoATabla(empleado);
        }
    }

    private void agregarEmpleadoATabla(
            Employee empleado
    ) {

        Object[] fila = {
                empleado.getId(),
                empleado.getNombre(),
                empleado.getDepartamento(),
                empleado.getPuesto(),
                empleado.getSalario()
        };

        modeloTabla.addRow(fila);
    }

    private void actualizarEstadisticas() {

        int totalEmpleados = empleados.size();
        double nominaTotal = 0;
        double salarioMayor = 0;

        for (Employee empleado : empleados) {

            nominaTotal += empleado.getSalario();

            if (empleado.getSalario()
                    > salarioMayor) {

                salarioMayor =
                        empleado.getSalario();
            }
        }

        double salarioPromedio =
                totalEmpleados == 0
                        ? 0
                        : nominaTotal / totalEmpleados;

        etiquetaTotalEmpleados.setText(
                String.valueOf(totalEmpleados)
        );

        etiquetaNominaTotal.setText(
                String.format("$%,.2f", nominaTotal)
        );

        etiquetaSalarioPromedio.setText(
                String.format(
                        "$%,.2f",
                        salarioPromedio
                )
        );

        etiquetaSalarioMayor.setText(
                String.format("$%,.2f", salarioMayor)
        );
    }

    private void guardarEmpleadosEnArchivo() {

        try (
                BufferedWriter escritor =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        new FileOutputStream(
                                                ARCHIVO_EMPLEADOS
                                        ),
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            for (Employee empleado : empleados) {

                escritor.write(
                        empleado.toCsv()
                );

                escritor.newLine();
            }

        } catch (IOException e) {

            mostrarError(
                    "No fue posible guardar los empleados.\n"
                            + e.getMessage()
            );
        }
    }

    private void cargarEmpleadosDesdeArchivo() {

        File archivo =
                new File(ARCHIVO_EMPLEADOS);

        if (!archivo.exists()) {
            return;
        }

        try (
                BufferedReader lector =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(
                                                archivo
                                        ),
                                        StandardCharsets.UTF_8
                                )
                        )
        ) {

            String linea;

            while ((linea = lector.readLine())
                    != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                try {

                    Employee empleado =
                            Employee.fromCsv(linea);

                    if (buscarEmpleadoPorId(
                            empleado.getId()) == null) {

                        empleados.add(empleado);
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Registro ignorado: "
                                    + linea
                    );
                }
            }

        } catch (IOException e) {

            mostrarError(
                    "No fue posible cargar los empleados.\n"
                            + e.getMessage()
            );
        }
    }

    private void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

class Employee {

    private int id;
    private String nombre;
    private String departamento;
    private String puesto;
    private double salario;

    public Employee(
            int id,
            String nombre,
            String departamento,
            String puesto,
            double salario
    ) {

        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.puesto = puesto;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getPuesto() {
        return puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDepartamento(
            String departamento
    ) {
        this.departamento = departamento;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String toCsv() {

        return id
                + ";"
                + limpiarTexto(nombre)
                + ";"
                + limpiarTexto(departamento)
                + ";"
                + limpiarTexto(puesto)
                + ";"
                + salario;
    }

    public static Employee fromCsv(
            String linea
    ) {

        String[] datos =
                linea.split(";", -1);

        if (datos.length != 5) {

            throw new IllegalArgumentException(
                    "Registro inválido."
            );
        }

        int id =
                Integer.parseInt(datos[0]);

        String nombre = datos[1];
        String departamento = datos[2];
        String puesto = datos[3];

        double salario =
                Double.parseDouble(datos[4]);

        return new Employee(
                id,
                nombre,
                departamento,
                puesto,
                salario
        );
    }

    private String limpiarTexto(String texto) {

        return texto
                .replace(";", ",")
                .replace("\n", " ")
                .replace("\r", " ");
    }

    @Override
    public String toString() {

        return "Employee{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                '}';
    }
}