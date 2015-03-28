package WebSrver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Sergio
 */
//Clase Reserva que representa la reserva realizada por un Huesped. Contiene el nif del Huesped,
//las fechas de entrada y salida y la habitación asignada.
public class Reserva {
    private String nif;
    private Date fentrada;
    private Date fsalida;
    private int habitacion;
    
    public Reserva(String nif, Date fentrada, Date fsalida){
        //Metodo Constructor de la Clase Reserva con asignación aleatoria de la habitación.
        this.nif=nif;
        this.fentrada=fentrada;
        this.fsalida=fsalida;
        this.habitacion=this.habitacionAleatoria();
        
    }
    public Reserva(String nif, Date fentrada, Date fsalida,int i){
         //Metodo Constructor de la Clase Reserva con asignaciÃ³n aleatoria de la habitaciÃ³n.
        this.nif=nif;
        this.fentrada=fentrada;
        this.fsalida=fsalida;
        this.habitacion=i;
        
    } 
    //A continuación se muestran los métodos get y set de la clase Reserva.

    /**
     * @return the huesped
     */

    public String getNif() {
        return this.nif;
    }

    /**
     * @param huesped the huesped to set
     */
    public void setHuesped(String nif) {
        this.nif = nif;
    }

    /**
     * @return the fentrada
     */
    public Date getFentrada() {
        return fentrada;
    }

    /**
     * @param fentrada the fentrada to set
     */
    public void setFentrada(Date fentrada) {
        this.fentrada = fentrada;
    }

    /**
     * @return the fsalida
     */
    public Date getFsalida() {
        return fsalida;
    }

    /**
     * @param fsalida the fsalida to set
     */
    public void setFsalida(Date fsalida) {
        this.fsalida = fsalida;
    }

    /**
     * @return the habitacion
     */
    public int getHabitacion() {
        return habitacion;
    }

    /**
     * @param habitacion the habitacion to set
     */
    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }
    //Metodo que devuelve un número aleatorio en el intervalo [100,599]
    public int habitacionAleatoria(){
         Random rnd = new Random();
         return (rnd.nextInt(499))+100;
        
    }
}