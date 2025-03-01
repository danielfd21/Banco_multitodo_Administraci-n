/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multitodo1.Recursos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author ferna
 */
public class Factura_deposito implements Printable{
    
    
    String cue;
    double sal;
    double can;
     double Saldo_restante;
    
    
    public Factura_deposito(String cuenta , double saldo , double cantidad ){
        this.cue = cuenta;
        this.sal = saldo;
        this.can = cantidad;
        this.Saldo_restante = sal + can;
    }
    
    
    
    
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            
        if(pageIndex > 0 ){
            return NO_SUCH_PAGE;
        }
        
        Graphics2D grafico = (Graphics2D) graphics;
        
        grafico.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
        
         String saldo_limitado = String.format("%.2f", Saldo_restante);
        
        graphics.drawString("---------FACTURA-----------",100,100);
        graphics.drawString("Cuenta: " + cue, 100, 120);
        graphics.drawString("Cantidad depositada: " + can, 100, 140);
        graphics.drawString("Saldo restante: " + saldo_limitado, 100, 160);
        graphics.drawString("---------------------------",100,180);
        
    
        
        return PAGE_EXISTS;
    
    }
    
}
