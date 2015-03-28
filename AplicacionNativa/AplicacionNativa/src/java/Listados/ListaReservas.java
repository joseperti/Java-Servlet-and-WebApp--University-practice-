package Listados;

/*
 * Esta clase se encargará de recopilar y mantener accesibles las distintas
 * reservas que se introduzcan en el hotel
 */


import ClasesBase.Huesped;
import ClasesBase.Domicilio;
import ClasesBase.Reserva;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class ListaReservas {
    //Atributos de la clase
    private ArrayList<Reserva> listaReserva;
    
    public ListaReservas(){
        //Inicializamos la lista
        this.listaReserva=new ArrayList();
        
        try {
            Date fecha =  new Date(94,4,9);
            Date fechae = new Date(115,2,1);
            Date fechas = new Date(115,2,2);
            Domicilio dom= new Domicilio("C/Paul McCartney",new String("Móstoles".getBytes(), "ISO-8859-1"),"Madrid","29322");
            Huesped cliente;
            cliente = new Huesped(new String("José".getBytes(), "ISO-8859-1"),new String("Pérez Pérez".getBytes(), "ISO-8859-1"),
                        "12345678A",fecha,dom,"Sin Datos","Sin Datos","Sin Datos");
            Reserva res=new Reserva(cliente,fechae,fechas,101);
            this.listaReserva.add(res);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ListaReservas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Implementación de los métodos de acceso a los atributos de la clase
    /**
     * @return the listaReserva
     */
    public ArrayList<Reserva> getListaReserva() {
        return listaReserva;
    }

    /**
     * @param listaReserva the listaReserva to set
     */
    public void setListaReserva(ArrayList<Reserva> listaReserva) {
        this.listaReserva = listaReserva;
    }
    //Obtención de reservas realizadas por un huésped con nif conocido pasado como parámtetro
    public Reserva ConsultaHuespedNIF(String nif){
        Reserva res=null;
        for(Reserva reserva: this.listaReserva){
            if(reserva.getHuesped().getNif().equals(nif)){
                res=reserva;
                break;
            }
                
        }
        return res;
    }
    
    //Obtención de reservas realizadas por un huésped con nombre y apellidos conocidos pasados como parámtetro
    public Reserva ConsultaHuespedNombre(String nombre,String apellidos){
        Reserva res=null;
        for(Reserva reserva: this.listaReserva){
            if(reserva.getHuesped().getNombre().equals(nombre)&&reserva.getHuesped().getApellidos().equals(apellidos)){
                res=reserva;
                break;
            }
                
        }
        return res;
    }
    //Obtención de reservas realizadas con una fecha de entrada pasada como parámetro
    public ArrayList<Reserva> ConsultaHuespedFechaEntrada(Date fechaEntrada){
        ArrayList<Reserva> res= new ArrayList<Reserva>();
        for(Reserva reserva: this.listaReserva){
            if(reserva.getFentrada().equals(fechaEntrada)){
                res.add(reserva);
            }
        }
        return res;
    }
         public void addReserva(Reserva res){
        this.listaReserva.add(res);
        }
        public void removeReserva(Reserva res){
            this.listaReserva.remove(res);
        }
    //Obtención de reservas realizadas por un huésped con nif conocido pasado como parámtetro y con una 
    // fecha de entrada también como parámetro

    public Reserva ConsultaReservaFechaNif(Date fecha,String nif){
        Reserva res=null;
        for(Reserva reserva: this.listaReserva){
            if(reserva.getFentrada().equals(fecha)&&reserva.getHuesped().getNif().equals(nif)){
                res=reserva;
                break;
            }
                
        }
        return res;
    }
    //Comprobación de la existencia de una reserva en la lista con una fecha de entrada y nif dados
    public boolean ExisteReserva (Date fecha,String nif){
        for(Reserva reserva: this.listaReserva){
            if(reserva.getFentrada().equals(fecha)&&reserva.getHuesped().getNif().equals(nif)){
                return true;
            }
                
        }
        return false;
    }
}