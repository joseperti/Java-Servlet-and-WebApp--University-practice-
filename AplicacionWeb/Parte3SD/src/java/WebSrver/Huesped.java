package WebSrver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;

/**
 *
 * @author Sergio
 */
public class Huesped {
    //Atributos de la clase Huesped que representan lo especificado en el enunciado
    private String nombre;
    private String apellidos;
    private String nif;
    private Date fechaNacimiento;
    private Domicilio domicilio;
    private String fijo;
    private String movil;
    private String email;
    
    public Huesped(String n, String pa,String nif, Date fn, Domicilio dom, String fijo, String movil, String email){
        //Método Constructor de la Clase Huesped
        this.nombre=n;
        this.apellidos=pa;
        this.nif=nif;
        this.fechaNacimiento=fn;
        this.domicilio=dom;
        this.fijo=fijo;
        this.movil=movil;
        this.email=email;
        
    }

    /**
     * @return the nombre
     */
    //A continuación se muestran los métodos get y set de los atributos de la clase Huesped
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pApellido
     */
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellido) {
        this.apellidos = apellido;
    }
    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
 
    }
    public String toString (){
        return ("Nombre: "+this.getNombre()+"\nApellidos: "+this.getApellidos()+"\nNIF: "+this.getNif()+"\nFecha de nacimiento"+this.getFechaNacimiento().toString());
        
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the domicilio
     */
    public Domicilio getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * @return the fijo
     */
    public String getFijo() {
        return fijo;
    }

    /**
     * @param fijo the fijo to set
     */
    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    /**
     * @return the movil
     */
    public String getMovil() {
        return movil;
    }

    /**
     * @param movil the movil to set
     */
    public void setMovil(String movil) {
        this.movil = movil;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
