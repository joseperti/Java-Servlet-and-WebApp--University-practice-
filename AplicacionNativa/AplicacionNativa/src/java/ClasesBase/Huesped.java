package ClasesBase;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class Huesped {
    //Atributos de la clase Huésped
    private String nombre;
    private String apellidos;
    private String nif;
    private Date fechaNacimiento;
    private Domicilio domicilio;
    private String fijo;
    private String movil;
    private String email;
    
    /*
    * Constructor de la clase
    * Introducimos los parámetros: n = nombre del huésped, pa = apellidos, nif, fn = Fecha de nacimiento,
    * dom = un objeto de domicilio con los datos ya introducidos, fijo = número de teléfono fijo (puede ser null),
    * movil = número de móvil del huésped (puede ser null), email (puede ser null)
    * 
    */
    public Huesped(String n, String pa,String nif, Date fn, Domicilio dom,
            String fijo, String movil, String email){
        //Guardamos los parámetros en los atributos del huésped
        this.nombre=n;
        this.apellidos=pa;
        this.nif=nif;
        this.fechaNacimiento=fn;
        this.domicilio=dom;
        this.fijo=fijo;
        this.movil=movil;
        this.email=email;
        
    }
    
    //A continuación se implementan los métodos de acceso a los atributos
    /**
     * @return the nombre
     */
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

    /**
     * @param pApellido the pApellido to set
     */
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
    @Override
    public String toString(){

        try {
            return new String(("Nombre: "+this.getNombre()+"\nApellidos: "+this.getApellidos()+
                    "\nNIF: "+this.getNif()+"\nFecha de nacimiento: "+
                    this.getFechaNacimiento().toString()+"\nDomicilio: "+
                    this.domicilio.toString()+"\nTeléfono fijo: "+this.fijo+
                    "\nTeléfono movil: "+this.movil+"\nEmail: "+this.email+"\n").getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Huesped.class.getName()).log(Level.SEVERE, null, ex);
            return "Error al exportar huésped";
        }
        
    }
}

