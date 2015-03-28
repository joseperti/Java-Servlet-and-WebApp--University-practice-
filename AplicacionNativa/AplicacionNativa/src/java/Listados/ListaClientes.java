package Listados;

/**
 *Se encargará de agrupar y poder acceder a los distintos clientes del Hotel
 * 
*/


import ClasesBase.Huesped;
import ClasesBase.Domicilio;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class ListaClientes {
    //Atributos de la clase
    private ArrayList<Huesped> listaClientes;
    
    //Constructor
    public ListaClientes(){
        //Inicializamos la lista de clientes
        this.listaClientes= new ArrayList();
        //Insertamos un cliente de prueba
        Domicilio dom;
        try {
            dom = new Domicilio("C/Paul McCartney",new String("Móstoles".getBytes(), "ISO-8859-1"),"Madrid","29322");
            Date fecha =  new Date(94,4,9);
            Huesped cliente;
            cliente = new Huesped(new String("José".getBytes(), "ISO-8859-1"),new String("Pérez Pérez".getBytes(), "ISO-8859-1"),
                    "12345678A",fecha,dom,"Sin Datos","Sin Datos","Sin Datos");
            this.listaClientes.add(cliente);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ListaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Métodos de acceso a los atributos de la clase
    /**
     * @return the listaClientes
     */
    public ArrayList<Huesped> getListaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientes the listaClientes to set
     */
    public void setListaClientes(ArrayList<Huesped> listaClientes) {
        this.listaClientes = listaClientes;
    }
    //Método para obtener los datos de un huésped mediante su nif
    public Huesped ConsultaHuespedNIF(String nif){
        Huesped cli=null;
        for(Huesped cliente: this.listaClientes){
            if(cliente.getNif().equals(nif)){
                cli=cliente;
                break;
            }
                
        }
        return cli;
    }  
    //Método para obtener los datos de un huésped mediante su nombre y apellidos
    public Huesped ConsultaHuespedNombre(String nombre,String apellidos){
        Huesped cli=null;
        for(Huesped cliente: this.listaClientes){
            if(cliente.getNombre().equals(nombre)&&cliente.getApellidos().equals(apellidos)){
                cli=cliente;
                break;
            }
                
        }
        return cli;
    }
    //Método para añadir un huésped a la lista
    public void addHuesped(Huesped cli){
        this.listaClientes.add(cli);
    }
    //Método para eliminar un huésped de la lista
    public void removeHuesped(Huesped cli){
        this.listaClientes.remove(cli);
    }
    
    //Método para conocer la existencia de un cliente en el hotel mediante nif
    //True si se encuentra, false si no
    public boolean ExisteNIF (String nif){
        for(Huesped cliente: this.listaClientes){
            if(cliente.getNif().equals(nif)){
                return true;
            }
                
        }
        return false;        
    }
    //Método para conocer la existencia de un cliente en el hotel mediante nif
    //True si se encuentra, false si no
    public boolean ExisteNombreApellidos (String nombre, String apellidos){
        for(Huesped cliente: this.listaClientes){
            if(cliente.getNombre().equals(nombre)&&cliente.getApellidos().equals(apellidos)){
                return true;
            }       
        }
        return false;        
    }
}