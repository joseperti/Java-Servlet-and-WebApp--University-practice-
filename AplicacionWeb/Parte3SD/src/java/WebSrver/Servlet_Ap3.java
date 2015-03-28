package WebSrver;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author Sergio
 */
@WebServlet(name = "Servlet_Ap3", urlPatterns = {"/Servlet_Ap3"})
public class Servlet_Ap3 extends HttpServlet {
    //Como argumentos tendremos una ListaClientes y una ListaReservas que inicializamos a continuación.
    private ListaClientes lClientes = new ListaClientes();
    private ListaReservas lReservas = new ListaReservas(); 

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    //Aqui llegarán las operaciones de Consulta. El nombre del Parametro escogido para conocer que tipo de
    //consulta debemos realizar es TipoConsulta.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //Obtenemos el parametro mencionado anteriormente
        String TipoConsulta = request.getParameter("TipoConsulta");
            
        if(TipoConsulta.equals("NIF")){
            //Si TipoConsulta es NIF estamos ante una consulta por NIF
            String nif=request.getParameter("NIF");
            boolean buenNIF=(nif!=null);
            Huesped cliente=lClientes.ConsultaHuespedNIF(nif);

                
            if(buenNIF){
                //Si el NIF  es correcto miramos si el cliente se encuentra en nuestra lista.
                boolean clienteEncontrado=(cliente!=null);
                if(clienteEncontrado){
                //Si el cliente se ha encontrado generamos la consulta    
                this.generarConsulta2(cliente, response);
                }else{
                    //Si no hemos encontrado al huesped permitimos al usuario añadirlo, notificandole lo ocurrido.
                    this.generarAddCliente("", "", nif, response,null);
                }
             
            }else{
                //En caso de que el NIF no sea correcto mostramos un mensaje de Error
                this.generarError("El NIF introducido no es válido.", response);
            }
                
        }else if(TipoConsulta.equals("NombreYApellidos")){
            //Estamos ante una consulta por Nombre y Apellidos.
                String nombre=new String(request.getParameter("Nombre").getBytes("ISO-8859-1"),"UTF-8");
                String apellidos=new String(request.getParameter("Apellidos").getBytes("ISO-8859-1"),"UTF-8");
                boolean buenNombre=(!nombre.isEmpty() && !apellidos.isEmpty());
                if(buenNombre){
                    //Si el nombre y apellidos son correctos, miramos si el Huesped se encuenta en nuestra lista.
                    Huesped cliente=lClientes.ConsultaHuespedNombre(nombre, apellidos);
                    boolean clienteEncontrado=(cliente!=null);
                    if(clienteEncontrado){
                        //Si el cliente se encuentra en la lista, generamos la consulta.
                        this.generarConsulta2(cliente, response);      
                    }else{
                        //Si no encontramos el Huesped, damos la opción al usuario de añadirlo.
                        this.generarAddCliente(nombre, apellidos, "", response,null);
                    }
                }else{
                    //Si el nombre o apellidos no son validos generamos el error asociado.
                   this.generarError("El nombre o Apellidos introducidos no son validos", response);
                }
                
            }else if(TipoConsulta.equals("Reserva")){
                //Estamos ante una consulta de Reservas por fecha de Entrada.
                if(this.esBuenFormulario(request)){
                    try {
                        //Todos los campos estan correcto
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaRecibida = sdf.parse(request.getParameter("FechaEntrada"));
                        ArrayList<Reserva> reservas = this.lReservas.ConsultaHuespedFechaEntrada(fechaRecibida);
                        if(reservas.isEmpty()){
                            //No hay Reservas asociadas a esa fecha por lo que notificamos el error.
                            this.generarError("No existe reservas en el día: "+sdf.format(fechaRecibida), response);
                        }else{
                            //Generamos la consulta ya que minimo hay una reserva con esa fecha
                            this.generarConsultaReserva(reservas, response);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    //Algun campo del formulario es incorrecto por lo que generamos el error notificando lo ocurrido.
                    this.generarError("El Formulario no se ha rellenado correctamente", response);
                }
            }else if (TipoConsulta.equals("ModElimReserva")){
                //Estamos ante la consulta para poder Modificar o eliminar una reserva.
                if(this.esBuenFormulario(request)){
                    try {
                        //El formulario tiene todos los campos correctos.
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String nif=request.getParameter("NIF");                    
                        Date fechaE=sdf.parse(request.getParameter("FechaEntrada"));
                        Reserva re=this.lReservas.ConsultaReservaFechaNif(fechaE, nif);
                        if(re==null){
                            //No encontramos reserva con esa fecha y NIF. Generamos Error.
                            this.generarError("No existe reserva con NIF: "+nif+
                                    " y fecha: "+sdf.format(fechaE), response);
                        }else{
                            //Hemos encontrado una reserva.Generamos la pagina para que el cliente pueda
                            //modificar o eliminarla.
                        this.generarModElimReserva(response, re);
                            
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    //El formulario no tiene todos los campos correctos. Generamos Error
                    this.generarError("El Formulario no se ha rellenado Correctamente", response);
                }
            }else if(TipoConsulta.equals("ModElimHuesped")){
                //Peticion de Modificacion o Borrado de Huesped.
                if(this.esBuenFormulario(request)){
                    //Formulario Relleno correctamente.
                    String nif=request.getParameter("NIF");
                    if(this.lClientes.ExisteNIF(nif)){
                        //El cliente solicitado existe
                        this.generarConsulta2(this.lClientes.ConsultaHuespedNIF(nif), response);
                    }else{
                        //El cliente solicitado no existe. Generamos error.
                        this.generarError("No existe cliente con el NIF: "+nif, response);
                    }
                }else{
                    //Algun campo del formulario no es correcto. Generamos error.
                    this.generarError("El Formulario no se relleno correctamente.", response);
                }
            }else{
                //El recurso solicitado no se encuentra entre los disponibles. Generamos el error correspondiente
                this.generarError("El recurso solicitado no ha sido encontrado", response);
            }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Estamos ante una petición de tipo POST. El nombre del parametro utilizado para conocer que operacion
        //Hay que realizar es de nuevo TipoConsulta.
        processRequest(request, response);
        if(request.getParameter("TipoConsulta").equals("AnadirCliente")){
            //Estamos ante una petición de Añadir Huesped
            if(this.esBuenFormulario(request)){
                //El formulario es correcto.
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String nombre=new String(request.getParameter("Nombre").getBytes("ISO-8859-1"),"UTF-8");
                    String apellidos=new String(request.getParameter("Apellidos").getBytes("ISO-8859-1"),"UTF-8");
                    String nif=new String(request.getParameter("NIF").getBytes("ISO-8859-1"),"UTF-8");
                    String calle=new String(request.getParameter("Calle").getBytes("ISO-8859-1"),"UTF-8");
                    String provincia=new String(request.getParameter("Provincia").getBytes("ISO-8859-1"),"UTF-8");
                    String localidad=new String(request.getParameter("Localidad").getBytes("ISO-8859-1"),"UTF-8");
                    String cp = request.getParameter("CP");
                    String movil=new String(request.getParameter("Movil").getBytes("ISO-8859-1"),"UTF-8");
                    String fijo=new String(request.getParameter("Fijo").getBytes("ISO-8859-1"),"UTF-8");
                    String email=new String(request.getParameter("Email").getBytes("ISO-8859-1"),"UTF-8");
                    Domicilio dom= new Domicilio(calle,localidad,provincia,cp);
                    Date fechaN=sdf.parse(request.getParameter("FechaNacimiento"));
                    Huesped cli=new Huesped(nombre,apellidos,nif,fechaN,dom,fijo,movil,email);
                    this.lClientes.addHuesped(cli);
                    if(request.getParameter("LlevoFechas").equals("Si")){
                       //Si llevoFechas es Si quiere decir que estamos ante una operacion de añadir cliente
                      //resultante de haber intentado añadir una reserva a un cliente no existente.
                      //Debemos añadir la reserva asociada al cliente
                        sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date fechaEntrada= sdf.parse(request.getParameter("FechaEntrada"));
                        Date fechaSalida= sdf.parse(request.getParameter("FechaSalida"));
                        Reserva re = new Reserva(nif,fechaEntrada,fechaSalida);
                        this.lReservas.addReserva(re);
                    }
                    //La operacion se ha realizado con exito por lo que generamos este.
                    this.generarExito("Cliente creado correctamente",response);
                } catch (ParseException ex) {
                    this.generarError("El Formulario no se relleno correctamente.", response);
                    Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
                //El formulario presenta algun campo mal rellenado por lo que generamos el error asociado.
                this.generarError("Algun campo del formulario es incorrecto", response);
            }
        }else if(request.getParameter("TipoConsulta").equals("Eliminar")){
            //Estamos ante una operacion de eliminar a un Huesped
            String nif=new String(request.getParameter("OldNIF").getBytes("ISO-8859-1"),"UTF-8"); 
            Huesped cliente = this.lClientes.ConsultaHuespedNIF(nif);
            this.lClientes.removeHuesped(cliente);
            //Generamos el exito de la operación.
            this.generarExito("Cliente eliminado correctamente",response);
        }else if(request.getParameter("TipoConsulta").equals("Modificar")){
            //Estamos ante una operacion de Modificacion de Huesped.
            if(this.esBuenFormulario(request)){
                //El formulario esta correcto.
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String oldnif=new String(request.getParameter("OldNIF").getBytes("ISO-8859-1"),"UTF-8");
                    Huesped cliente = this.lClientes.ConsultaHuespedNIF(oldnif);
                    this.lClientes.removeHuesped(cliente);
                    
                    String nombre=new String(request.getParameter("Nombre").getBytes("ISO-8859-1"),"UTF-8");
                    String apellidos=new String(request.getParameter("Apellidos").getBytes("ISO-8859-1"),"UTF-8");
                    String nif=new String(request.getParameter("NIF").getBytes("ISO-8859-1"),"UTF-8");
                    String calle=new String(request.getParameter("Calle").getBytes("ISO-8859-1"),"UTF-8");
                    String provincia=new String(request.getParameter("Provincia").getBytes("ISO-8859-1"),"UTF-8");
                    String localidad=new String(request.getParameter("Localidad").getBytes("ISO-8859-1"),"UTF-8");
                    String cp = request.getParameter("CP");
                    String movil=new String(request.getParameter("Movil").getBytes("ISO-8859-1"),"UTF-8");
                    String fijo=new String(request.getParameter("Fijo").getBytes("ISO-8859-1"),"UTF-8");
                    String email=new String(request.getParameter("Email").getBytes("ISO-8859-1"),"UTF-8");
                    Domicilio dom= new Domicilio(calle,localidad,provincia,cp);
                    Date fechaN=sdf.parse(request.getParameter("FechaNacimiento"));
                    Huesped cli=new Huesped(nombre,apellidos,nif,fechaN,dom,fijo,movil,email);
                    this.lClientes.addHuesped(cli);
                    //Tomamos los datos del formulario, creamos el cliente, lo añadimos y 
                    //Generamos el exito correspondiente.
                    this.generarExito("Cliente modificar correctamente",response);
                } catch (ParseException ex) {
                    this.generarError("Error en los parámetros", response);
                    Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                //DAREMOS ERROR PORQUE PARMETROS DE MODIICACION NO VALIDOS
                this.generarError("Rellene todos los campos del formulario correctamente", response);
            }
        }else if(request.getParameter("TipoConsulta").equals("AddReserva")){
            //Estamos ante una peticion de Añadir Reserva
            if(this.esBuenFormulario(request)){
                //El formulario esta correcto
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String nif=request.getParameter("NIF"); //Obtenemos el parametro NIF
                boolean existeCliente=this.lClientes.ExisteNIF(nif); //Comprobamos si el cliente ya esta registrado                
                if(existeCliente){
                    try {
                        //Si el cliente existe procederemos a comprobar las fechas
                        Date fechaEn = sdf.parse(request.getParameter("FechaEntrada"));
                        Date fechaSa = sdf.parse(request.getParameter("FechaSalida"));
                        if(fechaSa.after(fechaEn)){
                            //Las fechas estan corretcas y añadimos la reserva
                            Reserva re= new Reserva(nif,fechaEn,fechaSa);
                            this.lReservas.addReserva(re);
                            this.generarExito("Reserva creada correctamente",response);
                        }else{
                            //Las fechas estan mal. Damos error
                            this.generarError("Las fechas son erróneas", response);
                        }
                    } catch (ParseException ex) {
                        this.generarError("Error en los parámetros", response);
                        Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try {
                        //Si el cliente no existe redirigimos a la pagina de añadir Huesped.
                        Date fechaEn = sdf.parse(request.getParameter("FechaEntrada"));
                        Date fechaSa = sdf.parse(request.getParameter("FechaSalida"));
                        Date[] fechas = new Date[2];
                        fechas[0]=fechaEn;
                        fechas[1]=fechaSa;
                        this.generarAddCliente("", "", nif, response,fechas);
                    } catch (ParseException ex) {
                        this.generarError("Error en los parámetros", response);
                        Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
              
            }else{
                //Si algun campo del formulario esta vacio lanzamos error
                this.generarError("Rellene todos los campos del formulario", response);
                
            }
        }else if(request.getParameter("TipoConsulta").equals("EliminarReserva")){
            try {
                //Estamos ante una peticion de Eliminacion de Reserva.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String nif=request.getParameter("NIF");
                Date fechaE = sdf.parse(request.getParameter("FechaEntrada"));
                //Obtenemos la reserva que debemos eliminar y la quitamos de la lista.
                Reserva re=this.lReservas.ConsultaReservaFechaNif(fechaE, nif);
                this.lReservas.removeReserva(re);
                //Enviamos mensaje de operacion realizda con exito y redirigimos a la Pagina Principal
                this.generarExito("Reserva eliminada correctamente",response);
            } catch (ParseException ex) {
                //Damos mensaje de error.
                this.generarError("Error en los parámetros", response);
                Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(request.getParameter("TipoConsulta").equals("ModificarReserva")){
            try {
                //Estamos ante una peticion de Modificación de reserva.
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String nif=request.getParameter("NIF");
                String habitacion=request.getParameter("Habitacion");
                Date fechaE = sdf.parse(request.getParameter("FechaEntrada"));
                Date fechaSa = sdf.parse(request.getParameter("FechaSalida"));
                Reserva re=this.lReservas.ConsultaReservaFechaNif(fechaE, nif);
                this.lReservas.removeReserva(re);
                Reserva nueva=new Reserva(nif,fechaE,fechaSa,Integer.parseInt(habitacion));
                //Actualizamos la Reserva
                this.lReservas.addReserva(nueva);
                this.generarExito("Reserva modificada correctamente",response);
            } catch (ParseException ex) {
                //mensaje de Error.
                this.generarError("Error en los parámetros", response);
                Logger.getLogger(Servlet_Ap3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            this.generarError("La consulta: "+request.getParameter("TipoConsulta")
                    +" ,no está disponible", response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    //El método generarError genera un HTML dinámico que se usará para indicar que se ha producido un error durante alguna operación.
    //Recibe como parametro un String que representará el mótivo por el cual se ha producido el error.
    public void generarError(String mensajeError, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
                    try(PrintWriter out = response.getWriter()){
                        out.println("<html>");
                        out.println("	<head>");
                        out.println("		<title>Error</title>");
                        out.println("	    <meta charset=\"UTF-8\">");
                        out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/error.css\">");
                        out.println("");
                        out.println("	</head>");
                        out.println("	<body >");
                        out.println("		<header><h1><font color=\"black\">Error: </font></h1></header>");
                        out.println("		<div class='mensaje_error'><p><font size=\"5\">"+mensajeError+"</font></p>");
                        out.println("		<br>");
                        out.println("		<table>");
                        out.println("			<tr>");
                        out.println("				<td><a href=\"index.html\">Volver a la Pagina Principal</a></td>");
                        out.println("			</tr>");
                        out.println("			<tr>");
                        out.println("				<td><a href=\"javascript:window.history.back();\">Realizar Nueva Consulta</a></td>");
                        out.println("			</tr>");
                        out.println("		</table></div>");
                        out.println("");
                        out.println("	</body>");
                        out.println("</html>");

                    }
    }
    //El método generarExito genera una pagina dinámica de HTML que se mostrará cada vez que una operación
    //se realice exitosamente. A los 5 segundos se redirige a la pagina principal. Recibe un String que 
    //indicará que tipo de operación resulto exitosa.
    public void generarExito(String mensajeExito, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
                    try(PrintWriter out = response.getWriter()){
                        out.println("<html>");
                        out.println("	<head>");
                        out.println("	    <meta charset=\"UTF-8\">");
                        out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/exito.css\">");
                        out.println("<script>setTimeout(function(){location.href = \"index.html\"},6000);</script>");
                        out.println("");
                        out.println("	</head>");
                        out.println("	<body >");
                        out.println("		<title>Operación efectuada</title><header><h1><font color=\"black\"></font></h1></header>");
                        out.println("		<div class='mensaje_error'><p><font size=\"5\">"+mensajeExito+"<br>"
                                + "             Redirigiendo a la página principal en unos 5 segundos...</font></p>");
                        out.println("		<br>");
                        out.println("		<table>");
                        out.println("			<tr>");
                        out.println("				<td><a href=\"index.html\">Volver a la Pagina Principal</a></td>");
                        out.println("			</tr>");
                        out.println("			<tr>");
                        out.println("				<td><a href=\"javascript:window.history.back();\">Realizar Nueva Operación</a></td>");
                        out.println("			</tr>");
                        out.println("		</table></div>");
                        out.println("");
                        out.println("	</body>");
                        out.println("</html>");

                    }
    }
    //El método generarAddCliente genera una página dinámica de HTML que permite añadir un nuevo Huesped
    //en caso de que una consulta de Huesped no resulte exitosa.
    //Además permite completar una reserva si al intentar añadirla introujimos un Huesped del que no se tenia constancia.
    //En este caso guardará las fechas en campos olcultos.
    public void generarAddCliente(String nombre, String apellidos, String NIF, HttpServletResponse response, Date[] fechas) throws IOException{
        String boton;
        String llevoFechas;
        if(fechas==null){
            boton="Incluir Huesped";
            llevoFechas="No";   
        }else{
            boton="Añadir Reserva";
            llevoFechas="Si";
        }
        
         response.setContentType("text/html;charset=UTF-8");
         try(PrintWriter out = response.getWriter()){
                out.println("<html>");
                out.println("	<head>");
                out.println("		<title>A&ntildeadir Nuevo Huesped</title>");
                out.println("	    <meta charset=\"UTF-8\">");
                out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\">");
                out.println("	</head>");
                out.println("		<body >");
                out.println("		<h1>Error: El cliente introducido no se ha encontrado.</h1>");
                out.println("		<h2><img src=\"Img/AddPersona.png\" width=\"25\"> A&ntildeadir Nuevo Huesped:</h2>");
                out.println("		<form action=\"Servlet_Ap3\" method=\"post\">");
                out.println("		<table>");
                out.println("			<tr>");
                out.println("				<td><b>* Nombre: </b></td><td><input type=\"text\" name=\"Nombre\" value=\""+nombre+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Apellidos: </b></td><td><input type=\"text\" name=\"Apellidos\" value=\""+apellidos+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* NIF: </b></td><td><input type=\"text\" name=\"NIF\" value=\""+NIF+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Nacimiento: </b></td><td><input type=\"date\" name=\"FechaNacimiento\" max=\"2020-12-31\" min=\"1950-12-31\" required>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Direcci&oacuten: </b></td><td><input type=\"text\" name=\"Calle\" size=\"20\" required></td>");
                out.println("");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Provincia: </b></td><td><input type=\"text\" name=\"Provincia\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Localidad: </b></td><td><input type=\"text\" name=\"Localidad\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("");
                out.println("			<tr>");
                out.println("				<td><b>* C&oacutedigo Postal: </b></td><td><input type=\"text\" name=\"CP\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("");
                out.println("			<tr>");
                out.println("				<td><b>Fijo: </b></td><td><input type=\"tel\" name=\"Fijo\" size=\"20\"></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>M&oacutevil: </b></td><td><input type=\"tel\" name=\"Movil\" size=\"20\"></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>Email: </b></td><td><input type=\"email\" name=\"Email\" size=\"20\"></td>");
                out.println("			</tr>");
                out.println("");
                out.println("		</table>");
                out.println("		<br>");
                out.println("		<p><font style=\"italic\">Los campos con asterisco (*) son <b>obligatorios</b>.</font></p>");
                out.println("		<br>");
                out.println("<input type=\"hidden\" name=\"TipoConsulta\" value=\"AnadirCliente\">");
                out.println("<input type=\"hidden\" name=\"LlevoFechas\" value=\""+llevoFechas+"\">");
                
                if(fechas!=null){
                    //Metemos las fechas de entrada y salida en campos ocultos
                     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                     String fEntrada=sdf.format(fechas[0]);
                     String fSalida=sdf.format(fechas[1]);
                     out.println("<input type=\"hidden\" name=\"FechaEntrada\" value=\""+fEntrada+"\">");
                     out.println("<input type=\"hidden\" name=\"FechaSalida\" value=\""+fSalida+"\">");
                }
                
                out.println("		<input type=\"submit\" value=\""+boton+"\">");
                out.println("		</form>");
                out.println("		<br>");
                out.println("		<a href=\"index.html\">Volver a Página Principal</a>");
                out.println("	</body>	");
                out.println("</html>");
             
         }
    }
    //Este método genera una pagina dinamica de HTML que se usará para mostrar el resultado de una consulta de Huesped.
    //Recibe como parametro un Huesped del que mostrará la información.
    //Además tambien permite Modificar o Eliminar al Huesped mostrado.
    public void generarConsulta2(Huesped cliente, HttpServletResponse response) throws IOException{
                response.setContentType("text/html;charset=UTF-8");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try(PrintWriter out = response.getWriter()){
                out.println("<html>");
                out.println("	<head>");
                out.println("		<title>Resultados Consulta</title>");
                out.println("	    <meta charset=\"UTF-8\">");
                out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\">");
                out.println("	</head>");
                out.println("	<body>");
                out.println("		<body >");
                out.println("		<h2><img src=\"Img/Lupa.png\" width=\"25\">Resultado de la Consulta:</h2>");
                out.println("<p><font size=\"4\" color=\"darkred\">El resultado de la busqueda se muestra a continuaci&oacuten.<br>Si desea eliminar al ");
                out.println("Huesped pulse \"Eliminar Huesped\".<br>Si desea cambiar sus datos, modifiquelos y pulse \"Modificar Cliente\".</font></p>");
                out.println("		<form action=\"Servlet_Ap3\" method=\"post\">");
                out.println("		<table>");
                out.println("			<tr>");
                out.println("				<td><b>* Nombre: </b></td><td><input type\"text\" name=\"Nombre\" value=\""+this.stringToHTMLString(cliente.getNombre())+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Apellidos: </b></td><td><input type=\"text\" name=\"Apellidos\" value=\""+this.stringToHTMLString(cliente.getApellidos())+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* NIF: </b></td><td><input type=\"text\" name=\"NIF\" value=\""+this.stringToHTMLString(cliente.getNif())+"\" size=\"20\" readonly></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Nacimiento: </b></td><td><input type=\"date\" name=\"FechaNacimiento\" max=\"2020-12-31\" min=\"1950-12-31\" value=\""+sdf.format(cliente.getFechaNacimiento())+"\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Direcci&oacuten: </b></td><td><input type=\"text\" name=\"Calle\"value=\""+this.stringToHTMLString(cliente.getDomicilio().getDireccion())+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Provincia: </b></td><td><input type=\"text\" name=\"Provincia\" value=\""+this.stringToHTMLString(cliente.getDomicilio().getProvincia())+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>* Localidad: </b></td><td><input type=\"text\" name=\"Localidad\" value=\""+this.stringToHTMLString(cliente.getDomicilio().getLocalidad())+"\" size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("");
                out.println("			<tr>");
                out.println("				<td><b>* C&oacutedigo Postal: </b></td><td><input type=\"text\" name=\"CP\" value=\""+cliente.getDomicilio().getCpostal()+"\"  size=\"20\" required></td>");
                out.println("			</tr>");
                out.println("");
                out.println("			<tr>");
                out.println("				<td><b>Fijo: </b></td><td><input type=\"tel\" name=\"Fijo\"value=\""+this.stringToHTMLString(cliente.getFijo())+"\"  size=\"20\"></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>M&oacutevil: </b></td><td><input type=\"tel\" name=\"Movil\" value=\""+this.stringToHTMLString(cliente.getMovil())+"\" size=\"20\"></td>");
                out.println("			</tr>");
                out.println("			<tr>");
                out.println("				<td><b>Email: </b></td><td><input type=\"email\" name=\"Email\" value=\""+this.stringToHTMLString(cliente.getEmail())+"\" size=\"20\"></td>");
                out.println("			</tr>");
                out.println("		</table>");
                out.println("		<br>");
                out.println("		<p><font style=\"italic\">Los campos con asterisco (*) son <b>obligatorios</b>.</font></p>");
                out.println("<br>");
                out.println("<input type=\"hidden\" name=\"OldNIF\" value=\""+cliente.getNif()+"\">");
                out.println("<button class='boton' type=\"submit\" name=\"TipoConsulta\" value=\"Modificar\">Modificar Huesped</button>");
                out.println("<button class='boton' type=\"submit\" name=\"TipoConsulta\" value=\"Eliminar\">Eliminar Huesped</button>");
                out.println("		</form>");
                out.println("		<br>");
                out.println("		<a href=\"index.html\">Volver a Pagina Principal</a>");
                out.println("	</body>	");
                out.println("	</body>");
                out.println("</html>");
             
         }
    }
    //Este metodo genera una pagina dinamica de HTML que muestra las reservas asociadas a una fecha de entrada recibida anteriormente.
    //Recibe una lista de Reservas de las que mostrara los datos.
    public void generarConsultaReserva(ArrayList<Reserva> reservas, HttpServletResponse response) throws IOException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        response.setContentType("text/html;charset=UTF-8");
        int i=1;
             try(PrintWriter out = response.getWriter()){
                 out.println("<html>");
out.println("	<head>");
out.println("		<title>Mostrar Consulta</title>");
out.println("	    <meta charset=\"UTF-8\">");
out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\">");
out.println("");
out.println("	</head>");
out.println("	<body >");
out.println("		<h2><img src=\"Img/Lupa.png\" width=\"25\"> Resultado de la Consulta:</h2>");
for(Reserva r: reservas){
out.println("		<table>");
out.println("			<tr>");
out.println("				<td><h3>Reserva " +i+": </h3></td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Nombre: </b></td><td>"+this.stringToHTMLString(this.lClientes.ConsultaHuespedNIF(r.getNif()).getNombre())+"</td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Apellidos: </b></td><td>"+this.stringToHTMLString(this.lClientes.ConsultaHuespedNIF(r.getNif()).getApellidos())+"</td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>NIF: </b></td><td>"+this.stringToHTMLString(r.getNif())+"</td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Fecha de Entrada: </b></td><td>"+sdf.format(r.getFentrada())+"</td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Fecha de salida: </b></td><td>"+sdf.format(r.getFsalida())+"</td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Habitaci&oacuten:</b></td><td>"+r.getHabitacion()+"</td>");
out.println("");
out.println("			</tr>");
out.println("		</table>");
out.println("		</br>");
i++;
}
out.println("		<br>");
out.println("		<a href=\"index.html\">Volver a Pagina Principal</a>");
out.println("	</body>	");
out.println("");
out.println("</html>");
                 
             }
             }
    
    //El metodo genera una pagina dinamica de HTML que permite modificar o eliminar una reserva.
    public void generarModElimReserva(HttpServletResponse response, Reserva re) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try(PrintWriter out = response.getWriter()){
out.println("<html>");
out.println("	<head>");
out.println("		<title>Modificar o Eliminar Reserva</title>");
out.println("	    <meta charset=\"UTF-8\">");
out.println("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
out.println("        <link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\">");
out.println("");
out.println("	</head>");
out.println("<html>");
out.println("	<body >");
out.println("		<h2><img src=\"Img/ModReserva.png\" width=\"25\"> Resultado de la Reserva:</h2>");
out.println("<p>El resultado de la busqueda se muestra a continuaci&oacuten.<br>Si desea eliminar la ");
out.println("reserva pulse \"Eliminar Reserva\".<br>Si desea cambiar los datos, modifiquelos y pulse \"Guardar Cambios\".</p>");
out.println("		<form action=\"Servlet_Ap3\" method=\"post\">");
out.println("		<table>");
out.println("			<tr>");
out.println("				<td><b>NIF: </b></td><td><input type=\"text\" name=\"NIF\" value=\""+re.getNif()+"\" readonly></td>");
out.println("			</tr>");
out.println("			<tr>");
out.println("				<td><b>Fecha De Entrada: </b></td><td><input type=\"date\" name=\"FechaEntrada\" max=\"2020-12-31\" min=\"1950-12-31\" value=\""+sdf.format(re.getFentrada())+"\" required></td>");
out.println("</tr>");
out.println("<tr>");
out.println("				<td><b>Fecha De Salida: </b></td><td><input type=\"date\" name=\"FechaSalida\" max=\"2020-12-31\" min=\"1950-12-31\" value=\""+sdf.format(re.getFsalida())+"\" required></td>");
out.println("</tr>");
out.println("			<tr>");
out.println("				<td><b>Habitaci&oacuten: </b></td><td><input type=\"number\" name=\"Habitacion\" value=\""+re.getHabitacion()+"\" size=\"10\" min=\"100\" max=\"599\" required></td>");
out.println("			</tr>");            
out.println("");
out.println("			</tr>");
out.println("		</table>");
out.println("<button class='boton' type=\"submit\" name=\"TipoConsulta\" value=\"ModificarReserva\">Guardar Cambios</button>");
out.println("<button class='boton' type=\"submit\" name=\"TipoConsulta\" value=\"EliminarReserva\">Eliminar Reserva</button>");
out.println("        </form>");
out.println("		<br>");
out.println("		<a href=\"index.html\">Volver a Pagina Principal</a>");
out.println("	</body>	");
out.println("");
out.println("</html>");

        }
    }
    //El método transforma un String recibido para poder ser mostrado en HTML
    public  String stringToHTMLString(String string) {
    StringBuffer sb = new StringBuffer(string.length());
    // true if last char was blank
    boolean lastWasBlankChar = false;
    int len = string.length();
    char c;

    for (int i = 0; i < len; i++)
        {
        c = string.charAt(i);
        if (c == ' ') {
            // blank gets extra work,
            // this solves the problem you get if you replace all
            // blanks with &nbsp;, if you do that you loss 
            // word breaking
            if (lastWasBlankChar) {
                lastWasBlankChar = false;
                sb.append("&nbsp;");
                }
            else {
                lastWasBlankChar = true;
                sb.append(' ');
                }
            }
        else {
            lastWasBlankChar = false;
            //
            // HTML Special Chars
            if (c == '"')
                sb.append("&quot;");
            else if (c == '&')
                sb.append("&amp;");
            else if (c == '<')
                sb.append("&lt;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '\n')
                // Handle Newline
                sb.append("&lt;br/&gt;");
            else {
                int ci = 0xffff & c;
                if (ci < 160 )
                    // nothing special only 7 Bit
                    sb.append(c);
                else {
                    // Not 7 Bit use the unicode system
                    sb.append("&#");
                    sb.append(new Integer(ci).toString());
                    sb.append(';');
                    }
                }
            }
        }
    return sb.toString();
}
    //El metodo devuelve TRUE si todos los campos del formulario estan rellenos correctamente. FALSE en caso contrario.
    public boolean esBuenFormulario(HttpServletRequest request){
        Enumeration<String> parametros=request.getParameterNames();
        boolean noVacios=true;
        while(parametros.hasMoreElements()){
            String paramet=parametros.nextElement();
            if(paramet.equals("Movil")||paramet.equals("Fijo")||paramet.equals("Email")){
                
            }else{
                if(request.getParameter(paramet).isEmpty()){
                    noVacios=false;
                }
            }
        }
        return noVacios;
    }


}
