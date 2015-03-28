package ClasesBase;

/**
 * 
 * Se encargará de manipular los datos correspondientes a la reserva del cliente
 * en el hotel
 * 
 */

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class Reserva {
    //Atributos de la claae Reserva
    private Huesped huesped;
    private Date fentrada;
    private Date fsalida;
    private int habitacion;
    
    //Constructor con la habitación desconocida: se elegirá aleatoriamente
    public Reserva(Huesped c, Date fentrada, Date fsalida){
        this.huesped=c;
        this.fentrada=fentrada;
        this.fsalida=fsalida;
        this.habitacion=this.habitacionAleatoria();
        
    }
    //Constructor con la habitación conocida
    public Reserva(Huesped c, Date fentrada, Date fsalida,int i){
        this.huesped=c;
        this.fentrada=fentrada;
        this.fsalida=fsalida;
        this.habitacion=i;
        
    }
    /**
     * 
     * @return un entero que corresponde al número de habitación seleccionada 
     */
    public int habitacionAleatoria(){
        return (int)((Math.random()*499)+100);
    }
    //A continuación se implementan los métodos de acceso a los atributos
    /**
     * @return the huesped
     */

    public Huesped getHuesped() {
        return huesped;
    }

    /**
     * @param huesped the huesped to set
     */
    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
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
    
    
    
    public String toString(){
        try {
            return new String(("Huésped: "+this.huesped.toString()+"\nFecha Entrada: "+this.fentrada+
                    "\nFecha Salida: "+this.fsalida+"\nHabitación: "+this.habitacion+"\n").getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Reserva.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}