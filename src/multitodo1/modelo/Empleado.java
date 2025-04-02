/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multitodo1.modelo;
import com.mysql.cj.protocol.Resultset;
import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ferna
 */

public class Empleado  extends Gerente{
    
    JTextField ced;
    JTextField nom;
    JTextField ape;
    JTextField cor;
    JDateChooser fec;
    JComboBox dep;
    
    
    String ce, no, ap , co , de;
    Calendar fe;
    
    public Empleado(){
        
    }
    
    
    public Empleado(JTextField ced,JTextField nom,JTextField ape,JTextField cor,JDateChooser fec, JComboBox dep){
        
        this.ced = ced;
        this.nom = nom;
        this.ape = ape;
        this.cor = cor;
        this.fec = fec;
        this.dep = dep;
        
        
        ce = ced.getText();
        no = nom.getText();
        ap = ape.getText();
        co = cor.getText();
        fe = fec.getCalendar();
        de = dep.getSelectedItem().toString();
        
        
    }
    
     public boolean Consulta_Loguear(String usu, String cla){
       
        boolean estado = false;
         
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://Localhost/inicio_multitodo","root","")){
            
            PreparedStatement ingresar = conexion.prepareStatement("SELECT * FROM empleados WHERE Cedula = ? AND clave = ?");
            ingresar.setString(1, usu);
            ingresar.setString(2, cla);
            ResultSet ingresado = ingresar.executeQuery();
            
            if(ingresado.next()){
                
             estado = true;
                
            }
            
            
            
            
            
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error Consulta Loguear" + e);
        }
        

