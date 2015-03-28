package WebSrver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Sergio
 */
//La clase ListaCliente contiene un arrayList de los Huespedes del hotel.
public class ListaClientes {
    private ArrayList<Huesped> listaClientes;
    
    //Metodo Constructor que añaade el Huesped especificado en el enunciado.
    public ListaClientes(){
        this.listaClientes= new ArrayList();
        Domicilio dom= new Domicilio("C/LosChunguitos","Mostoles","Madrid","29322");
        Date fecha =  new Date(94,4,9);
        Huesped cliente = new Huesped("José","Pérez Pérez","12345678A",fecha,dom,"916172401","653331978","JoseP@PichaBrava.com");
        this.listaClientes.add(cliente);
        
    }

    public ListaClientes(ArrayList<Huesped> lista){
        this.listaClientes=lista;
        
    }
    //A continuación se presentan los metodos get y set de la clase ListaClientes.
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
    //El método ConsultaHuespedNIF recibe un NIF y busca al cliente asociado a dicho NIF.
    //Si no lo encuentra devuelve null.
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
    //El siguiente método devuelve el Huesped asociado a un nombre y apellidos que recibe como parametros.
    //Si no encuentra ninguno, deuelve null.
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
    //El método addHuesped recibe un Huesped como argumento que añade a la lista de clientes.
    public void addHuesped(Huesped cli){
        this.listaClientes.add(cli);
    }
    //El método removeHuesped recibe un Huesped como argumento que elimina de la lista de clientes.
    public void removeHuesped(Huesped cli){
        this.listaClientes.remove(cli);
    }
    //El método ExisteNIF devuelve TRUE si encuentra un clienten en la lista con el mismo NIF que recibe
    //como argumento, delvuelve FALSE en caso contrario.
    public boolean ExisteNIF (String nif){
        for(Huesped cliente: this.listaClientes){
            if(cliente.getNif().equals(nif)){
                return true;
            }
                
        }
        return false;        
    }
}