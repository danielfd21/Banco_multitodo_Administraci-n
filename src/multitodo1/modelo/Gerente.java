/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multitodo1.modelo;
import com.sun.jdi.connect.Connector;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Calendar;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JPasswordField;
/**
 *
 * @author ferna
 */


public class Gerente {
    
     public boolean verificar_Datos_Cuenta(String campo, String obj, int id ){
        
        boolean res = false;
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            
            PreparedStatement verificar = conexion.prepareStatement("SELECT * FROM empleados WHERE " + campo + " = ?  AND ID_departamento = ? ");
            verificar.setString(1, obj);
            verificar.setInt(2, id);
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
    
    
    
    
   
    public void SetDepartamento(JComboBox cb){
        
        DefaultComboBoxModel com = (DefaultComboBoxModel) cb.getModel();
        
        
        com.addElement("SELECCIONAR");
        
      try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
          
          
          PreparedStatement insertar = conexion.prepareStatement("SELECT Nombre FROM departamento");
          ResultSet insertado = insertar.executeQuery();
          while(insertado.next()){
              
              
            com.addElement(insertado.getString(1));
            cb.setModel(com);
              
          }
          
          
        
          
          
      }catch(Exception e){
          
          JOptionPane.showMessageDialog(null, "error setDepartamento" + e.getMessage());
          
      }
        
        
        
    }
    
    
    public int get_Id_Departamento(String nom){
        
        int id = 0;
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
           PreparedStatement obtener = conexion.prepareStatement("SELECT Id_departamento FROM departamento WHERE Nombre = ?");
            obtener.setString(1, nom);
            ResultSet obtenido = obtener.executeQuery();
            if(obtenido.next()){
                
              id = obtenido.getInt(1);
                
            }
        }catch(Exception e){
            
        }
        return id;
    }
    
    
   
    
     
    
    
    
    
   
    
    
    
    
   public void Mostrar(JTable tab){
       
       
       
       DefaultTableModel ta = (DefaultTableModel) tab.getModel();
       
       Object fila[] = new Object[10];
       
      
       try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
           
           PreparedStatement mostrar = conexion.prepareStatement("SELECT empleados.Cedula , empleados.Nombre, empleados.Apellidos, empleados.Correo, TIMESTAMPDIFF( YEAR, empleados.Fecha_de_nacimiento, CURRENT_DATE) , departamento.Nombre, jefe_departamento.Nombres FROM empleados INNER JOIN departamento ON empleados.Id_departamento = departamento.Id_departamento INNER JOIN jefe_departamento ON departamento.Cedula = jefe_departamento.Cedula");
           ResultSet mostrado = mostrar.executeQuery();
           
           
           int filas = tab.getRowCount();
           
           for(int i = 0; i < filas; i++){
               
               ta.removeRow(0);
               
           }
           
           int conf = 0;
           
           
           while(mostrado.next()){
               
               fila[0] = mostrado.getString(1);
               fila[1] = mostrado.getString(2);
               fila[2] = mostrado.getString(3);
               fila[3] = mostrado.getString(4);
               fila[4] = mostrado.getString(5);
               fila[5] = mostrado.getString(6);
               
               ta.addRow(fila);
               tab.setModel(ta);
               
               conf = 1;
           }
           
           if(conf == 0){
               JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
           }
           
           
           
       }catch(Exception e){
           
           JOptionPane.showMessageDialog(null, "Error Mostrar" + e.getMessage());
           
       }
       
       
       
       
   }
   
   
   public void Pre_Mostrar_ind(String campo, String obj, JTable tab){
       
       DefaultTableModel ta = (DefaultTableModel) tab.getModel();
       
       Object fila[] = new Object[10];
       
       try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
           
          PreparedStatement mostrar = conexion.prepareStatement("SELECT empleados.Cedula , empleados.Nombre, empleados.Apellidos, empleados.Correo, TIMESTAMPDIFF( YEAR, empleados.Fecha_de_nacimiento, CURRENT_DATE) , departamento.Nombre, jefe_departamento.Nombres FROM empleados INNER JOIN departamento ON empleados.Id_departamento = departamento.Id_departamento INNER JOIN jefe_departamento ON departamento.Cedula = jefe_departamento.Cedula WHERE " + campo +  " LIKE ?");
          mostrar.setString(1, "%" + obj + "%");
          ResultSet mostrado = mostrar.executeQuery();
          
          
          int filas = tab.getRowCount();
          
          for(int i = 0; i < filas; i++){
              
              ta.removeRow(0);
          }
          
          
          int bandera = 0;
          
          
          while(mostrado.next()){
              
          fila[0] = mostrado.getString(1);
          fila[1] = mostrado.getString(2);
          fila[2] = mostrado.getString(3);
          fila[3] = mostrado.getString(4);
          fila[4] = mostrado.getString(5);
          fila[5] = mostrado.getString(6);
          
          ta.addRow(fila);
              bandera = 1;
          }
          
          if(bandera == 0){
              
              JOptionPane.showMessageDialog(null, "No se han encontrado resultados");
          }
           
           
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error Motrar_ind" + e.getMessage());
       }
       
       
   }
   
   
   public void Mostrar_ind(JTable tab, String ced, String ape, String dep){
       
       
       
       
       if(ced.isEmpty() && ape.isEmpty() && dep.equals("SELECCIONAR")){
           
           JOptionPane.showMessageDialog(null, "Por favor ingrese un campo de busqueda");
           
       }else{
       
           
           if(!ced.isEmpty()){
               
               
               Pre_Mostrar_ind("empleados.Cedula", ced, tab);
           }
           
           
           if(!ape.isEmpty()){
               
               Pre_Mostrar_ind("empleados.Apellidos", ape, tab);
               
               
           }
           
           if(!dep.equals("SELECCIONAR")){
               
               int id = get_Id_Departamento(dep);
               String ids = String.valueOf(id);
               
               
               Pre_Mostrar_ind("empleados.Id_departamento", ids, tab);
               
           }
           
           
       }
       
       
       
       
   }
    
    
    
    
    public int Total_dep(){
        
        int cant = 0;
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            PreparedStatement obtener = conexion.prepareStatement("SELECT Count(DISTINCT Id_departamento) FROM empleados");
            ResultSet obtenido = obtener.executeQuery();
            
            if(obtenido.next()){
                
                cant = obtenido.getInt(1);
            }
            
        }catch(Exception e){
 
            JOptionPane.showMessageDialog(null, "Error Total_dep" + e.getMessage());
            
            
        }
        
        return cant;
    }
    
    
    public String getDepartamento(int id){
        
        String nom = "";
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            PreparedStatement obtener = conexion.prepareStatement("SELECT Nombre FROM departamento WHERE Id_departamento = ?");
            obtener.setInt(1, id);
            ResultSet obtenido = obtener.executeQuery();
            if(obtenido.next()){
                
                nom = obtenido.getString(1);
                
                
            }
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "Error getDepartamento" + e.getMessage());
        
        }
        return nom;
    }

    
    public int get_cant_x_departamento(int id){
        
        int cant = 0;
        
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
            
            PreparedStatement obtener = conexion.prepareStatement("SELECT Count(Cedula) FROM empleados WHERE Id_departamento = ?");
            obtener.setInt(1, id);
            ResultSet obtenido = obtener.executeQuery();
            if(obtenido.next()){
                
                
                cant = obtenido.getInt(1);
            }
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error get_x_departamento" + e.getMessage());
        }
        return cant;
            
    }
    
    

    public void Enviar_Actualizar2(JTable tab){
        
        int fila_seleccionada = tab.getSelectedRow();
        
        
        
        if(fila_seleccionada == -1){
            
            JOptionPane.showMessageDialog(null, "Por favor seleccione una fila antes de actualizar el registro");
            
        }else{
            
            String cedula = tab.getValueAt(fila_seleccionada, 0).toString();
            
            multitodo1.vista.Gerente.Actualizar2 act = new multitodo1.vista.Gerente.Actualizar2(null, true,cedula);
            act.setVisible(true);
        }
        
  
        
        
    }
    
    
    public void Mostrar_actualizar2(String cedula, JTextField ced, JTextField nom, JTextField ape, JTextField cor, JDateChooser fec, JComboBox dep){
        
        if(cedula.isEmpty()){
            JOptionPane.showMessageDialog(null, "La cedula es inexistente");
        }else{
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/inicio_multitodo","root","")){
                
                PreparedStatement obtener = conexion.prepareStatement("SELECT empleados.Cedula , empleados.Nombre , empleados.Apellidos , empleados.Correo , empleados.Fecha_de_nacimiento , departamento.Nombre FROM empleados INNER JOIN departamento ON empleados.Id_departamento = departamento.Id_departamento WHERE empleados.Cedula = ? ");
                obtener.setString(1, cedula);
                ResultSet obtenido = obtener.executeQuery();
                
                while(obtenido.next()){
                    
                    String fe = obtenido.getString(5);
                    
                    SimpleDateFormat cal = new SimpleDateFormat("yyyy-MM-dd");
                    Date f =  cal.parse(fe);
                    
                    Calendar ca = new GregorianCalendar();
                    ca.setTime(f);
                    
                    
                    
                    
                    ced.setText(obtenido.getString(1));
                    nom.setText(obtenido.getString(2));
                    ape.setText(obtenido.getString(3));
                    cor.setText(obtenido.getString(4));
                    fec.setCalendar(ca);
                    dep.setSelectedItem(obtenido.getString(6));
                }
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "error" + e.getMessage());
            }
            
            
            
            
            
        }
        
        
    }
    
    
   
    
}




