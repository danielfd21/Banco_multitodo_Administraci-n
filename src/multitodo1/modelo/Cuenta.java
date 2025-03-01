/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multitodo1.modelo;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ferna
 */
public class Cuenta extends Cliente {
    JTextField num_cue;
    JTextField cla;
    JTextField sal;
    String nu_cu; 
    String cl; 
    double sa ;

    public Cuenta(){
        
    }
    
    
    public Cuenta(JTextField cedu, JTextField nomb, JTextField apel, JTextField core, JDateChooser fech, JTextField nume_cuen, JTextField clav, JTextField sald) {
        super(cedu, nomb, apel, core, fech);
        this.num_cue = nume_cuen;
        this.cla = clav;
        this.sal = sald;
        
        this.nu_cu = num_cue.getText();
        this.cl = cla.getText();
        this.sa = Double.parseDouble(sal.getText());
        
        
    }
    
    
     public boolean verificar_cuenta_banco(String num){
            
            boolean bandera = false;
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                
                 PreparedStatement verificar = conexion.prepareStatement("SELECT * FROM cuenta WHERE Numero_cuenta = ?");
                verificar.setString(1,num);
                ResultSet verificado = verificar.executeQuery();
                
                if(verificado.next()){
                    
                    bandera = true;
                    
                }else{
                    bandera = false;
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: verificar_cuenta" + e.getMessage());
            }
            
            return bandera;
            
        }
        
    
    
    
    
    
    
    public boolean generar_Cuenta(JTextField cue){
            
               
            boolean ok = false;
            String numeros = "1234567890";
            
            boolean bandera = false;
            
            String acumulador = "";
            
            
            Random aleatorio = new Random();
            
            while(bandera == false){
                
                for(int i = 0; i < 10; i++ ){
                    
                    int pos = aleatorio.nextInt(numeros.length());
                    char val = numeros.charAt(pos);
                    acumulador += val;
                    
                }
                
                boolean ver_cue_ban = verificar_cuenta_banco(acumulador);
                
                if(ver_cue_ban == false){
                    
                    bandera = true;
                    
                }else{
                    bandera = false;
                }
               
            }
            
               
            if(bandera == true){
                
                cue.setText(acumulador);
                ok = true;
            }
               
               
               
          
            
            
            
           
            
            
            return ok;
            
            
        }
    
     public String Generar_Clave_cuenta(){
            
            
            
           String numeros = "1234567890";
           
           
           String acumulador = "";
           
           Random aleatorio = new Random();
           
           for(int i = 0; i < 4; i++ ){
               
                int pos = aleatorio.nextInt(numeros.length());
                char val = numeros.charAt(pos);
                acumulador += val;
                       
               
               
           }
           
           
           return acumulador;
            
           
            
        } 
        
    
    
    
 public void Crear_cuenta(){
            
            String es = "Activo";
            
            try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                
                PreparedStatement insertar = conexion.prepareStatement("INSERT INTO cuenta VALUES(?,?,?,?,?)");
                insertar.setString(1, nu_cu);
                insertar.setString(2, es);
                insertar.setDouble(3, sa);
                insertar.setString(4, cl);
                insertar.setString(5, ce);
                
                
                int insertado = insertar.executeUpdate();
                
                if(insertado > 0){
                    
                    num_cue.setText(null);
                    sal.setText(null);
                    sal.setEnabled(false);
                    cla.setText(null);
                    
                    JOptionPane.showMessageDialog(null, "Cuenta creada con exito");
                    
                }
                    
                
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "ERROR: crear_cuenta" +  e.getMessage());
            }
            
        }
        
        
   public void Eliminar_Cuenta(JTable tab){
            
            DefaultTableModel ta = (DefaultTableModel) tab.getModel();
            
            int fila_sele = tab.getSelectedRow();
            
            
            if(fila_sele == -1){
                
                JOptionPane.showMessageDialog(null, "Por favor elija una cuenta antes de eliminarla");
            
            }else{
                String cue = tab.getValueAt(fila_sele, 3).toString();
            
            
            int Mensaje = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar esta cuenta?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if(Mensaje == JOptionPane.YES_OPTION){
                
                 try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                
                PreparedStatement Eliminar = conexion.prepareStatement("DELETE FROM cuenta WHERE Numero_cuenta = ?");
                Eliminar.setString(1, cue);
                int Eliminado = Eliminar.executeUpdate();
                
                
                
                if(Eliminado > 0){
                    
                    JOptionPane.showMessageDialog(null, "Cuenta eliminada con exito");
                    ta.removeRow(fila_sele);
                }
                    
                    tab.setModel(ta);
                
                 }catch(Exception e){
                
                   JOptionPane.showMessageDialog(null, "Error: Eliminar_cuenta" + e.getMessage());
                   
                 }
                
            }
            }
            
            
            
            
           
            
            
            
            
            
        }
}
