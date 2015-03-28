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
//La clase ListaReservas representa la lista de Reservas del Hotel. Posee un array list de Reservas.
public class ListaReservas {
    private ArrayList<Reserva> listaReserva;
    
    public ListaReservas(){
        //Metodo Constructor de la clase que aÃ±ade directamente la Reserva especificada en el enunciado.
        this.listaReserva=new ArrayList();
        Date fechae = new Date(115,2,1);
        Date fechas = new Date(115,2,2);
        Reserva res=new Reserva("12345678A",fechae,fechas,101);
        this.listaReserva.add(res);
    }
    public ListaReservas(ArrayList<Reserva> listaReserva){
        this.listaReserva=listaReserva;
    }

    /**
     * @return the listaReserva
     */
    //A continuaciÃ³n se presentan los mÃ©todos get y set de la Clase Lista Reservas.
    public ArrayList<Reserva> getListaReserva() {
        return listaReserva;
    }

    /**
     * @param listaReserva the listaReserva to set
     */
    public void setListaReserva(ArrayList<Reserva> listaReserva) {
        this.listaReserva = listaReserva;
    }
    //El metodo ConsultaHuespedFechaEntrada recibe una fecha de entrada y devuelve una lista de Reservas
    //Que poseen dicha fecha de entrada.
       public ArrayList<Reserva> ConsultaHuespedFechaEntrada(Date fechaEntrada){
        ArrayList<Reserva> res= new ArrayList<Reserva>();
        for(Reserva reserva: this.listaReserva){
            System.out.println(reserva.getFentrada().toString());
            System.out.println(fechaEntrada.toString());
            if(reserva.getFentrada().equals(fechaEntrada)){
                res.add(reserva);
            }
                
        }
        return res;
    }
       //El mÃ©todo addReserva aÃ±ade una Reserva a la lista de reservas
        public void addReserva(Reserva res){
        this.listaReserva.add(res);
        }
        //El mÃ©todo removeReserva elimina una Reserva de la lista de Reservas
        public void removeReserva(Reserva res){
            this.listaReserva.remove(res);
        }
      
    //El mÃ©todo ConsultareservaFechaNif devuelve una reserva asociada a un NIF y una fecha de entrada.
    //Si no hay ninguna devuelve null.
    public Reserva ConsultaReservaFechaNif(Date fecha,String nif){
        Reserva res=null;
        for(Reserva reserva: this.listaReserva){
            if(reserva.getFentrada().equals(fecha)&&reserva.getNif().equals(nif)){
                res=reserva;
                break;
            }
                
        }
        return res;
    }

}