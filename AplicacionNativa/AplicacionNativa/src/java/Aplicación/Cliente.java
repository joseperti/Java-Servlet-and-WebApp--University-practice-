package Aplicación;


import Mensajes.MensajeErrorNameSurname;
import Mensajes.MensajeErrorDate;
import Mensajes.MensajeErrorDateNIF;
import Mensajes.MensajeRespuesta;
import Mensajes.MensajeErrorNIF;
import ClasesBase.Huesped;
import ClasesBase.Reserva;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;

/*
    Clase que representa la aplicación cliente
*/

/**
 *
 * @author Alumno
 */
public class Cliente {
    
    //Atributos de lectura y escritura por pantalla y conexión con el servidor
    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in,"ISO-8859-1"));
    PrintStream consolaesp = new PrintStream(System.out, true, "ISO-8859-1");
    String direccion_ip = "localhost";
    String proyecto = "AplicacionNativa";
    String nombre_servlet = "Servlet";
    String ruta = "http://"+direccion_ip+":8080/"+proyecto+"/"+nombre_servlet;
    
    //Constructor
    public Cliente() throws IOException, ParseException{
        
        menu_ppal();
        consolaesp.println("Adiós!");
        
    }
    
    //Ejecución de la operación para consultar un huésped
    public void consultarHuesped() throws IOException, ParseException{
        //Consulta-> GET
        consolaesp.println("Seleccione forma de consulta:");
        consolaesp.println("1.- NIF");
        consolaesp.println("2.- Nombre y apellidos");
        int i = Integer.parseInt(sc.readLine());
        URL url;
        HttpURLConnection conn;
        switch (i){
            case 1:
                //Se consultará mediante el NIF del huésped
                consolaesp.print("Inserte NIF: ");
                String nif = sc.readLine();
                //tipoConsulta es el identificador que usará el servidor para diferenciar las acciones
                String tipoConsulta = "ConsultaHuespedNIF";
                try {
                    //Url con los parámetros al ser un GET
                    url = new URL(ruta + "?tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
                            "&NIF="+ URLEncoder.encode(nif,"UTF-8"));
                    //consolaesp.println("Ejecutando en url: "+url.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    /* Realizamos la conexión
                    */
                    InputStream is = conn.getInputStream();
                    int codigo_http = conn.getResponseCode();
                    if (codigo_http/100 != 2) {
                        consolaesp.println("Error HTTP "+codigo_http);
                    }
                    else {
                        //consolaesp.println("Esperando respuesta");
                        BufferedReader lector =
                        new BufferedReader(new InputStreamReader(is));

                        //consolaesp.println("Respuesta: ");
                        String xml_recibido = "";
                        for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                            xml_recibido += t;
                        }
                        //System.out.println(xml_recibido);
                        //consolaesp.println("Codigo HTTP: "+codigo_http);
                        //}

                        XStream mistream = new XStream();
                        Object o = mistream.fromXML(xml_recibido);
                        if (o instanceof MensajeRespuesta){
                            //System.out.println("Mensaje correcto");
                            MensajeRespuesta mensaje = (MensajeRespuesta) o;
                            Huesped recibido = (Huesped) mistream.fromXML(mensaje.getTexto());
                            consolaesp.println(recibido.toString());
                            System.out.println("¿Qué desea hacer?\n0.-Menú principal\n1.-Modificar\n2.-Eliminar");
                            i = Integer.parseInt(sc.readLine());
                            switch (i){
                                case 0:
                                    //Salimos
                                    menu_ppal();
                                    break;
                                case 1:
                                    //Modificamos el cliente -> POST
                                    String nif_anterior = recibido.getNif();
                                    String nombre ="";
                                    while (nombre.equals("")){
                                        consolaesp.print("Inserte Nombre: ");
                                        nombre = sc.readLine();
                                    }
                                    String apellidos = "";
                                    while (apellidos.equals("")){
                                        consolaesp.print("Inserte Apellidos: ");
                                        apellidos = sc.readLine();
                                    }
                                    nif = "";
                                    while ( nif.equals("")){
                                        consolaesp.print("Inserte NIF: ");
                                        nif = sc.readLine();
                                    }
                                    String fechaNacimiento = "";
                                    while (fechaNacimiento.equals("")){
                                        consolaesp.print("Inserte Fecha de nacimiento (dd/mm/YYYY): ");
                                        fechaNacimiento = sc.readLine();
                                    }
                                    String direccion = "";
                                    while (direccion.equals("")){
                                        consolaesp.print("Inserte Direccion: ");
                                        direccion = sc.readLine();
                                    }
                                    String localidad = "";
                                    while (localidad.equals("")){
                                        consolaesp.print("Inserte Localidad: ");
                                        localidad = sc.readLine();
                                    }
                                    String codigoPostal = "";
                                    while (codigoPostal.equals("")){
                                        consolaesp.print("Inserte Código Postal: ");
                                        codigoPostal = sc.readLine();
                                    }
                                    String provincia = "";
                                    while (provincia.equals("")){
                                        consolaesp.print("Inserte Provincia: ");
                                        provincia = sc.readLine();
                                    }
                                    consolaesp.print("Inserte Móvil (opcional): ");
                                    String movil = sc.readLine();
                                    consolaesp.print("Inserte Fijo (opcional): ");
                                    String fijo = sc.readLine();
                                    consolaesp.print("Inserte Email (opcional): ");
                                    String email = sc.readLine();
                                    if (email.equals("")){
                                        email = "Sin datos";
                                    }
                                    if (movil.equals("")){
                                        movil = "Sin datos";
                                    }
                                    if (fijo.equals("")){
                                        fijo = "Sin datos";
                                    }
                                    tipoConsulta = "modificarHuesped";
                                    try {
                                        url = new URL(ruta );
                                        String parametros ="tipoConsulta="+tipoConsulta+"&NifAnterior="+nif_anterior+"&Nombre=" +
                                        URLEncoder.encode(nombre,"UTF-8" )+"&"+
                                        "Apellidos=" +
                                        URLEncoder.encode(apellidos,"UTF-8" )+"&"+
                                        "Nif=" +
                                        URLEncoder.encode(nif,"UTF-8" )+"&"+
                                        "FechaNacimiento=" +
                                        URLEncoder.encode(fechaNacimiento,"UTF-8" )+"&"+
                                        "Direccion=" +
                                        URLEncoder.encode(direccion,"UTF-8" )+"&"+
                                        "Localidad=" +
                                        URLEncoder.encode(localidad,"UTF-8" )+"&"+
                                        "CodigoPostal=" +
                                        URLEncoder.encode(codigoPostal,"UTF-8" )+"&"+
                                         "Provincia=" +
                                        URLEncoder.encode(provincia,"UTF-8" )+"&"+
                                        "Movil=" +
                                        URLEncoder.encode(movil,"UTF-8" )+"&"+
                                        "Fijo=" +
                                        URLEncoder.encode(fijo,"UTF-8" )+"&"+
                                        "Email=" +
                                        URLEncoder.encode(email,"UTF-8" );
                                        //consolaesp.println("Parámetros de envío: "+parametros);
                                        conn = (HttpURLConnection) url.openConnection();
                                        conn.setDoOutput(true); // Establece el verbo POST
                                        OutputStreamWriter writer = new
                                        OutputStreamWriter(conn.getOutputStream());
                                        writer.write(parametros);
                                        writer.flush(); // Envía el cuerpo del mensaje
                                        /* Realizamos la conexión */
                                        is = conn.getInputStream();
                                        codigo_http = conn.getResponseCode();
                                        if (codigo_http/100 != 2) {
                                            consolaesp.println("Error HTTP "+codigo_http);
                                        }
                                        else {
                                            //consolaesp.println("Esperando respuesta");
                                            lector =
                                            new BufferedReader(new InputStreamReader(is));
                                            //consolaesp.println("Respuesta: ");
                                            String texto = "";
                                            for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                                texto += t;
                                            }
                                            mistream = new XStream();
                                            o = mistream.fromXML(xml_recibido);
                                            if (o instanceof MensajeRespuesta){
                                                mensaje = (MensajeRespuesta) o;
                                                consolaesp.println(mistream.fromXML(mensaje.getTexto()));
                                            }else if (o instanceof MensajeErrorNIF){
                                                MensajeErrorNIF mensaje_error = (MensajeErrorNIF) o;
                                                System.out.println("Ya existe un huésped con el NIF: "+mensaje_error.getNIF());
                                            }
                                        }
                                    } 
                                    catch (Exception e){
                                        
                                    }
                                break;
                                case 2:
                                    //EliminarHuésped ->POST
                                    tipoConsulta = "EliminarHuesped";
                                    try {
                                        url = new URL(ruta);
                                        String parametros ="tipoConsulta="+tipoConsulta+"&nif="+recibido.getNif();
                                        //consolaesp.println("Parámetros de envío: "+parametros);
                                        conn = (HttpURLConnection) url.openConnection();
                                        conn.setDoOutput(true); // Establece el verbo POST
                                        OutputStreamWriter writer = new
                                        OutputStreamWriter(conn.getOutputStream());
                                        writer.write(parametros);
                                        writer.flush(); // Envía el cuerpo del mensaje
                                        /* Realizamos la conexión */
                                        is = conn.getInputStream();
                                        codigo_http = conn.getResponseCode();
                                        if (codigo_http/100 != 2) {
                                            consolaesp.println("Error HTTP "+codigo_http);
                                        }
                                        else{
                                            //consolaesp.println("Esperando respuesta");
                                            lector =
                                            new BufferedReader(new InputStreamReader(is));
                                            consolaesp.println("Respuesta: ");
                                            String texto = "";
                                            //Leemos la respuesta
                                            for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                                texto += t;
                                            }
                                            mistream = new XStream();//Transformación a XML
                                            o = mistream.fromXML(xml_recibido);
                                            if (o instanceof MensajeRespuesta){
                                                mensaje = (MensajeRespuesta) o;
                                                consolaesp.println(mistream.fromXML(mensaje.getTexto()));
                                            }
                                        }
                                    }catch (Exception e){
                                        
                                    }
                            }
                        }else if(o instanceof MensajeErrorNIF){
                            //Se ha producido un fallo con el huésped: no se ha encontrado con ese nif
                            //System.out.println("Rama de error");
                            MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                            System.out.println("No se ha encontrado el huésped con NIF: "+
                                            mensaje.getNIF());
                        }
                        
                    }
                    
                }
                catch (Exception e){

                }
                break;
            case 2:
                //Consulta de huésped mediante Nombre y Apellidos
                consolaesp.print("Inserte Nombre: ");
                String minombre = sc.readLine();
                consolaesp.print("Inserte Apellidos: ");
                String apellido = sc.readLine();
                //tipoConsulta es el parámetro que se encargará de diferenciar la acción en el servidor
                tipoConsulta = "ConsultaHuespedNombre";
                try {
                    url = new URL(ruta + "?tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
                            "&Nombre="+ URLEncoder.encode(minombre,"UTF-8")+
                            "&Apellidos="+URLEncoder.encode(apellido,"UTF-8"));
                    //consolaesp.println("Ejecutando en url: "+url.toString());
                    conn = (HttpURLConnection) url.openConnection();
                    /* Realizamos la conexion */
                    InputStream is = conn.getInputStream();
                    int codigo_http = conn.getResponseCode();
                    if (codigo_http/100 != 2) {
                        consolaesp.println("Error HTTP "+codigo_http);
                    }
                    else {
                        //consolaesp.println("Esperando respuesta");
                        BufferedReader lector =
                        new BufferedReader(new InputStreamReader(is));
                        //consolaesp.println("Respuesta: ");
                        String xml_recibido = "";
                        for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                            xml_recibido += t;
                        }
                        XStream mistream = new XStream();
                        Object o = mistream.fromXML(xml_recibido);
                        if (o instanceof MensajeRespuesta){
                            MensajeRespuesta mensaje = (MensajeRespuesta) o;
                            Huesped recibido = (Huesped) mistream.fromXML(mensaje.getTexto());
                            consolaesp.println(recibido.toString());
                            System.out.println("¿Qué desea hacer?\n0.- Menú principal\n1.-Modificar\n2.-Eliminar");
                            i = Integer.parseInt(sc.readLine());
                            switch (i){
                                case 0://Salimos
                                    menu_ppal();
                                    break;
                                case 1:
                                    //Modificamos los datos del huésped -> POST
                                    String nif_anterior = recibido.getNif();
                                    String nombre ="";
                                    while (nombre.equals("")){
                                        consolaesp.print("Inserte Nombre: ");
                                        nombre = sc.readLine();
                                    }
                                    String apellidos = "";
                                    while (apellidos.equals("")){
                                        consolaesp.print("Inserte Apellidos: ");
                                        apellidos = sc.readLine();
                                    }
                                    nif = "";
                                    while ( nif.equals("")){
                                        consolaesp.print("Inserte NIF: ");
                                        nif = sc.readLine();
                                    }
                                    String fechaNacimiento = "";
                                    while (fechaNacimiento.equals("")){
                                        consolaesp.print("Inserte Fecha de nacimiento (dd/mm/YYYY): ");
                                        fechaNacimiento = sc.readLine();
                                    }
                                    String direccion = "";
                                    while (direccion.equals("")){
                                        consolaesp.print("Inserte Direccion: ");
                                        direccion = sc.readLine();
                                    }
                                    String localidad = "";
                                    while (localidad.equals("")){
                                        consolaesp.print("Inserte Localidad: ");
                                        localidad = sc.readLine();
                                    }
                                    String codigoPostal = "";
                                    while (codigoPostal.equals("")){
                                        consolaesp.print("Inserte Código Postal: ");
                                        codigoPostal = sc.readLine();
                                    }
                                    String provincia = "";
                                    while (provincia.equals("")){
                                        consolaesp.print("Inserte Provincia: ");
                                        provincia = sc.readLine();
                                    }
                                    consolaesp.print("Inserte Móvil (opcional): ");
                                    String movil = sc.readLine();
                                    consolaesp.print("Inserte Fijo (opcional): ");
                                    String fijo = sc.readLine();
                                    consolaesp.print("Inserte Email (opcional): ");
                                    String email = sc.readLine();
                                    if (email.equals("")){
                                        email = "Sin datos";
                                    }
                                    if (movil.equals("")){
                                        movil = "Sin datos";
                                    }
                                    if (fijo.equals("")){
                                        fijo = "Sin datos";
                                    }
                                    //Consulta del tipo modificarHuesped para el servidor
                                    tipoConsulta = "modificarHuesped";
                                    try {
                                        url = new URL(ruta );
                                        //Guardamos los parámetros en el cuerpo del mensaje
                                        String parametros ="tipoConsulta="+tipoConsulta+"&NifAnterior="+nif_anterior+"&Nombre=" +
                                        URLEncoder.encode(nombre,"UTF-8" )+"&"+
                                        "Apellidos=" +
                                        URLEncoder.encode(apellidos,"UTF-8" )+"&"+
                                        "Nif=" +
                                        URLEncoder.encode(nif,"UTF-8" )+"&"+
                                        "FechaNacimiento=" +
                                        URLEncoder.encode(fechaNacimiento,"UTF-8" )+"&"+
                                        "Direccion=" +
                                        URLEncoder.encode(direccion,"UTF-8" )+"&"+
                                        "Localidad=" +
                                        URLEncoder.encode(localidad,"UTF-8" )+"&"+
                                        "CodigoPostal=" +
                                        URLEncoder.encode(codigoPostal,"UTF-8" )+"&"+
                                         "Provincia=" +
                                        URLEncoder.encode(provincia,"UTF-8" )+"&"+
                                        "Movil=" +
                                        URLEncoder.encode(movil,"UTF-8" )+"&"+
                                        "Fijo=" +
                                        URLEncoder.encode(fijo,"UTF-8" )+"&"+
                                        "Email=" +
                                        URLEncoder.encode(email,"UTF-8" );
                                        consolaesp.println("Parámetros de envío: "+parametros);
                                        conn = (HttpURLConnection) url.openConnection();
                                        conn.setDoOutput(true); // Establece el verbo POST
                                        OutputStreamWriter writer = new
                                        OutputStreamWriter(conn.getOutputStream());
                                        writer.write(parametros);
                                        writer.flush(); // Envía el cuerpo del mensaje
                                        /* Realizamos la conexión */
                                        is = conn.getInputStream();
                                        codigo_http = conn.getResponseCode();
                                        if (codigo_http/100 != 2) {
                                            consolaesp.println("Error HTTP "+codigo_http);
                                        }
                                        else {
                                            consolaesp.println("Esperando respuesta");
                                            lector =
                                            new BufferedReader(new InputStreamReader(is));
                                            consolaesp.println("Respuesta: ");
                                            String texto = "";
                                            //Leemos la respuesta obtenida
                                            for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                                texto += t;
                                            }
                                            mistream = new XStream();//La transformamos a XML
                                            o = mistream.fromXML(xml_recibido);
                                            if (o instanceof MensajeRespuesta){
                                                //Todo correcto
                                                mensaje = (MensajeRespuesta) o;
                                                consolaesp.println(mistream.fromXML(mensaje.getTexto()));
                                            }else if (o instanceof MensajeErrorNIF){
                                                //Ya existe un huésped con el nuevo NIF
                                                MensajeErrorNIF mensaje_error = (MensajeErrorNIF) o;
                                                System.out.println("Ya existe un huésped con el NIF: "+mensaje_error.getNIF());
                                            }
                                        }
                                    } 
                                    catch (Exception e){
                                        
                                    }
                                break;
                                case 2:
                                    //Eliminar huésped -> POST
                                    tipoConsulta = "EliminarHuesped";
                                    try {
                                        url = new URL(ruta);
                                        String parametros ="tipoConsulta="+tipoConsulta+"&nif="+recibido.getNif();
                                        //consolaesp.println("Parámetros de envío: "+parametros);
                                        conn = (HttpURLConnection) url.openConnection();
                                        conn.setDoOutput(true); // Establece el verbo POST
                                        OutputStreamWriter writer = new
                                        OutputStreamWriter(conn.getOutputStream());
                                        writer.write(parametros);
                                        writer.flush(); // Envía el cuerpo del mensaje
                                        /* REalizamos la conexión */
                                        is = conn.getInputStream();
                                        codigo_http = conn.getResponseCode();
                                        if (codigo_http/100 != 2) {
                                            consolaesp.println("Error HTTP "+codigo_http);
                                        }
                                        else{
                                            //consolaesp.println("Esperando respuesta");
                                            lector =
                                            new BufferedReader(new InputStreamReader(is));
                                            consolaesp.println("Respuesta: ");
                                            String texto = "";
                                            for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                                texto += t;
                                            }
                                            mistream = new XStream();
                                            o = mistream.fromXML(xml_recibido);
                                            if (o instanceof MensajeRespuesta){
                                                mensaje = (MensajeRespuesta) o;
                                                consolaesp.println(mistream.fromXML(mensaje.getTexto()));
                                            }
                                        }
                                    }catch (Exception e){
                                        
                                    }
                            }
                        }else if(o instanceof MensajeErrorNameSurname){
                            MensajeErrorNameSurname mensaje = (MensajeErrorNameSurname) o;
                            System.out.println("No se ha encontrado el huésped con Nombre y Apellidos: "+
                                            mensaje.getNombre()+" "+mensaje.getApellidos());
                        }
                    }
                } 
                catch (Exception e){

                }
                break;
            case 0:
                menu_ppal();
            default:
                consultarHuesped();
        }
        menu_ppal();
    }
    
    //Método que se encarga de realizar la operación Crear nuevo Huésped
    public void añadirHuesped() throws ParseException, IOException{
        
        //Introducción de datos->POST
        URL url;
        HttpURLConnection conn;
       
        String nombre ="";
        while (nombre.equals("")){
            System.out.print("Inserte Nombre: ");
            nombre = sc.readLine();
        }
        String apellidos = "";
        while (apellidos.equals("")){
            System.out.print("Inserte Apellidos: ");
            apellidos = sc.readLine();
        }
        String nif = "";
        while ( nif.equals("")){
            System.out.print("Inserte NIF: ");
            nif = sc.readLine();
        }
        String fechaNacimiento = "";
        while (fechaNacimiento.equals("")){
            System.out.print("Inserte Fecha de nacimiento (dd/mm/YYYY): ");
            fechaNacimiento = sc.readLine();
        }
        String direccion = "";
        while (direccion.equals("")){
            consolaesp.print("Inserte Direccion: ");
            direccion = sc.readLine();
        }
        String localidad = "";
        while (localidad.equals("")){
            consolaesp.print("Inserte Localidad: ");
            localidad = sc.readLine();
        }
        String codigoPostal = "";
        while (codigoPostal.equals("")){
            System.out.print("Inserte Código Postal: ");
            codigoPostal = sc.readLine();
        }
        String provincia = "";
        while (provincia.equals("")){
            consolaesp.print("Inserte Provincia: ");
            provincia = sc.readLine();
        }
        System.out.print("Inserte Móvil (opcional): ");
        String movil = sc.readLine();
        System.out.print("Inserte Fijo (opcional): ");
        String fijo = sc.readLine();
        System.out.print("Inserte Email (opcional): ");
        String email = sc.readLine();
        if (email.equals("")){
            email = "Sin datos";
        }
        if (movil.equals("")){
            movil = "Sin datos";
        }
        if (fijo.equals("")){
            fijo = "Sin datos";
        }
        String tipoConsulta = "nuevoHuesped";
        try {
            url = new URL(ruta );
            String parametros ="tipoConsulta="+tipoConsulta+"&Nombre=" +
            URLEncoder.encode(nombre,"UTF-8" )+"&"+
            "Apellidos=" +
            URLEncoder.encode(apellidos,"UTF-8" )+"&"+
            "Nif=" +
            URLEncoder.encode(nif,"UTF-8" )+"&"+
            "FechaNacimiento=" +
            URLEncoder.encode(fechaNacimiento,"UTF-8" )+"&"+
            "Direccion=" +
            URLEncoder.encode(direccion,"UTF-8" )+"&"+
            "Localidad=" +
            URLEncoder.encode(localidad,"UTF-8" )+"&"+
            "CodigoPostal=" +
            URLEncoder.encode(codigoPostal,"UTF-8" )+"&"+
             "Provincia=" +
            URLEncoder.encode(provincia,"UTF-8" )+"&"+
            "Movil=" +
            URLEncoder.encode(movil,"UTF-8" )+"&"+
            "Fijo=" +
            URLEncoder.encode(fijo,"UTF-8" )+"&"+
            "Email=" +
            URLEncoder.encode(email,"UTF-8" );
            //consolaesp.println("Parámetros de envío: "+parametros);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Establece el verbo POST
            OutputStreamWriter writer = new
            OutputStreamWriter(conn.getOutputStream());
            writer.write(parametros);
            writer.flush(); // Envía el cuerpo del mensaje
            /* Aquí se fijarían otras características de la
            conexión, pero en este caso no hace falta */
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
            else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeRespuesta){
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    System.out.println(mensaje.getTexto());
                }else if(o instanceof MensajeErrorNIF){
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    System.out.println("Ya existe el huésped con NIF: "+
                                    mensaje.getNIF());
                }
            }

        } 
        catch (Exception e){

        }
        //Método de creación de huésped
        menu_ppal();
    }
    
    //Método que realiza la operación de modificar un cliente existente en el Hotel, primero habrá que realizar
    //una consulta para saber si existe el cliente y después modificarlo
    public void modificarHuesped() throws MalformedURLException, UnsupportedEncodingException, IOException, ParseException{
        URL url;
        HttpURLConnection conn;
        consolaesp.print("Inserte NIF: ");
        String nif_anterior = sc.readLine();
        //Método que llama a consulta de nif
        String tipoConsulta = "ConsultaHuespedNIF";
        try{
            url = new URL(ruta + "?tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
                    "&NIF="+ URLEncoder.encode(nif_anterior,"UTF-8"));
            //consolaesp.println("Ejecutando en url: "+url.toString());
            conn = (HttpURLConnection) url.openConnection();
            /* Aquí se fijarían otras características de la
            conexión, pero en este caso no hace falta */
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
                else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }

                //consolaesp.println("Codigo HTTP: "+codigo_http);
                //}
                //consolaesp.println("XML: "+xml_recibido);
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeErrorNIF){
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    consolaesp.println("No existe el cliente con Nif: " + mensaje.getNIF());
                    //System.out.println("Mensaje correcto");
                }else if( o instanceof MensajeRespuesta){
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    Huesped recibido = (Huesped) mistream.fromXML(mensaje.getTexto());
                    consolaesp.println("Modificar cliente:\n"+recibido.toString());
                    consolaesp.print("¿Desea modificar el cliente?(s/n): ");
                    String opcion = sc.readLine();
                    if (opcion.equals("s")){
                        String nombre ="";
                        while (nombre.equals("")){
                            consolaesp.print("Inserte Nombre: ");
                            nombre = sc.readLine();
                        }
                        String apellidos = "";
                        while (apellidos.equals("")){
                            consolaesp.print("Inserte Apellidos: ");
                            apellidos = sc.readLine();
                        }
                        String nif = "";
                        while ( nif.equals("")){
                            consolaesp.print("Inserte NIF: ");
                            nif = sc.readLine();
                        }
                        String fechaNacimiento = "";
                        while (fechaNacimiento.equals("")){
                            consolaesp.print("Inserte Fecha de nacimiento (dd/mm/YYYY): ");
                            fechaNacimiento = sc.readLine();
                        }
                        String direccion = "";
                        while (direccion.equals("")){
                            System.out.print("Inserte Dirección: ");
                            direccion = sc.readLine();
                        }
                        String localidad = "";
                        while (localidad.equals("")){
                            consolaesp.print("Inserte Localidad: ");
                            localidad = sc.readLine();
                        }
                        String codigoPostal = "";
                        while (codigoPostal.equals("")){
                            System.out.print("Inserte Código Postal: ");
                            codigoPostal = sc.readLine();
                        }
                        String provincia = "";
                        while (provincia.equals("")){
                            consolaesp.print("Inserte Provincia: ");
                            provincia = sc.readLine();
                        }
                        System.out.print("Inserte Móvil (opcional): ");
                        String movil = sc.readLine();
                        System.out.print("Inserte Fijo (opcional): ");
                        String fijo = sc.readLine();
                        System.out.print("Inserte Email (opcional): ");
                        String email = sc.readLine();
                        if (email.equals("")){
                            email = "Sin datos";
                        }
                        if (movil.equals("")){
                            movil = "Sin datos";
                        }
                        if (fijo.equals("")){
                            fijo = "Sin datos";
                        }
                        tipoConsulta = "modificarHuesped";
                        try {
                            url = new URL(ruta );
                            String parametros ="tipoConsulta="+tipoConsulta+"&NifAnterior="+nif_anterior+"&Nombre=" +
                            URLEncoder.encode(nombre,"UTF-8" )+"&"+
                            "Apellidos=" +
                            URLEncoder.encode(apellidos,"UTF-8" )+"&"+
                            "Nif=" +
                            URLEncoder.encode(nif,"UTF-8" )+"&"+
                            "FechaNacimiento=" +
                            URLEncoder.encode(fechaNacimiento,"UTF-8" )+"&"+
                            "Direccion=" +
                            URLEncoder.encode(direccion,"UTF-8" )+"&"+
                            "Localidad=" +
                            URLEncoder.encode(localidad,"UTF-8" )+"&"+
                            "CodigoPostal=" +
                            URLEncoder.encode(codigoPostal,"UTF-8" )+"&"+
                             "Provincia=" +
                            URLEncoder.encode(provincia,"UTF-8" )+"&"+
                            "Movil=" +
                            URLEncoder.encode(movil,"UTF-8" )+"&"+
                            "Fijo=" +
                            URLEncoder.encode(fijo,"UTF-8" )+"&"+
                            "Email=" +
                            URLEncoder.encode(email,"UTF-8" );
                            //consolaesp.println("Parámetros de envío: "+parametros);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoOutput(true); // Establece el verbo POST
                            OutputStreamWriter writer = new
                            OutputStreamWriter(conn.getOutputStream());
                            writer.write(parametros);
                            writer.flush(); // Envía el cuerpo del mensaje
                            /* Realizamos la conexión */
                            is = conn.getInputStream();
                            codigo_http = conn.getResponseCode();
                            if (codigo_http/100 != 2) {
                                consolaesp.println("Error HTTP "+codigo_http);
                            }
                            else {
                                //consolaesp.println("Esperando respuesta");
                                lector =
                                new BufferedReader(new InputStreamReader(is));
                                //consolaesp.println("Respuesta: ");
                                String texto = "";
                                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                    texto += t;
                                }
                                o = mistream.fromXML(texto);
                                if (o instanceof MensajeRespuesta){
                                    //Todo correcto
                                    MensajeRespuesta mensaje_ok = (MensajeRespuesta) o;
                                    System.out.println(mensaje_ok.getTexto());
                                }else if(o instanceof MensajeErrorNIF){
                                    //Ya existe un huésped con el nuevo nif introducido
                                    MensajeErrorNIF mensaje_error = (MensajeErrorNIF) o;
                                    System.out.println("Ya existe el huésped con NIF: "+
                                                    mensaje_error.getNIF());
                                }
                            }
                        } 
                        catch (Exception e){

                        }
                    }
                }
            }
        }catch(Exception e){
            
        }
        //
        menu_ppal();
    }
    
    //Método que se encargará de realizar la operación de eliminación de un cliente existente en el Hotel
    public void eliminarHuesped() throws MalformedURLException, UnsupportedEncodingException, IOException, ParseException{
        //->POST
        URL url;
        HttpURLConnection conn;
        consolaesp.print("Inserte NIF: ");
        String nif = sc.readLine();
        //Método que llama a consulta de nif
        String tipoConsulta = "ConsultaHuespedNIF";
        try{
            url = new URL(ruta + "?tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
                    "&NIF="+ URLEncoder.encode(nif,"UTF-8"));
            //consolaesp.println("Ejecutando en url: "+url.toString());
            conn = (HttpURLConnection) url.openConnection();
            /* Aquí se fijarían otras características de la
            conexión, pero en este caso no hace falta */
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
                else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }
                //consolaesp.println("Codigo HTTP: "+codigo_http);
                //}
                //consolaesp.println("XML: "+xml_recibido);
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeErrorNIF){
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    consolaesp.println("No existe el cliente con Nif: " + mensaje.getNIF());
                    //System.out.println("Mensaje correcto");
                }else if( o instanceof MensajeRespuesta){
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    Huesped recibido = (Huesped) mistream.fromXML(mensaje.getTexto());
                    consolaesp.println("Eliminar cliente:\n"+recibido.toString());
                    consolaesp.print("¿Desea eliminar el cliente?(s/n): ");
                    String opcion = sc.readLine();
                    if (opcion.equals("s")){
                        tipoConsulta = "EliminarHuesped";
                        try {
                            url = new URL(ruta );
                            String parametros ="tipoConsulta="+tipoConsulta+"&nif="+
                            URLEncoder.encode(nif,"UTF-8" );
                            //consolaesp.println("Parámetros de envío: "+parametros);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoOutput(true); // Establece el verbo POST
                            OutputStreamWriter writer = new
                            OutputStreamWriter(conn.getOutputStream());
                            writer.write(parametros);
                            writer.flush(); // Envía el cuerpo del mensaje
                            /* Aquí se fijarían otras características de la
                            conexión, pero en este caso no hace falta */
                            is = conn.getInputStream();
                            codigo_http = conn.getResponseCode();
                            if (codigo_http/100 != 2) {
                                consolaesp.println("Error HTTP "+codigo_http);
                            }
                            else {
                                //consolaesp.println("Esperando respuesta");
                                lector =
                                new BufferedReader(new InputStreamReader(is));
                                //consolaesp.println("Respuesta: ");
                                String texto = "";
                                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                                    texto += t;
                                }
                                o = mistream.fromXML(texto);
                                if (o instanceof MensajeRespuesta){
                                    MensajeRespuesta mensaje_ok = (MensajeRespuesta) o;
                                    System.out.println(mensaje_ok.getTexto());
                                }
                            }
                        } 
                        catch (Exception e){

                        }
                    }
                }
            }
        }catch(Exception e){
            
        }
        //
        menu_ppal();
    }
    
    //Método que se encargará de obtener las reservas del HOTEL mediante fecha de entrada
    public void consultarReservas() throws IOException, ParseException{
        //Consulta -> GET
        URL url;
        HttpURLConnection conn;
        consolaesp.print("Inserte Fecha(dd/mm/YYYY): ");
        String fecha = sc.readLine();
        //Petición de Consulta de reservas
        String tipoConsulta = "ConsultaReservas";
        try {
            url = new URL(ruta + "?tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
                    "&Fecha="+ URLEncoder.encode(fecha,"UTF-8"));
            //consolaesp.println("Ejecutando en url: "+url.toString());
            conn = (HttpURLConnection) url.openConnection();
            /* REalizamos la conexión */
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
                else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeRespuesta){
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    ArrayList<Reserva> recibido = (ArrayList<Reserva>) mistream.fromXML(mensaje.getTexto());
                    for (Reserva valor : recibido){
                        consolaesp.println(valor.toString());
                        System.out.println("¿Qué desea hacer?\n0.-Continuar\n1.-Modificar\n2.-Eliminar");
                        String i = sc.readLine();
                        if (i.equals("1")){
                            //Modificamos la reserva
                            SimpleDateFormat fechaD = new SimpleDateFormat("dd/MM/yyyy");
                            modificarReserva(valor.getHuesped().getNif(),fechaD.format(valor.getFentrada()));
                        }else if(i.equals("2")){
                            //Eliminamos la reserva
                            SimpleDateFormat fechaD = new SimpleDateFormat("dd/MM/yyyy");
                            eliminarReserva(valor.getHuesped().getNif(),fechaD.format(valor.getFentrada()));
                        }else{
                            continue;
                        }
                        
                    }
                }else if(o instanceof MensajeErrorDate){
                    //Fallo con la fecha. No existen reservas con ésta
                    MensajeErrorDate mensaje = (MensajeErrorDate) o;
                    System.out.println("No se ha encontrado ninguna reserva en la fecha: "+
                                    mensaje.getFecha().toString());
                }
            }
        }
        catch (Exception e){

        }
        
        menu_ppal();
    }
    
    //Método que se encargará de modificar reservas creadas en el Hotel
    public void modificarReserva() throws IOException, ParseException{
        
        String nif ="";
        while (nif.equals("")){
            consolaesp.print("Inserte NIF: ");
            nif = sc.readLine();
        }
        String fechae = "";
        while (fechae.equals("")){
            consolaesp.print("Inserte Fecha(DD/MM/YYYY): ");
            fechae = sc.readLine();
        }
        modificarReserva(nif,fechae);
    }
    
    //Método que se encargará de modificar la reserva dados un nif y fecha de entrada conocidos
    public void modificarReserva(String nif, String fechae) throws IOException, ParseException{
        
        String nif_nuevo = "";
        while (nif_nuevo.equals("")){
            System.out.print("Inserte NIF nuevo: ");
            nif_nuevo = sc.readLine();
        }
        String fechae_nueva = "";
        while (fechae_nueva.equals("")){
            System.out.print("Inserte nueva Fecha(DD/MM/YYYY) de entrada:");
            fechae_nueva = sc.readLine();
        }
        String fechas_nueva = "";
        while (fechas_nueva.equals("")){
            System.out.print("Inserte nueva Fecha(DD/MM/YYYY) de salida:");
            fechas_nueva = sc.readLine();
        }
        SimpleDateFormat fechaD = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = fechaD.parse(fechae);
        //Método de envío
        URL url;
        HttpURLConnection conn;
        String tipoConsulta = "modificarReserva";
        //Método que llama a consulta de nif
        try{
            url = new URL(ruta);
            String parametros = "tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
            "&nif="+ URLEncoder.encode(nif,"UTF-8")+
            "&fechae="+URLEncoder.encode(fechae,"UTF-8")+
            "&fechaeN="+URLEncoder.encode(fechae_nueva,"UTF-8")+
            "&nifN="+ URLEncoder.encode(nif_nuevo,"UTF-8")+
            "&fechasN="+URLEncoder.encode(fechas_nueva,"UTF-8");
            //consolaesp.println("Ejecutando en url: "+url.toString());
            /* Aquí se fijarían otras características de la
            conexión, pero en este caso no hace falta */
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Establece el verbo POST
            OutputStreamWriter writer = new
            OutputStreamWriter(conn.getOutputStream());
            writer.write(parametros);
            writer.flush();
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
            else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeRespuesta){
                    //Todo ok
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    consolaesp.println(mensaje.getTexto());
                }else if(o instanceof MensajeErrorNIF){
                    //No existe cliente con ese nif
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    System.out.println("No existe ningún huésped con NIF: "+
                                    mensaje.getNIF());
                }else if(o instanceof MensajeErrorDateNIF){
                    //No existe reserva asociada a ese nif y con esa fecha de entrada
                    MensajeErrorDateNIF mensaje = (MensajeErrorDateNIF) o;
                    System.out.println("No existe reserva con el NIF:"+mensaje.getNif()+" y fecha de entrada: "+
                    mensaje.getFecha().toString());
                }
            }
        }catch(Exception e){
            
        }
        //
    }
    
    //Método que se encargará de eliminar una reserva creada en el hotel
    public void eliminarReserva() throws IOException, ParseException{
        
        String nif ="";
        while (nif.equals("")){
            System.out.print("Inserte NIF: ");
            nif = sc.readLine();
        }
        String fechae = "";
        while (fechae.equals("")){
            System.out.print("Inserte Fecha(DD/MM/YYYY): ");
            fechae = sc.readLine();
        }
        eliminarReserva(nif,fechae);
    }
    
    //Método que se encargará de eliminar una reserva creada en el hotel conocidos su nif y fecha de entrada
     public void eliminarReserva(String nif,String fechae) throws IOException, ParseException{
        
        SimpleDateFormat fechaD = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = fechaD.parse(fechae);
        //Método de envío
        URL url;
        HttpURLConnection conn;
        String tipoConsulta = "EliminarReserva";
        //Método que llama a consulta de nif
        try{
            url = new URL(ruta);
            String parametros = "tipoConsulta=" + URLEncoder.encode(tipoConsulta,"UTF-8")+
            "&nif="+ URLEncoder.encode(nif,"UTF-8") +"&fecha="+URLEncoder.encode(fechae,"UTF-8");
            //consolaesp.println("Ejecutando en url: "+url.toString());
            /* Realizamos la conexión */
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Establece el verbo POST
            OutputStreamWriter writer = new
            OutputStreamWriter(conn.getOutputStream());
            writer.write(parametros);
            writer.flush();
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
            else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String xml_recibido = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    xml_recibido += t;
                }
                XStream mistream = new XStream();
                Object o = mistream.fromXML(xml_recibido);
                if (o instanceof MensajeRespuesta){
                    //Todo ok
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    consolaesp.println(mensaje.getTexto());
                }else if(o instanceof MensajeErrorNIF){
                    //No existen cliente con ese nif
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    System.out.println("No existe ningún huésped con NIF: "+
                                    mensaje.getNIF());
                }else if(o instanceof MensajeErrorDateNIF){
                    //No existe reserva con ese nif en esa fecha
                    MensajeErrorDateNIF mensaje = (MensajeErrorDateNIF) o;
                    System.out.println("No existe reserva con el NIF:"+mensaje.getNif()+" y fecha de entrada: "+
                    mensaje.getFecha().toString());
                }
            }
        }catch(Exception e){
            
        }
        //  
    }
    
     //Método que se encargará de crear una nueva reserva asociada a un cliente
    public void añadirReserva() throws IOException, ParseException{
        
        System.out.print("Inserte NIF: ");
        String NIF = sc.readLine();
        //Comprobación de que el huésped está en la lista
        System.out.print("Inserte Fecha(dd/MM/yyyy) de entrada: ");
        String fecha_entrada = sc.readLine();
        System.out.print("Inserte Fecha(dd/MM/yyyy) de salida: ");
        String fecha_salida = sc.readLine();
        //Envío de información nueva
        URL url;
        HttpURLConnection conn;
        String tipoConsulta = "nuevaReserva";
        try {
            url = new URL(ruta );
            String parametros ="tipoConsulta="+tipoConsulta+"&nif=" +
            URLEncoder.encode(NIF,"UTF-8" )
            +"&fechae=" +
            URLEncoder.encode(fecha_entrada,"UTF-8" )+
            "&fechas=" +
            URLEncoder.encode(fecha_salida,"UTF-8" );
            //consolaesp.println("Parámetros de envío: "+parametros);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // Establece el verbo POST
            OutputStreamWriter writer = new
            OutputStreamWriter(conn.getOutputStream());
            writer.write(parametros);
            writer.flush(); // Envía el cuerpo del mensaje
            /* Realizamos la conexión */
            InputStream is = conn.getInputStream();
            int codigo_http = conn.getResponseCode();
            if (codigo_http/100 != 2) {
                consolaesp.println("Error HTTP "+codigo_http);
            }
            else {
                //consolaesp.println("Esperando respuesta");
                BufferedReader lector =
                new BufferedReader(new InputStreamReader(is));

                //consolaesp.println("Respuesta: ");
                String texto = "";
                for (String t = lector.readLine(); t!=null; t= lector.readLine()){
                    texto += t;
                }
                
                //consolaesp.println(texto);
                XStream mistream = new XStream();
                Object o = mistream.fromXML(texto);
                if (o instanceof MensajeRespuesta){
                    MensajeRespuesta mensaje = (MensajeRespuesta) o;
                    consolaesp.println(mensaje.getTexto());
                }else if(o instanceof MensajeErrorNIF){
                    MensajeErrorNIF mensaje = (MensajeErrorNIF) o;
                    System.out.println("No existe huésped con NIF:"+mensaje.getNIF());
                }
                
            }
        }
        catch (Exception e){

        }
        //Método de creación de huésped
        menu_ppal();
    }
    
    //Menú principal de selección de las distintas operaciones que facilita la aplicación
    public void menu_ppal() throws IOException, ParseException{
        System.out.println("\n\nHotel Distribuido\n----------------");
        System.out.println("1.- Consultar huésped");
        System.out.println("2.- Añadir huésped");
        System.out.println("3.- Modificar huésped");
        System.out.println("4.- Eliminar huésped");
        System.out.println("5.- Consulta reservas");
        System.out.println("6.- Añadir reserva");
        System.out.println("7.- Modificar reserva");
        System.out.println("8.- Eliminar reserva");
        System.out.println("0.- Salir");
        try{
            int i = Integer.parseInt(sc.readLine());
            switch (i){
                case 1: consultarHuesped();break;
                case 2: añadirHuesped(); break;
                case 3: modificarHuesped();break;
                case 4: eliminarHuesped();break;
                case 5: consultarReservas();break;
                case 6: modificarReserva();break;
                case 7: añadirReserva();break;
                case 8: eliminarReserva();break;
                case 0: break;
            }
        }catch (Exception e){
            menu_ppal();
        }
        
    }
    
}