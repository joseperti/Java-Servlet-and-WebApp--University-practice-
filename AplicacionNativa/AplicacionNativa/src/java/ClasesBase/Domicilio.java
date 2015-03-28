package ClasesBase;

/**Para favorecer la encapsulacion, implementamos la clase Domicilio, que contendrá
 * en forma de atributos, todo lo relacionado con la localización del Huesped.
 *
 * @author alumno
 * 
 * Esta clase se encargará de manipular los datos del domicilio del huésped
 */
public class Domicilio {
    private String direccion;
    private String localidad;
    private String provincia;
    private String cpostal;

    
    public Domicilio(String dir, String loc, String prov, String cp){
        //Atributos de la clase Domicilio
        this.cpostal=cp;
        this.direccion=dir;
        this.localidad=loc;
        this.provincia=prov;
    }
    //A continuación se implementan los métodos de acceso a los atributos
    /**
     * @return la dirección del domicilio
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion nueva dirección a introducir en el domicilio
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return método de petición de la Localidad del domicilio
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the cpostal
     */
    public String getCpostal() {
        return cpostal;
    }

    /**
     * @param cpostal the cpostal to set
     */
    public void setCpostal(String cpostal) {
        this.cpostal = cpostal;
    }
    
    /**
     * @return Retornamos un string con los datos del domicilio
     */
    public String toString(){
        return(this.direccion+" "+this.localidad+", "+this.provincia+" "+this.cpostal);
    }
    
}