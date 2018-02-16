/**
 * Se escriben las librerias que van a ser usadas
 */

import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.util.StringTokenizer;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * ----Fin de librerias---
 */

public class ClienteGUI extends JFrame implements ActionListener
{
  private JTextField tfNocta, tfNombre, tfTipo, tfSaldo, tfFecha, tfHora, tfFechaApertura;
  private JButton bCapturar, bConsultar, bSalir, bDeposito, bRetiros;
  private JButton bConsultarNocta, bCancelar;
  private JButton bConsultarDepositos, bConsultarRetiros;
  private JPanel panel1, panel2;
  private JTextArea taDatos;
  //private Calendar calendar;
  //private Date date;

  JComboBox combo = new JComboBox();

  //private BancoAD bancoad = new BancoAD();
  private BancoADjdbc bancoadjdbc = new BancoADjdbc();

  public ClienteGUI()
  {
    super("Admin. de Clientes");

    // 1. Crear los objetos de los atributos
    tfNocta = new JTextField();
    tfNombre = new JTextField();
    tfTipo = new JTextField();
    tfSaldo = new JTextField();

    bConsultarNocta = new JButton("Consultar No. Cuenta");
    bCancelar     = new JButton("Cancelar");
    bConsultarDepositos = new JButton("Consular Depositos");
    bConsultarRetiros = new JButton("Consultar Retiros");

    bCapturar = new JButton("Capturar Datos");
    bConsultar = new JButton("Consultar Datos");
    bDeposito = new JButton("Depositar");
    bRetiros = new JButton("Retirar");
    bSalir = new JButton("Exit");

    panel1 = new JPanel();
    panel2 = new JPanel();

    taDatos = new JTextArea(25,35);

    bSalir.addActionListener(this);
    bConsultar.addActionListener(this);
    bCapturar.addActionListener(this);
    bDeposito.addActionListener(this);
    bRetiros.addActionListener(this);
    bConsultarNocta.addActionListener(this);
    bCancelar.addActionListener(this);
    bConsultarDepositos.addActionListener(this);
    bConsultarRetiros.addActionListener(this);
    combo.addActionListener(this);

    bRetiros.setEnabled(false);
    bDeposito.setEnabled(false);
    bCancelar.setEnabled(false);

    // 2. Definir los Layouts de los JPanels
    panel1.setLayout(new GridLayout(11,2));
    panel2.setLayout(new FlowLayout());
    // 3. Colocar los objetos de los atributos en los JPanels correspondientes
    panel1.add(new JLabel("No. DE CUENTA"));
    panel1.add(tfNocta);
    panel1.add(new JLabel("NOMBRE"));
    panel1.add(tfNombre);
    panel1.add(new JLabel("TIPO DE CUENTA"));
    panel1.add(combo);
    combo.addItem("AHORRO");
    combo.addItem("CREDITO");
    combo.addItem("HIPOTECA");
    combo.addItem("INVERSION");
    //panel1.add(tfTipo);
    panel1.add(new JLabel("SALDO"));
    panel1.add(tfSaldo);

    panel1.add(bCapturar);
    panel1.add(bConsultar);
    panel1.add(bConsultarNocta);
    panel1.add(bDeposito);
    panel1.add(bRetiros);
    panel1.add(bCancelar);
    panel1.add(bConsultarDepositos);
    panel1.add(bConsultarRetiros);
    panel1.add(bSalir);

    panel2.add(panel1);
    panel2.add(new JScrollPane(taDatos));
    // 4. Adicionar el panel1 al panel1
    add(panel2);
    setSize(500,500);
    setVisible(true);
    // 5. Adicional el panel2 al JFrame y hacerlo visible
    setSize(500,500);
    setVisible(true);
  }