        return estado;
    }
     
     
    public int Get_ID_Empleado(String ced){
        
        int id = 0;
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://Localhost/inicio_multitodo","root","")){
            
            PreparedStatement obtener = conexion.prepareStatement("SELECT Id_departamento FROM empleados WHERE Cedula = ?");
            obtener.setString(1, ced);
            ResultSet obtenido = obtener.executeQuery();
            
            if(obtenido.next()){
                
                id= obtenido.getInt(1);
                
            }
            
            
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error get_id_empleado");
        }
        
        return id;
        
    }
     
     
     public void Loguear(String usuario, String clave){
         
         
         if(!usuario.isEmpty() && !clave.isEmpty()){
             
             boolean ver_cre =  Consulta_Loguear(usuario, clave);
             if(ver_cre == true){
                 
                 
                 int id = Get_ID_Empleado(usuario);
                 String departamento = getDepartamento(id);
                 
                 if(departamento.equals("Gerencia")){
                     
                    multitodo1.vista.Gerente.Index_gerente ger = new  multitodo1.vista.Gerente.Index_gerente();
                    multitodo1.vista.Index.Index index = new  multitodo1.vista.Index.Index();
                    
                    
                    ger.setVisible(true);
                    index.dispose();
                    
                 }else if(departamento.equals("Contabilidad")){
                     
                   multitodo1.vista.Empleado.Menu_Cajero emp = new   multitodo1.vista.Empleado.Menu_Cajero();
                    multitodo1.vista.Index.Index index = new  multitodo1.vista.Index.Index();
                    
                    
                    emp.setVisible(true);
                    
                     
                     
                     
                 }else{
                     
                     JOptionPane.showMessageDialog(null, "Lo sentimos, su departamento no contiene platafora, estamos trabajando para crear una plataforma para su departamento");
                 }
                 
                 
                 
             }
             
             
         }else{
             JOptionPane.showMessageDialog(null, "Por favor ingrese todos las credenciales de ingreso");
         }
         
         
         
     }
     
     
    
    public boolean verificar_campo_Empleados(String campo, String obj){
        
        boolean res = false;
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            
            PreparedStatement verificar = conexion.prepareStatement("SELECT * FROM empleados WHERE " + campo + " = ?");
            verificar.setString(1, obj);
            ResultSet verificado = verificar.executeQuery();
            
            if(verificado.next()){
                
                
                res = true;
                
            }
            
            
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "error verificar_one" + e.getMessage());
            
        }
        
        
        return res;
        
    }
    
    
    
    
    
    
    
     public boolean Registrar(){
        
        
        boolean ok = false;
        
       
        
        
        
        
        if(ce.isEmpty() && no.isEmpty() && ap.isEmpty() && co.isEmpty() && fe == null && de.equals("SELECCIONAR") ){
       
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
            
            
        }else{
            
            boolean verificar_c = verificar_campo_Empleados("Cedula", ce);
            
            if(verificar_c == false){
                
                boolean verificar_co = verificar_campo_Empleados("Correo", co);
                
                if(verificar_co == false){
                    
                    int año = fe.get(Calendar.YEAR);
                    int mes = fe.get(Calendar.MONTH) + 1;
                    int dia = fe.get(Calendar.DAY_OF_MONTH);
                    
                    String fe_nac =  año + "-" + mes +  "-" + dia;
                    
                    int id = get_Id_Departamento(de);
                    
                    try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
                        
                        PreparedStatement insertar = conexion.prepareStatement("INSERT INTO empleados VALUES(?,?,?,?,?,'',?)");
                        insertar.setString(1, ce);
                        insertar.setString(2, no);
                        insertar.setString(3, ap);
                        insertar.setString(4, co);
                        insertar.setString(5, fe_nac);
                        insertar.setInt(6, id);
                        
                        int insertado = insertar.executeUpdate();
                        
                        if(insertado > 0){
                            
                            JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
                            
                            ok = true;
                        }
                        
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "error registrar" + e.getMessage());
                    }
                    
                    
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "El correo ya se encuentra en uso digite otro");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "No se puede registrar porque la cedula ingresada ya existe");
            }
            
            
            
        }
        
        
        return ok;
    }
     
     
     public boolean Pre_actualizar(String campo, String obj, String ced){
        
        boolean ok = false;
        
        
          try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
                    
                    PreparedStatement actualizar = conexion.prepareStatement("UPDATE empleados SET " + campo + " = ? WHERE Cedula = ? " );
                    actualizar.setString(1, obj);
                    actualizar.setString(2, ced);
                    int actualizado = actualizar.executeUpdate();
                    
                    if(actualizado > 0){
                        
                        ok = true;
                        
                    }
                    
                    
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error Pre_actualizar" + e.getMessage());
                    
                }
        return ok;
        
    }
    
    
     
     
     public void Actualizar(JTable tab){
     
        
        int fila_s = tab.getSelectedRow();
        
        if(fila_s == -1){
            
            JOptionPane.showMessageDialog(null, "Por favor elija una fila antes de actualizar");
            
            
        }else{
            
          
            
            if(ce.isEmpty() && no.isEmpty() && ap.isEmpty() && co.isEmpty() && fe == null && de.equals("SELECCIONAR") ){
                
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
            }else{
                
                
               int mensaje_c = JOptionPane.showConfirmDialog(null, "Actualizar","¿Esta seguro de actualizar los datos del cliente?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
               
               if(mensaje_c == JOptionPane.YES_OPTION){
                   
               
               
               
                
                String cedula = tab.getValueAt(fila_s, 0).toString();
                
                String mensaje[] = new String[10];
                
                String ini = "";
                String fin = "";
                
                int inicio = 0;
                
                
                if(!ce.isEmpty()){
                    
                    if(inicio == 0){
                        
                        ini = "Cedula";
                        inicio = 1;
                    }else{
                        fin = "Cedula";
                    }
                    
                    Pre_actualizar("Cedula", ce, cedula);
                    
                    mensaje[0] = "Cedula";
                    
                }
                
                if(!no.isEmpty()){
                    Pre_actualizar("Nombre", no, cedula);
                    
                     if(inicio == 0){
                        
                        ini = "Nombre";
                        inicio = 1;
                    }else{
                        fin = "Nombre";
                    }
                    
                  
                    
                    mensaje[1] = "Nombre";
                    
                    
                }
                
                if(!ap.isEmpty()){
                    Pre_actualizar("Apellidos", ap, cedula);
                    
                     if(inicio == 0){
                        
                        ini = "Apellidos";
                        inicio = 1;
                    }else{
                        fin = "Apellidos";
                    }
                    
                  
                    
                    mensaje[2] = "Apellidos";
                }
                
                
                if(!co.isEmpty()){
                    Pre_actualizar("Correo", co, cedula);
                    
                     if(inicio == 0){
                        
                        ini = "Correo";
                        inicio = 1;
                    }else{
                        fin = "Correo";
                    }
                    
                  
                    
                    mensaje[3] = "Correo";
                    
                    
                    
                }
                
                if(fe != null){
                    
                    int año = fe.get(Calendar.YEAR);
                    int mes = fe.get(Calendar.MONTH);
                    int dia = fe.get(Calendar.DAY_OF_MONTH);
                    
                    String fe_na = año + "-" + mes + "-" + dia;
                    
                    Pre_actualizar("Fecha_de_nacimiento", fe_na, cedula);
                    
                     if(inicio == 0){
                        
                        ini = "Fecha de nacimiento";
                        inicio = 1;
                    }else{
                        fin = "Fecha de nacimiento";
                    }
                    
                  
                    
                    mensaje[4] = "Fecha de nacimiento";
                    
                    
                    
                }
                
                if(!de.equals("SELECCIONAR")){
                    
                    int id = get_Id_Departamento(de);
                    String ids = String.valueOf(id);
                    
                    Pre_actualizar("Id_departamento", ids, cedula);
                    
                     if(inicio == 0){
                        
                        ini = "Departamento";
                        inicio = 1;
                    }else{
                        fin = "Departamento";
                    }
                    
                  
                    
                    mensaje[5] = "Departamento";
                    
                    
                }
                
                
                String mensaje_def = "";
                
                for(int i = 0; i < mensaje.length; i++){
                    
                    if(mensaje[i] == ini){
                        
                        mensaje_def += mensaje[i];
                        
                    }else if(mensaje[i] == fin){
                        
                        mensaje_def += "y" + mensaje[i];
                    }else if(mensaje[i] != null){
                        mensaje_def += "," + mensaje[i];
                    }
                    
                }
                
                JOptionPane.showMessageDialog(null, "Se han actualizado los campos: " + mensaje_def);
                    
                
                
                }
            }
            
        }
        
    }
    
     public void Actualizar2(String idn){
   
      
        if(ce.isEmpty() || no.isEmpty() || ap.isEmpty() || co.isEmpty() || fe == null || dep.equals("SELCCIONAR") ){
            
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
        }else{
            
            
            int año = fe.get(Calendar.YEAR);
            int mes = fe.get(Calendar.MONTH) +1;
            int dia = fe.get(Calendar.DAY_OF_MONTH);
            
            String f =  año + "-" + mes + "-" + dia;
            
            
            int id = get_Id_Departamento(de);
            
            
            
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
                
                
                
            PreparedStatement Actualizar = conexion.prepareStatement("UPDATE empleados SET Cedula = ?, nombre = ?,  Apellidos = ?,  Correo = ?,  Fecha_de_nacimiento = ?,  ID_departamento = ? WHERE Cedula = ?");
            
            
            Actualizar.setString(1, ce);
            Actualizar.setString(2, no);
            Actualizar.setString(3, ap);
            Actualizar.setString(4, co);
            Actualizar.setString(5, f);
            Actualizar.setInt(6, id);
            Actualizar.setString(7, idn);
            
            int Actualizado = Actualizar.executeUpdate();
            
            if(Actualizado > 0){
                
                
                JOptionPane.showMessageDialog(null, "Se ha actualizado el registro correctamente");
                
                
                
                Mostrar_actualizar2(ce, ced, nom, ape, cor, fec, dep);
                
                multitodo1.vista.Gerente.Actualizar2 act = new multitodo1.vista.Gerente.Actualizar2(null, true, ce);
                   
                
            }
            
            
            
            
            
            
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
                
            
            
            }
            
        }
    
        
        
        
        
    }
     
     
     public void Eliminar(JTable tab){
        
        int fila_s = tab.getSelectedRow();
        
        if(fila_s == -1){
         
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila antes de eliminarla");
            
        }else{
        
        
        String ced = tab.getValueAt(fila_s, 0).toString();
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            int mensaje = JOptionPane.showConfirmDialog(null, "Eliminar","¿Esta seguro de eliminar el registro?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(mensaje == JOptionPane.YES_OPTION){
                
            
            PreparedStatement eliminar = conexion.prepareStatement("DELETE FROM empleados WHERE Cedula = ?");
            eliminar.setString(1, ced);
            int eliminado = eliminar.executeUpdate();
            if(eliminado > 0){
                
                JOptionPane.showMessageDialog(null, "Se ha eliminado el registro con exito");
            }
            
                      
            
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error eliminar"+ e.getMessage());
        }
        
        }
        
    }
    
    
    
    
    public void Generar_Clave(String ced, JTextArea txta_cla){
        
        
        
        
        
        String minusculas = "abcdefghijklmnopqrstuwxyz";
        String mayusculas = minusculas.toUpperCase();
        String numeros = "1234567890";
        String caracteres = "@#_-";
        
        String cadena = minusculas + mayusculas + numeros + caracteres;
        
        String min = "^[a-z]+$";
        String may = "^[A-Z]+$";
        String num = "^[0-9]+$";
        String car = "^[@#_-]+$";
        
        String min_may = "^[a-zA-Z]+$";
        String min_num = "^[a-z0-9]+$";
        String min_car = "^[a-z@#_-]+$";
        String num_car = "^[0-9@#_-]+$";
        String may_num = "^[A-Z0-9]+$";
        String may_car = "^[A-Z@#_-]+$";
        
        String min_may_num = "^[a-zA-Z0-9]+$";
        String min_may_car = "^[a-zA-Z@#_-]+$";
        String min_num_car = "^[a-z0-9@#_-]+$";
        String may_num_car = "^[A-Z0-9@#_-]+$";
        
        
        String clave_tem = "";
        String clave_def = "";
        
        Random aleatorio = new Random();
        
        
        int bandera = 0;
        
        
        
        while(bandera == 0){
            
            for(int i = 0; i < 16; i++ ){
                
                int pos = aleatorio.nextInt(cadena.length());
                char dig = cadena.charAt(pos);
                clave_tem += dig;
            }
            
            if(clave_tem.matches(min) || clave_tem.matches(may) || clave_tem.matches(num) || clave_tem.matches(car) || clave_tem.matches(min_may) || clave_tem.matches(min_num) || clave_tem.matches(min_car) || clave_tem.matches(may_num) || clave_tem.matches(may_car) || clave_tem.matches(num_car) || clave_tem.matches(min_may_num) || clave_tem.matches(min_may_car) || clave_tem.matches(min_num_car) || clave_tem.matches(may_num_car)){
                
                bandera = 0;
                clave_tem = "";
            }else{
                bandera = 1;
                clave_def = clave_tem;
            }
            
        }
        
        
        
        
        
        
        if(ced.isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor ingrese su cedula");
        }else{
            
            boolean verificar_ce = verificar_campo_Empleados("Cedula", ced);
            
            if(verificar_ce == true){
                
              
                boolean actualizar_cl = Pre_actualizar("Clave", clave_def, ced);
                
                if(actualizar_cl == true){
                    
                    JOptionPane.showMessageDialog(null, "Clave actualizada con exito");
                    txta_cla.setText(clave_def);
                    
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "La cedula ingresada no existe");
            }
            
        }
     
        
    }
    
    public void Copiar(String cla){
        
        StringSelection sel = new StringSelection(cla);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
        
        JOptionPane.showMessageDialog(null, "La clave ha sido copiada");
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     public boolean verificar_Datos_Cuenta(String campo, String obj ){
        
        boolean res = false;
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            
            PreparedStatement verificar = conexion.prepareStatement("SELECT * FROM empleados WHERE " + campo + " = ? ");
            verificar.setString(1, obj);
            ResultSet verificado = verificar.executeQuery();
            
            if(verificado.next()){
                
                
                res = true;
                
            }
            
            
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "error verificar_one" + e.getMessage());
            
        }
        
        
        return res;
        
    }
     
     
     
       public void Mostrar_cla(JPasswordField jp_cla){
        
        if(jp_cla.getEchoChar() == '*'){
            
            jp_cla.setEchoChar((char)0);
            
        }else{
            jp_cla.setEchoChar('*');
        }
            
        
    }
    
    
   
    
  
    
    
    
 
    
    
    
    
    
    public void Buscar(String campo, String objeto, JTable tab){
        
        DefaultTableModel ta = (DefaultTableModel) tab.getModel();
        
        Object columna[] = new Object[10];
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
            
            PreparedStatement buscar = conexion.prepareStatement("SELECT cliente.Cedula, cliente.Nombres, cliente.Apellidos, cliente.Correo, TIMESTAMPDIFF(YEAR,cliente.Fecha_nacimiento,Current_date) FROM cliente WHERE " + campo + " LIKE ?");
            buscar.setString(1, "%"+objeto+"%");
            ResultSet buscado = buscar.executeQuery();
            
            
            boolean bandera = false; 
            
            ta.setRowCount(0);
            
            while(buscado.next()){
                
                columna[0] = buscado.getString(1);
                columna[1] = buscado.getString(2);
                columna[2] = buscado.getString(3);
                columna[3] = buscado.getString(4);
                columna[4] = buscado.getString(5);
               
                
                ta.addRow(columna);
                
                tab.setModel(ta);
                
                bandera = true;
                
                
            }
            
            
            
            
            if(bandera == false){
                
                JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "error buscar" + e.getMessage() );
        }
        
    }
    
    
    

    public void Mostrar_datos_cliente(JTextField ced, JTextField ape, JTable tab){
        
       String ce = ced.getText();
       String ap = ape.getText();
       
       if(ce.isEmpty() && ap.isEmpty()){
           
           JOptionPane.showMessageDialog(null, "Por favor ingrese por lo minimo un dato de busqueda");
           
           
           
       }else{
           
           if(!ce.isEmpty()){
               
               Buscar("Cedula", ce, tab);
               
           }else if(!ap.isEmpty()){
               
               Buscar("Apellidos", ap, tab);
               
           }
           
       }
        
        
        
    }
    
    
    
    
    
    public void Enviar_datos_Cliente_Crear(JTable tab){
        
        int fila_sele = tab.getSelectedRow();
        
        if(fila_sele == -1){
            
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila antes de continuar");
            
        }else{
            
            String cedula = tab.getValueAt(fila_sele, 0).toString();
            
            multitodo1.vista.Empleado.Nueva_cuenta nue_cue = new multitodo1.vista.Empleado.Nueva_cuenta(null, true,  cedula);
            nue_cue.setVisible(true);
        }
        
        
        
        
        
    }
    
    public void Enviar_datos_Cliente_Actualizar(JTable tab){
        
        int fila_sele = tab.getSelectedRow();
        
        if(fila_sele == -1){
            
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila antes de continuar");
            
        }else{
            
            String cedula = tab.getValueAt(fila_sele, 0).toString();
            
          multitodo1.vista.Empleado.Actualizar_Cliente act_cli = new multitodo1.vista.Empleado.Actualizar_Cliente(null, true, cedula);
          act_cli.setVisible(true);
        }
        
        
        
        
        
    }
    
    
    
    
    
    public void Insertar_Datos_Cliente(JTextField ced, JTextField nom, JTextField ape, JTextField cor, JDateChooser fec, String cedula){
        
        String ce = ced.getText();
        String no = nom.getText();
        String ap = ape.getText();
        String co = cor.getText();
        
        Calendar fe = fec.getCalendar();
        
        
       
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                
                PreparedStatement obtener = conexion.prepareStatement("SELECT cliente.Cedula, cliente.Nombres, cliente.Apellidos, cliente.Correo, cliente.Fecha_nacimiento FROM cliente WHERE Cedula = ?");
                obtener.setString(1, cedula);
                ResultSet obtenido = obtener.executeQuery();
                
                while(obtenido.next()){
                    
                    String pre_fecha = obtenido.getString(5);
                    SimpleDateFormat formato_calendario = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha_insertada = formato_calendario.parse(pre_fecha);
                    
                    Calendar calendario = new GregorianCalendar();
                    calendario.setTime(fecha_insertada);
                    
                    
                    ced.setText(obtenido.getString(1));
                    nom.setText(obtenido.getString(2));
                    ape.setText(obtenido.getString(3));
                    cor.setText(obtenido.getString(4));
                    fec.setCalendar(calendario);
                    
                    
                }
                
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: insertar_datos_cliente" + e.getMessage());
                        
            }
            
            
        }
    
       
    
       
        
       
        
        
        public void desbloquear_campos_cuenta(JTextField cue ,JTextField saldo, JTextField cla){
            Cuenta cu = new Cuenta();
            
            
            boolean gen_cue = cu.generar_Cuenta(cue);
            
            if(gen_cue == true){
                
                String cl = cu.Generar_Clave_cuenta();
                
                cla.setText(cl);
                
                saldo.setEnabled(true);
            }
            
            
        }
        
        
        
        
       
        
        
        
        
       
        
        
        
        
        public void Buscar_Datos_Cuenta(JTable tab, String campo, String objeto){
            DefaultTableModel ta = (DefaultTableModel) tab.getModel();
            Object columna[] = new Object[10];
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement buscar = conexion.prepareStatement("SELECT cliente.Cedula,  cliente.Nombres, cliente.Apellidos, cuenta.Numero_Cuenta, cuenta.Estado, cuenta.Saldo FROM cuenta INNER JOIN cliente ON cuenta.Cedula = cliente.Cedula WHERE " + campo + " LIKE ?");
                buscar.setString(1, "%" +   objeto  + "%");
                ResultSet buscado = buscar.executeQuery();
                
                
                ta.setRowCount(0);
                
                
                boolean bandera = false;
                
                while(buscado.next()){
                    
                    
                    columna[0] = buscado.getString(1);
                    columna[1] = buscado.getString(2);
                    columna[2] = buscado.getString(3);
                    columna[3] = buscado.getString(4);
                    columna[4] = buscado.getString(5);
                    columna[5] = buscado.getString(6);
                    
                    ta.addRow(columna);
                    
                    
                    tab.setModel(ta);
                    
                    bandera = true;
                    
                }
                
                
                if(bandera == false){
                    JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
                           
                }
                
                
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null, "Error: buscar_datos_cuenta" + e.getMessage());
                
            }
            
            
            
            
        }
        
        
        public void Mostrar_Datos_Cuenta(JTextField ced, JTextField ape, JTable tab){
            
            String ce = ced.getText();
            String ap = ape.getText();
            
            
            
            
            
            if(ce.isEmpty() && ap.isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Por favor llene por lo menos un campos de busqueda");
                
            }else {
                
                if(!ce.isEmpty()){
                    
                    Buscar_Datos_Cuenta(tab, "cliente.Cedula", ce);
                    
                }else if(!ap.isEmpty()){
                    
                    
                     Buscar_Datos_Cuenta(tab, "cliente.Apellidos", ap);
                    
                }
                
            }
            
            
            
        }
        
        
        
        
        
        public void Enviar_Datos_Cuenta_Deposito(JTable tab){
            
          
            int fila_sele = tab.getSelectedRow();
            
            if(fila_sele == -1){
                
                JOptionPane.showMessageDialog(null, "Por favor seleccione una cuenta");
            }else{
                
                String cue = tab.getValueAt(fila_sele, 3).toString();
                
                multitodo1.vista.Empleado.Deposito_Cliente dep_cli = new multitodo1.vista.Empleado.Deposito_Cliente(null, true, cue);
                dep_cli.setVisible(true);
              
                
            }
            
         
            
            
        }
        
         public void Enviar_Datos_Cuenta_Retiro(JTable tab){
            
          
            int fila_sele = tab.getSelectedRow();
            
            if(fila_sele == -1){
                
                JOptionPane.showMessageDialog(null, "Por favor seleccione una cuenta");
            }else{
                
                String cue = tab.getValueAt(fila_sele, 3).toString();
                
                multitodo1.vista.Empleado.Retiro_Cliente ret_cli = new multitodo1.vista.Empleado.Retiro_Cliente(null, true, cue);
                ret_cli.setVisible(true);
                
                
            }
            
         
            
            
            
            
        }
         
         
         public void Enviar_Datos_Cuenta_Transaccion(JTable tab){
            
          
            int fila_sele = tab.getSelectedRow();
            
            if(fila_sele == -1){
                
                JOptionPane.showMessageDialog(null, "Por favor seleccione una cuenta");
            }else{
                
                String cue = tab.getValueAt(fila_sele, 3).toString();
                
                multitodo1.vista.Empleado.Transferir_Cliente tran_cli = new multitodo1.vista.Empleado.Transferir_Cliente(null, true, cue);
                tran_cli.setVisible(true);
                
                
            }
            
         
            
            
        }
        
        
        
        public void Mostrar_Datos_Deposito(JTextField cue, JTextField sal, String num){
            
            
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement Mostrar = conexion.prepareStatement("SELECT Numero_cuenta , Saldo FROM cuenta WHERE Numero_cuenta = ?");
                Mostrar.setString(1,num);
                ResultSet Mostrado = Mostrar.executeQuery();
                
                if(Mostrado.next()){
                    
                    cue.setText(Mostrado.getString(1));
                    sal.setText(Mostrado.getString(2));
                    
                    
                }
                
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: Mostrar_Datos_Deposito");
                
            }
            
            
            
        }
        
        
        public void Depositar(JTextField cue, JTextField dep , JTextField sal, String num){
            
           
            
            String cu = cue.getText();
            String c_de = dep.getText().trim();
            
            
            if(c_de.isEmpty()){
                JOptionPane.showMessageDialog(null, "por favor ingrese una cantidad");
            }else{
                double de = Double.parseDouble(c_de); 
                 
               boolean depositar = Consulta_deposito(de, cu);
               
               if(depositar == true){
                   
                  JOptionPane.showMessageDialog(null, "Deposito realizado con exito");
                    dep.setText(null);
                    Mostrar_Datos_Deposito(cue, sal, num);
               }
                
                
        
            }
            
           
            
            
            
        }
        
        
        public boolean Consulta_deposito(double sal, String cu){
            boolean ok = false; 
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement depositar = conexion.prepareStatement("UPDATE cuenta SET Saldo = Saldo + ? WHERE Numero_cuenta = ? ");
                depositar.setDouble(1, sal);
                depositar.setString(2, cu);
                int depositado = depositar.executeUpdate();
                
                if(depositado > 0 ){
                    
                    ok = true;
                    
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: deposito" + e.getMessage());
            }
            
            
            return ok;
        }
        
        public boolean Consulta_Retiro(double sal, String cue){
            
            boolean ok = false;
            
             try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement depositar = conexion.prepareStatement("UPDATE cuenta SET Saldo = Saldo - ? WHERE Numero_cuenta = ? ");
                depositar.setDouble(1, sal);
                depositar.setString(2, cue);
                int depositado = depositar.executeUpdate();
                
                if(depositado > 0 ){
                    
                    ok = true;
                    
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: retiro" + e.getMessage());
                
                
            }
            
            
            return ok;
            
        }
        
        
        public void Retirar (JTextField cue, JTextField dep , JTextField sal, String num){
            
           
            
            String cu = cue.getText();
            String c_de = dep.getText().trim();
            
            
            if(c_de.isEmpty()){
                JOptionPane.showMessageDialog(null, "por favor ingrese una cantidad");
            }else{
                double de = Double.parseDouble(c_de); 
                
                boolean retirar = Consulta_Retiro(de, cu);
                
                if(retirar == true){
                    
                      
                  JOptionPane.showMessageDialog(null, "Retiro realizado con exito");
                    dep.setText(null);
                    Mostrar_Datos_Deposito(cue, sal, num);
                    
                }
                
            }
            
           
            
        }
        
        
        
        public void Buscar_beneficiario(JTextField ben, JTextField nom_ben, JTextField can, JButton btn_tra){
              
               Cuenta cu = new Cuenta();
            
            
              String be = ben.getText().trim();
              
              boolean ver_cue = cu.verificar_cuenta_banco(be);
                
              
            
              
                if(ver_cue == true){     
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                       
                       
                  
                        PreparedStatement obtener = conexion.prepareStatement("SELECT cliente.Nombres FROM cliente INNER JOIN cuenta ON cliente.Cedula = cuenta.Cedula WHERE cuenta.Numero_cuenta = ?");
                        obtener.setString(1, be);
                        ResultSet obtenido = obtener.executeQuery();
                        if(obtenido.next()){
                            nom_ben.setText(obtenido.getString(1));
                            btn_tra.setEnabled(true);
                            can.setEnabled(true);
                            
                            
                            
                            
                            
                            
                            
                            
                        }
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error: buscar_ben" + e.getMessage());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No cuenta");
                }
        }
        
        
        public boolean Crear_Transferencia(String dep, String ben , double can){
            
            boolean ok = false;
            
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement crear = conexion.prepareStatement("INSERT INTO transaccion(`fecha_tra`, `Hora_tra`, `Cantidad`, `Cuenta_rem`, `Cuenta_ben`) VALUES(CURRENT_DATE,CURRENT_TIME,?,?,?)");
                crear.setDouble(1, can);
                crear.setString(2, dep);
                crear.setString(3, ben);
                int creado = crear.executeUpdate();
                
                if(creado > 0){
                    
                    
                    
                    ok = true;
                    
                }
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: crear transferencia" + e.getMessage());
            }
            
            return ok;
            
        }
        
        
        
        
        public void Transferir(JTextField cue, JTextField sal, JTextField can, JTextField ben ,  String num){
            
            String ca = can.getText().trim();
            String be = ben.getText().trim();
            
            if(ca.isEmpty() || be.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
            }else{
                
                
               
                    
                double c = Double.parseDouble(ca);
                double s = Double.parseDouble(sal.getText());
                
                if(s > c){
                     boolean deposito = Consulta_deposito(c, be);
                
                if(deposito == true){
                    
                    String dep = cue.getText().trim();
                    boolean retiro = Consulta_Retiro(c, dep);
                    
                    if(retiro == true){
                        
                        boolean transferencia = Crear_Transferencia(dep, be, c);
                        
                        if(transferencia == true){
                            JOptionPane.showMessageDialog(null, "Transeferencia realizada con exito");
                            Mostrar_Datos_Deposito(cue, sal, num);
                         
                            
                        }
                        
                    }
                    
                }
                    
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar la operación");
                }
                
               
                    
                }
                
                
                
            
            }
        
        
        
            public void Consultar_Transaccion(String campo ,String objeto, JTable tab){
                
                
                DefaultTableModel ta = (DefaultTableModel) tab.getModel();
                
                Object columna[] = new Object[10];
                
                try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                    
                    PreparedStatement mostrar = conexion.prepareStatement("SELECT transaccion.Cuenta_rem , remitente.Nombres , transaccion.Cuenta_ben , beneficiario.Nombres , transaccion.fecha_tra, transaccion.Hora_tra, transaccion.Cantidad FROM transaccion INNER JOIN cuenta AS c_remitente ON transaccion.Cuenta_rem = c_remitente.Numero_cuenta INNER JOIN cliente AS remitente ON c_remitente.Cedula = remitente.Cedula INNER JOIN cuenta AS c_beneficiario ON transaccion.Cuenta_ben =  c_beneficiario.Numero_cuenta INNER JOIN cliente AS beneficiario ON c_beneficiario.Cedula = beneficiario.Cedula WHERE " + campo + " Like ?");
                    mostrar.setString(1,"%" + objeto + "%");
                    ResultSet mostrado = mostrar.executeQuery();
                    
                    
                    boolean bandera = false;
                    
                    ta.setRowCount(0);
                    
                    while(mostrado.next()){
                        
                        
                        
                        columna[0] = mostrado.getString(1);
                        columna[1] = mostrado.getString(2);
                        columna[2] = mostrado.getString(3);
                        columna[3] = mostrado.getString(4);
                        columna[4] = mostrado.getString(5);
                        columna[5] = mostrado.getString(6);
                        columna[6] = mostrado.getString(7);
                        
                        ta.addRow(columna);
                        
                        bandera = true;
                        
                        tab.setModel(ta);
                    }
                    
                    
                    
                    if(bandera == false){
                        JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
                    }
                            
                    
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error: consultar transferencia" + e.getMessage());
                }
                
            }
        
        
        
                public void Buscar_Transacción(JTextField cue, JTextField nom, JTable tab){
                    
                    String cu = cue.getText();
                    String no = nom.getText();
                    
                    if(cu.isEmpty() && no.isEmpty()){
                        
                        JOptionPane.showMessageDialog(null, "Por favor ingrese un dato de busqueda");
                    }else{
                        
                        if(!cu.isEmpty()){
                            
                            Consultar_Transaccion("transaccion.Cuenta_rem", cu, tab);
                            
                        }else if(!no.isEmpty()){
                            
                             Consultar_Transaccion("remitente.Nombres", no, tab);
                            
                            
                            
                        }
                        
                        
                    }
                    
                    
                    
                    
                }
                
                public void Buscar_Datos_Cuenta_Bloqueada(JTable tab, String campo, String objeto){
            DefaultTableModel ta = (DefaultTableModel) tab.getModel();
            Object columna[] = new Object[10];
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement buscar = conexion.prepareStatement("SELECT cliente.Cedula,  cliente.Nombres, cliente.Apellidos, cuenta.Numero_Cuenta, cuenta.Estado, cuenta.Saldo FROM cuenta INNER JOIN cliente ON cuenta.Cedula = cliente.Cedula WHERE " + campo + " LIKE ?   AND cuenta.estado = ?");
                buscar.setString(1, "%" +   objeto  + "%");
                buscar.setString(2,"desactivo");
                ResultSet buscado = buscar.executeQuery();
                
                
                ta.setRowCount(0);
                
                
                boolean bandera = false;
                
                while(buscado.next()){
                    
                    
                    columna[0] = buscado.getString(1);
                    columna[1] = buscado.getString(2);
                    columna[2] = buscado.getString(3);
                    columna[3] = buscado.getString(4);
                    columna[4] = buscado.getString(5);
                    columna[5] = buscado.getString(6);
                    
                    ta.addRow(columna);
                    
                    
                    tab.setModel(ta);
                    
                    bandera = true;
                    
                }
                
                
                if(bandera == false){
                    JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
                           
                }
                
                
            }catch(Exception e){
                
                JOptionPane.showMessageDialog(null, "Error: buscar_datos_cuenta" + e.getMessage());
                
            }
            
            
            
            
        }
        
                
                
                
                
                public void Mostrar_Datos_Cuenta_Bloqueada(JTextField ced, JTextField ape, JTable tab){
            
            String ce = ced.getText();
            String ap = ape.getText();
            
            
            
            
            
            if(ce.isEmpty() && ap.isEmpty()){
                
                JOptionPane.showMessageDialog(null, "Por favor llene por lo menos un campos de busqueda");
                
            }else {
                
                if(!ce.isEmpty()){
                    
                    Buscar_Datos_Cuenta_Bloqueada(tab, "cliente.Cedula", ce);
                    
                }else if(!ap.isEmpty()){
                    
                    
                     Buscar_Datos_Cuenta_Bloqueada(tab, "cliente.Apellidos", ap);
                    
                }
                
            }
            
            
            
        }
                
                
                
                public void Enviar_datos_desbloqueo(JTable tab){
                    
                    int sel_fila = tab.getSelectedRow();
                    
                    if(sel_fila == -1){
                        
                        JOptionPane.showMessageDialog(null, "Por favor seleccione la cuenta que desea desbloquear");
                    }else{
                        
                        
                        String cuenta = tab.getValueAt(sel_fila, 3).toString();
                        
                        multitodo1.vista.Empleado.Desbloquear_cuenta des_cue = new multitodo1.vista.Empleado.Desbloquear_cuenta(null, true, cuenta);
                        des_cue.setVisible(true);
                        
                    }
                       
                    
                    
                    
                }
                
                
                public void Mostrar_datos_Desbloqueo(String cue, JTextField ced, JTextField nom, JTextField ape, JTextField cor, JDateChooser fec, JTextField num_cue, JTextField est){
                    
                    
                   try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                       
                       PreparedStatement mostrar = conexion.prepareStatement("SELECT cliente.Cedula, cliente.Nombres, cliente.Apellidos, cliente.Correo, cliente.Fecha_nacimiento , cuenta.Numero_cuenta , cuenta.Estado FROM cliente INNER JOIN cuenta ON cliente.Cedula = cuenta.Cedula WHERE cuenta.Numero_cuenta = ?");
                       mostrar.setString(1, cue);
                       ResultSet mostrado = mostrar.executeQuery();
                       
                       while(mostrado.next()){
                           
                           
                           
                           Date fecha = mostrado.getDate(5);
                           
                           java.util.Date fecha_ok = new java.util.Date(fecha.getTime());
                           
                           
                           ced.setText(mostrado.getString(1));
                           nom.setText(mostrado.getString(2));
                           ape.setText(mostrado.getString(3));
                           cor.setText(mostrado.getString(4));
                           fec.setDate(fecha_ok);
                           num_cue.setText(mostrado.getString(6));
                           est.setText(mostrado.getString(7));
                           
                           
                           
                       }
                       
                   }catch(Exception e){
                       JOptionPane.showMessageDialog(null, "Error: mostrar_datos_cuenta " + e.getMessage());
                   }
                           
                    
                    
                }
                
                
                public void Desbloquear_Cuenta(JTextField cue, JTextField est, JButton btn_des){
                    
                    String cu = cue.getText();
                    
                    try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                        
                        String estado = "Activo";
                        
                        PreparedStatement actualizar = conexion.prepareStatement("UPDATE cuenta SET estado = ? WHERE Numero_cuenta = ?");
                        actualizar.setString(1, estado);
                        actualizar.setString(2, cu);
                        int actualizado = actualizar.executeUpdate();
                        
                        if(actualizado > 0){
                            
                            JOptionPane.showMessageDialog(null, "Cuenta desbloqueada con exito");
                            
                            est.setText(estado);
                            
                            btn_des.setEnabled(false);
                            
                        }
                            
                        
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error: desbloqueo" + e.getMessage());
                    }
                    
                    
                }
                
                
                public void Enviar_Datos_Restablecer_Clave(JTable tab){
                    
                    int fila_sele = tab.getSelectedRow();
                    
                    if(fila_sele == -1){
                        
                       
                        
                        JOptionPane.showMessageDialog(null, "Por favor elija un registro antes de continuar");
                    }else{
                        String cuenta = tab.getValueAt(fila_sele, 3).toString();
                        multitodo1.vista.Empleado.Restablecer_clave res_cla = new multitodo1.vista.Empleado.Restablecer_clave(null, true, cuenta );
                        res_cla.setVisible(true);
                    }
                          
                    
                    
                }
                
                
                public void Restablecer_Clave(JTextField cue, JTextField cla){
                    
                    Cuenta cuenta = new Cuenta();
                    
                    String cu = cue.getText();
                    
                   
                    
                    
                    try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                        
                        String nueva_clave = cuenta.Generar_Clave_cuenta();
                        
                        PreparedStatement restablecer = conexion.prepareStatement("UPDATE cuenta SET Clave = ? WHERE Numero_cuenta = ?");
                        restablecer.setString(1, nueva_clave);
                        restablecer.setString(2, cu);
                        int restablecido = restablecer.executeUpdate();
                        
                        if(restablecido > 0){
                            
                            
                            JOptionPane.showMessageDialog(null, "Clave restablecida con exito");
                                   
                            
                            cla.setText(nueva_clave);
                            
                            
                            
                        }
                        
                        
                        
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Error: restablecer clave");
                    }
                    
                    
                    
                    
                }
                
                
                
                
                
                
            
            
        }
    
    
    
        





