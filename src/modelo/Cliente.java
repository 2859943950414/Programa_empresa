/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane; //usado para conectar con my sql
import java.sql.PreparedStatement; //usado para conectar con my sql
import java.sql.SQLException;
import java.sql.ResultSet; // usado para el manejo de la tabla
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author lglui
 */
public class Cliente extends Persona {
    private String nit;
    private int id;
    conexion cn; // variable para usar la conecion de la base de datos (abrir y cerrar)
    //conexion tiene que ser el mismo que usamos en conexion.java
    
    
    public Cliente(){}
    public Cliente(int id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.nit = nit;
        this.id= id;
    }

    public Cliente(Integer valueOf, String text, String text0, String text1, String text2, String text3, String text4) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cliente(String text, String text0, String text1, String text2, String text3, String text4) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
     //el siguiente codigo es para el manejo de la tabla.
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn= new conexion(); // esta variable es tambien minuscula porque de esa forma la declare
            cn.abrir_conexion();
           String query;
            query="SELECT id_cliente as id, nit, nombres, apellidos, direccion, telefono,fecha_nacimiento from clientes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            // encabezado de la tabla
            
            String encabezado[]= {"id","nit","nombres","apellidos","direccion","telefono","nacimiento"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos [] = new String [7]; // se coloco 7 porque son las cantidad de datos que vamos a colocar en la tabla
            //recorer 
            while(consulta.next()){
            datos[0]= consulta.getString("id");
            datos[1]= consulta.getString("nit");
            datos[2]= consulta.getString("nombres");
            datos[3]= consulta.getString("apellidos");
            datos[4]= consulta.getString("direccion");
            datos[5]= consulta.getString("telefono");
            datos[6]= consulta.getString("fecha_nacimiento");
            
            tabla.addRow(datos);
            }
            cn.cerrar_conexion(); // siempre va despues del while
            
        }catch(SQLException ex){
            cn.cerrar_conexion();
            System.out.println("Error: "+ ex.getMessage());
         
        }
        return tabla;
    }
    
    
    @Override
    //aqui se realiza el cambio para agregar los datos a la base de datos*******
   public void agregar (){
   //aqui inicia el codigo para agregar los datos a la base de datos
     try{
         PreparedStatement parametro; //declarado en la variable de arriba
         //aqui instanciamos 
         cn = new conexion();
         cn.abrir_conexion();
         String query;
         //estos datos de nit, nombres los creamos en my sql
         query = "INSERT INTO clientes (nit,nombres,apellidos,direccion,telefono,fecha_nacimiento) VALUES(?,?,?,?,?,?);";
         
         // pasar los datos al query
         parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getNit()); //siempre se inicializa en 1***
         parametro.setString(2, getNombres());
         parametro.setString(3, getApellidos());
         parametro.setString(4, getDireccion());
         parametro.setString(5, getTelefono());
         parametro.setString(6, getFecha_nacimiento());
         
         int executar = parametro.executeUpdate(); //en este caso usamos parametro porque esa es la variable que estamos trabajando
         //cerrar 
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null, Integer.toString(executar) + "  Registro Ingresado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);
         
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error..."+ ex.getMessage());
     }
   
   }
   
    @Override
   
     public void modificar (){
      
   //aqui inicia el codigo para agregar los datos a la base de datos
    
     try{
         PreparedStatement parametro; //declarado en la variable de arriba
          //aqui instanciamos 
         cn = new conexion();
         cn.abrir_conexion();
         String query;
         //estos datos de nit, nombres los creamos en my sql
         query = "update clientes set nit=?,nombres=?,apellidos=?,direccion=?,telefono=?,fecha_nacimiento=? where id_cliente=?";
         
         // pasar los datos al query
         parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getNit()); //siempre se inicializa en 1***
         parametro.setString(2, getNombres());
         parametro.setString(3, getApellidos());
         parametro.setString(4, getDireccion());
         parametro.setString(5, getTelefono());
         parametro.setString(6, getFecha_nacimiento());
         parametro.setInt(7, getId());
         int executar = parametro.executeUpdate(); //en este caso usamos parametro porque esa es la variable que estamos trabajando
         //cerrar 
         cn.cerrar_conexion();
         JOptionPane.showMessageDialog(null, Integer.toString(executar) + "  Registro Modificado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);
         
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error..."+ ex.getMessage());
     }
   
   }
  
 
     @Override
    //aqui se realiza el cambio para agregar los datos a la base de datos*******
   
     public void eliminar (){
         try{
         PreparedStatement parametro; //declarado en la variable de arriba
          //aqui instanciamos 
         cn = new conexion();
         cn.abrir_conexion();
         String query;
         //estos datos de nit, nombres los creamos en my sql
         query = "delete from clientes where id_cliente= ?";
         
         // pasar los datos al query
         parametro = (PreparedStatement)cn.conexionBD.prepareStatement(query);
        
         parametro.setInt(1,this.getId());
         int executar = parametro.executeUpdate(); //en este caso usamos parametro porque esa es la variable que estamos trabajando
         //cerrar 
         cn.cerrar_conexion();
         JOptionPane.showMessageDialog(null, Integer.toString(executar) + "  Registro Eliminado", "Mensaje",JOptionPane.INFORMATION_MESSAGE);
         
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error..."+ ex.getMessage());
     } 
}
}