  /**
   * [inactivarBotones description]
   *  Inactiva los botones (CAPTURAR, CONSULTAR, CONSULTARNOCUENTA)
   *  Activa los botones (RETIROS, DEPOSITOS, CANCELAR)
   */
  private void inactivarBotones()
  {
      bCapturar.setEnabled(false);
      bConsultar.setEnabled(false);
      bConsultarNocta.setEnabled(false);

      bRetiros.setEnabled(true);
      bDeposito.setEnabled(true);
      bCancelar.setEnabled(true);
  }

  /**
   * [activarBotones description]
   * Activa los botones (CAPTURAR, CONSULTAR, CONSULTARNOCUENTA)
   * Inactiva los botones (RETIROS, DEPOSITOS, CANCELAR)
   */
  private void activarBotones()
  {
      bCapturar.setEnabled(true);
      bConsultar.setEnabled(true);
      bConsultarNocta.setEnabled(true);

      bRetiros.setEnabled(false);
      bDeposito.setEnabled(false);
      bCancelar.setEnabled(false);
  }

  public void desplegar(String datos)
  {
      StringTokenizer st = new StringTokenizer(datos,"_");

      tfNocta.setText(st.nextToken());
      tfNombre.setText(st.nextToken());
      String tipo = st.nextToken();
      combo.setSelectedItem(tipo);
      tfSaldo.setText(st.nextToken());
      tfFecha.setText(st.nextToken());
      tfHora.setText(st.nextToken());
  }

  public void desplegar2(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");

		tfNocta.setText(st.nextToken());
		tfNombre.setText(st.nextToken());
		tfTipo.setText(st.nextToken());
		tfSaldo.setText(st.nextToken());
		tfFechaApertura.setText(st.nextToken()+" "+st.nextToken());
	}

  /**
   * [obtenerDatos description]
   * Obtiene los datos escritos en los Jlabels y los organiza con StringTokenizer para ser manipulados más facilmente
   * de misma manera en esta funcion se hace la creacion de la fecha y en la hora en la cual se hizo el registro de
   * crear un nuevo cliente.
   * @return [description]
   * Regresa 'datos' = El valor de los datos obtenidos para futuras funciones
   */
  private String obtenerDatos()
  {
    String datos= "";
    String nocta = tfNocta.getText();
    String nombre = tfNombre.getText();
    //String tipo  = tfTipo.getText();
    String tipo   = (String)combo.getSelectedItem();
    String saldo = tfSaldo.getText();

    String fecha = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
    String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

    if(nocta.equals("") || nombre.equals("") || tipo.isEmpty() || saldo.isEmpty())
      datos = "VACIO";
      else
      {
        try
        {
          int n = Integer.parseInt(saldo);
          datos = nocta+"_"+nombre+"_"+tipo+"_"+saldo+"_"+fecha+"_"+hora;
        }
        catch(NumberFormatException nfe)
        {
          datos = "NO_NUMERICO";
        }
      }
      return datos;
  }

