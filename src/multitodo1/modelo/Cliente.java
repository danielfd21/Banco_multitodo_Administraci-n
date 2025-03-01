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
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ferna
 */
public class Cliente {
    
    
    JTextField ced;
    JTextField nom;
    JTextField ape;
    JTextField cor;
    JDateChooser fec;
    
    
    String ce, no, ap, co;
    Calendar fe;
    
    
    public Cliente(){
        
    }
    
    
    public Cliente(JTextField cedu,JTextField nomb, JTextField apel , JTextField core, JDateChooser fech ){
        
        
        
        this.ced = cedu;
        this.nom = nomb;
        this.ape = apel;
        this.cor = core;
        this.fec = fech;
        
        ce = ced.getText();
        no = nom.getText();
        ap = ape.getText();
        co = cor.getText();
        fe = fec.getCalendar();
    }
    
    
    Empleado emp = new Empleado();
    
    
    public boolean Verificar_uno_Cliente(String campo, String objeto){
        
        boolean ok = false;
        
        try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
            
            PreparedStatement Consultar = conexion.prepareStatement("SELECT * FROM cliente WHERE " + campo + " =  ?");
            Consultar.setString(1, objeto);
            ResultSet Consultado = Consultar.executeQuery();
            
            if(Consultado.next()){
                
                ok = true;
                
            }
            
            
            
            
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, "error verificar_uno" + e.getMessage());
        }
        
            
        return ok;
        
    }
    
    
    public void Crear_Cliente(){
                
        if(ce.isEmpty() || no.isEmpty() || ap.isEmpty() || co.isEmpty() || fec == null){
            
            JOptionPane.showMessageDialog(null, "Por favor llene todos los datos");
        }else{
            
            boolean ver_ced = Verificar_uno_Cliente("Cedula", ce);
            
            if(ver_ced == false){
                
                boolean ver_cor = Verificar_uno_Cliente("Correo", co);
                if(ver_cor == false){
                    
                    
                    int año = fe.get(Calendar.YEAR);
                    int mes = fe.get(Calendar.MONTH) + 1;
                    int dia = fe.get(Calendar.DAY_OF_MONTH);
                    
                    String fe_na = año + "-" + mes + "-" + dia;
                    
                    try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                        
                       PreparedStatement Registrar = conexion.prepareStatement("INSERT INTO cliente VALUES(?,?,?,?,?)");
                       Registrar.setString(1, ce);
                       Registrar.setString(2, no);
                       Registrar.setString(3, ap);
                       Registrar.setString(4, co);
                       Registrar.setString(5, fe_na);
                       
                       int Registrado = Registrar.executeUpdate();
                       
                       if(Registrado > 0 ){
                           
                           JOptionPane.showMessageDialog(null, "Cliente registrado con exito");
                           
                           ced.setText(null);
                           nom.setText(null);
                           ape.setText(null);
                           cor.setText(null);
                           fec.setDate(new Date());
                       }
                       
          
                        
                    }catch(Exception e){
                        
                        JOptionPane.showMessageDialog(null, "error crear cliente" + e.getMessage());
                        
                    }
                    
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Error: el correo ya se encuentra en uso");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Error: El cliente ya se encuentra registrado");
                
            }
            
        }
        
        
    }
    
    
      
        public String Actualizar_Cliente(String id){
            
           
            
            if(ce.isEmpty() || no.isEmpty() || ap.isEmpty() || co.isEmpty() || fe == null){
                
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
                
            }else{
                
                int año = fe.get(Calendar.YEAR);
                int mes = fe.get(Calendar.MONTH)+ 1;
                int dia = fe.get(Calendar.DAY_OF_MONTH);
                
                String fecha_in = año + "-" + mes + "-" + dia;
                
                
               try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Banco_multitodo","root","")){
                
                PreparedStatement Actualizar = conexion.prepareStatement("UPDATE cliente SET Cedula = ?,  Nombres = ?,  Apellidos = ?,  Correo = ?, Fecha_nacimiento = ? WHERE Cedula = ?");
                Actualizar.setString(1, ce);
                Actualizar.setString(2, no);
                Actualizar.setString(3, ap);
                Actualizar.setString(4, co);
                
                Actualizar.setString(5, fecha_in);
               
                Actualizar.setString(6, id);
                
                int actualizado = Actualizar.executeUpdate();
                
                if(actualizado > 0){
                    
                    JOptionPane.showMessageDialog(null, "Datos actualizados con exito");
                    
                    
                    
                    emp.Insertar_Datos_Cliente(ced, nom, ape, cor, fec, ce);
                    
                    id = ce;
                    
                    
                    
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: actualizar_cliente" + e.getMessage());
                
                
            }
                
                
            }
            
            return id;
            
        }
        
        
        public void Eliminar_Cliente(JTable tab){
            
            int fila_seleccionada = tab.getSelectedRow();
            
            if(fila_seleccionada == -1){
                
                JOptionPane.showMessageDialog(null, "Antes de eliminar seleccione una fila");
            }else{
                
                String ced = tab.getValueAt(fila_seleccionada, 0).toString();
                
                
             
                    
                     try(Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco_multitodo","root","")){
                    
                            int pregunta  = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?","Eliminar Cliente",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                
                
                             if(pregunta == JOptionPane.YES_OPTION){
                         
                                PreparedStatement eliminar = conexion.prepareStatement("DELETE FROM cliente WHERE Cedula = ?");
                                eliminar.setString(1, ced);
                                int eliminado = eliminar.executeUpdate();
                    
                                DefaultTableModel ta = (DefaultTableModel) tab.getModel();
                    
                                if(eliminado > 0){
                        
                     
                                    JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                        
                        
                                    ta.removeRow(fila_seleccionada);
                        
                                    tab.setModel(ta);
                        
                                }
                        
                        
                    }
                    
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "ERROR: eliminar cliente" + e.getMessage());
                }
                    
                    
                    
                    
                    
                    
                    
                    
                
                
               
                
                
            }
            
        }
    
    
    
}





















