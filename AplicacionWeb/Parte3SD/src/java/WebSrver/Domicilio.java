package WebSrver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Para favorecer la encapsulacion, implementamos la clase Domicilio, que contendrá
//en forma de atributos, todo lo relacionado con la localizaciÃ³n del Huesped.
/**
 *
 * @author Sergio
 */
//Para 

public class Domicilio {
    //Atributos de la clse Domicilio que representan lo especificado en el enunciado
    private String direccion;
    private String localidad;
    private String provincia;
    private String cpostal;

    
    public Domicilio(String dir, String loc, String prov, String cp){
        //Metodo constructor  de la Clase Domicilio
        this.cpostal=cp;
        this.direccion=dir;
        this.localidad=loc;
        this.provincia=prov;
    }
    //A continuación se implementan los métodos get y set de la clase Domicilio.
    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the localidad
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
    
    public String toString(){
        return(this.direccion+" "+this.localidad+", "+this.provincia+" CP: "+(this.cpostal));
    }
    
}