  /**
   * [actionPerformed description]
   * Aquí se encuentran el funcionamiento que tiene cada boton del programa
   * @param ActionEvent e [description]
   * Va a ocurrir la funcion de cada boton siempre y cuando se obtenga el evento 'e' comparandolo con el boton precionado
   */
  public void actionPerformed(ActionEvent e)
  {
    String datos,respuesta;
    /**
     * [if Capturar description]
     * Como funciona el boton Capturar (captura de datos - bancoadjdbc funcion [capturar])
     */
    if(e.getSource() == bCapturar)
    {
      // 1. Obtene Datos
      datos = obtenerDatos();
      // 2. Checar Datos: Datos no vacios y saldo númerico y realizar la captura de datos
      if(datos.equals("VACIO"))
        respuesta = "Algun campo esta vacio...";
      else
        if(datos.equals("NO_NUMERICO"))
          respuesta = "Saldo debe ser numerico...";
        else
          respuesta = bancoadjdbc.capturar(datos);
        // 3. Desplegar el resultado de transaccion
      taDatos.setText(respuesta);
    }
    /**
     * ----FIN CAPTURAR----
     */

    /**
     * [if Consultar description]
     * Como funciona el boton Consultar (consulta general - bancoadjdbc funcion [consultarClientes])
     */
    if(e.getSource() == bConsultar)
    {
        datos = bancoadjdbc.consultarClientes();

        taDatos.setText(datos);
    }
    /**
     * ----FIN CONSULTAR (GENERAL)----
     */

    /**
     * [if Consultar No. Cuenta description]
     * Como funciona el boton consultar No. Cuenta (consulta el No. de Cuenta y habilita botones - bancoadjdbc funcion [consultarCuenta])
     */
    if(e.getSource() == bConsultarNocta)
    {
      //1. Obtener numero de la cuenta de tfNocta
      String ncta = tfNocta.getText();
      //2. Realizar transaccion de consultar no. de cuenta
      respuesta = bancoadjdbc.consultarCuenta(ncta);
      if(respuesta.equals("No_Localizado"))
      {
        respuesta = "No. de cuenta no localizado: "+ncta;
      }
      else
      {
        inactivarBotones();
      }
      //3. Desplegar resultado de la transaccion
      taDatos.setText(respuesta);
    }
    /**
     * ----FIN CONSULTAR (CUENTA)----
     */

    /**
     * [if Deposito description]
     * Como funciona el deposito (Aparte de depositar se añade a la tabla de depositos y se registra el deposito hecho - bancoadjdbc funcion [deposito])
     */
    if(e.getSource() == bDeposito)
    {
      String cta = tfNocta.getText();
        if(cta.isEmpty())
          JOptionPane.showMessageDialog(null,"Ingresa numero de cuenta");
      String fecha = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
      String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

       datos = bancoadjdbc.deposito(cta,0,fecha, hora);
       	taDatos.setText(datos);
        activarBotones();
    }
    /**
     * ----FIN DEPOSITO----
     */

     /**
      * [if Deposito description]
      * Como funciona el retiro (Aparte de depositar se añade a la tabla de retiro y se registra el retiro hecho - bancoadjdbc funcion [retiro])
      */
    if(e.getSource() == bRetiros)
    {
      String cta = tfNocta.getText();
				if(cta.isEmpty())
					JOptionPane.showMessageDialog(null,"Ingresa numero de cuenta");
			String fecha = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
			String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

		 datos = bancoadjdbc.retiro(cta,0,fecha, hora);
			taDatos.setText(datos);
        activarBotones();
    }
    /**
     * ----FIN RETIRO----
     */

     /**
      * [if Consultar Retiros description]
      * Como funciona ConsultarRetiros (Imprime los retiros hechos de todas las cuentas - bancoadjdbc funcion [consultarRetiro])
      */
    if(e.getSource() == bConsultarRetiros)
    {
     datos = bancoadjdbc.consultarRetiro();
			if(datos.isEmpty())
				JOptionPane.showMessageDialog(null,"No se localizaron retiros");
			else
      {
				taDatos.setText(datos);
			}
    }
    /**
     * ----FIN CONSULTAR RETIRO----
     */

     /**
      * [if Consultar Depositos description]
      * Como funciona ConsultarDepositos (Imprime los depositos hechos de todas las cuentas - bancoadjdbc funcion [consultarDeposito])
      */
    if(e.getSource() == bConsultarDepositos)
    {
       datos = bancoadjdbc.consultarDeposito();
			if(datos.isEmpty())
				JOptionPane.showMessageDialog(null,"No se localizaron depositos");
			else
      {
        taDatos.setText(datos);
			}
    }
    /**
     * ----FIN CONSULTAR DEPOSITO----
     */

    /**
     * [if Cancelar description]
     * Como funciona Cancelar (Una vez siendo habilitado por el boton Consultar No Cuenta permite al usuario cancelar su operacion)
     */
    if(e.getSource() == bCancelar)
    {
        activarBotones();
    }
    /**
     * ----FIN CANCELAR----
     */
    if(e.getSource() == bSalir)
    {
        System.exit(0);
    }
  }

   public static void main(String args[])
   {
     new ClienteGUI();
   }
}
