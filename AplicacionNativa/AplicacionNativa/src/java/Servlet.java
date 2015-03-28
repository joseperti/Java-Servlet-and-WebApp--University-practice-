/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Mensajes.MensajeErrorNameSurname;
import Mensajes.MensajeErrorDate;
import Mensajes.MensajeErrorDateNIF;
import Mensajes.MensajeRespuesta;
import Mensajes.MensajeErrorNIF;
import Listados.ListaReservas;
import Listados.ListaClientes;
import ClasesBase.Huesped;
import ClasesBase.Domicilio;
import ClasesBase.Reserva;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.thoughtworks.xstream.XStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author francisco
 */
@WebServlet(urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
private ListaClientes listaCliente = new ListaClientes();
private ListaReservas listaReserva = new ListaReservas(); 
private XStream mistream = new XStream();

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String Nombre=request.getParameter("tipoConsulta");
            Huesped cli; 
            response.setContentType("application/xml;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
            out.println ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        if (Nombre.equals("ConsultaHuespedNIF")){
            String nif = request.getParameter("NIF");
            if(listaCliente.ExisteNIF(nif)){
                cli= listaCliente.ConsultaHuespedNIF(nif);
                MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                mensajeRespuesta.setTexto(mistream.toXML(cli));
                out.println(mistream.toXML(mensajeRespuesta));
            }else{
                MensajeErrorNIF mensajeErrorNif = new MensajeErrorNIF();
                mensajeErrorNif.setNIF(nif);
                out.println(mistream.toXML(mensajeErrorNif));
            }
        }else if(Nombre.equals("ConsultaHuespedNombre")){
            String name= request.getParameter("Nombre");
            String ape= request.getParameter("Apellidos");
            if(listaCliente.ExisteNombreApellidos(name, ape)){
                cli= listaCliente.ConsultaHuespedNombre(name,ape);
                MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                mensajeRespuesta.setTexto(mistream.toXML(cli));
                out.println(mistream.toXML(mensajeRespuesta));
            }else{
                MensajeErrorNameSurname mensajeErrorNameSurname = new MensajeErrorNameSurname();
                mensajeErrorNameSurname.setNombre(name);
                mensajeErrorNameSurname.setApellidos(ape);
                out.println(mistream.toXML(mensajeErrorNameSurname));                
            }
            }else if(Nombre.equals("ConsultaReservas")){
                SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");
                String fecha =request.getParameter("Fecha");
                Date fechaD = formatofecha.parse(fecha);
                ArrayList<Reserva> lista = listaReserva.ConsultaHuespedFechaEntrada(fechaD);
                if (!lista.isEmpty()){
                    MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                    mensajeRespuesta.setTexto(mistream.toXML(lista));
                    out.println(mistream.toXML(mensajeRespuesta));
                }else{
                    MensajeErrorDate mensajeErrorDate = new MensajeErrorDate();
                    mensajeErrorDate.setFecha(fechaD);
                    out.println(mistream.toXML(mensajeErrorDate));
                }
                
            }
            } catch (ParseException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }finally{out.close();}
        
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
       response.setContentType("application/xml;charset=UTF-8");
       PrintWriter out = response.getWriter();
       String Nombre=request.getParameter("tipoConsulta");
       if (Nombre.equals("nuevoHuesped")){
            String nombre = request.getParameter("Nombre");
            String apellidos = request.getParameter("Apellidos");
            String nif = request.getParameter("Nif");
            String fechaNacimiento = request.getParameter("FechaNacimiento");
            String direccion = request.getParameter("Direccion");
            String localidad = request.getParameter("Localidad");
            String codigoPostal = request.getParameter("CodigoPostal");
            String provincia = request.getParameter("Provincia");
            String movil = request.getParameter("Movil");
            String fijo = request.getParameter("Fijo");
            String email = request.getParameter("Email");
            Domicilio tDomicilio = new Domicilio(direccion,localidad,
                    provincia,codigoPostal);
             SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
             Date fecha;
             try {
                 fecha = formatoDelTexto.parse(fechaNacimiento);
            if (!listaCliente.ExisteNIF(nif)){
                Huesped nuevo_cliente = new Huesped(nombre,apellidos,nif,fecha,
                tDomicilio,fijo,movil,email);
                listaCliente.addHuesped(nuevo_cliente); 
                MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                mensajeRespuesta.setTexto("Cliente creado con éxito");
                out.println(mistream.toXML(mensajeRespuesta));
            }else{
                MensajeErrorNIF mensajeErrorNif = new MensajeErrorNIF();
                mensajeErrorNif.setNIF(nif);
                out.println(mistream.toXML(mensajeErrorNif));
            }
            } catch (ParseException ex){
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else if (Nombre.equals("modificarHuesped")){
            Huesped cli= listaCliente.ConsultaHuespedNIF(request.getParameter("NifAnterior"));
            listaCliente.removeHuesped(cli);
            String nombre = request.getParameter("Nombre");
            String apellidos = request.getParameter("Apellidos");
            String nif = request.getParameter("Nif");
            String fechaNacimiento = request.getParameter("FechaNacimiento");
            String direccion = request.getParameter("Direccion");
            String localidad = request.getParameter("Localidad");
            String codigoPostal = request.getParameter("CodigoPostal");
            String provincia = request.getParameter("Provincia");
            String movil = request.getParameter("Movil");
            String fijo = request.getParameter("Fijo");
            String email = request.getParameter("Email");
            Domicilio tDomicilio = new Domicilio(direccion,localidad,
               provincia,codigoPostal);
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha;
            try {
                fecha = formatoDelTexto.parse(fechaNacimiento);
                    if (!listaCliente.ExisteNIF(nif)){
                        Huesped nuevo_cliente = new Huesped(nombre,apellidos,nif,fecha,
                        tDomicilio,fijo,movil,email);
                        listaCliente.addHuesped(nuevo_cliente);
                        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                        mensajeRespuesta.setTexto("Cliente modificado con éxito");
                        out.println(mistream.toXML(mensajeRespuesta));
                    }else{
                        MensajeErrorNIF mensajeErrorNif = new MensajeErrorNIF();
                        mensajeErrorNif.setNIF(nif);
                        out.println(mistream.toXML(mensajeErrorNif));
                    }
            }catch (ParseException ex){
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else if(Nombre.equals("nuevaReserva")){
           String nif = request.getParameter("nif");
           if (listaCliente.ExisteNIF(nif)){
                Huesped cli= listaCliente.ConsultaHuespedNIF(request.getParameter("nif"));
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
                Date fechae;
                Date fechas;
               try {
                   fechae = formatoDelTexto.parse(request.getParameter("fechae"));
                   fechas = formatoDelTexto.parse(request.getParameter("fechas"));
                   Reserva res = new Reserva(cli,fechae,fechas);
                   listaReserva.addReserva(res);
                   MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                   mensajeRespuesta.setTexto("Reserva creada con exito");
                   out.println(mistream.toXML(mensajeRespuesta));
               } catch (ParseException ex) {
                   Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
               }
           }else{
               MensajeErrorNIF mensajeErrorNif = new MensajeErrorNIF();
               mensajeErrorNif.setNIF(nif);
               out.println(mistream.toXML(mensajeErrorNif));

           }
           
       }else if(Nombre.equals("modificarReserva")){
            String nif = request.getParameter("nif");
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            Date fechae;
           try {
                fechae = formatoDelTexto.parse(request.getParameter("fechae"));
                if (listaReserva.ExisteReserva(fechae, nif)){
                    Reserva reserva = listaReserva.ConsultaReservaFechaNif(fechae, nif);
                    listaReserva.removeReserva(reserva);
                    Date fechaeN;
                    fechaeN = formatoDelTexto.parse(request.getParameter("fechaeN"));
                    Date fechas;
                    fechas = formatoDelTexto.parse(request.getParameter("fechasN"));
                    nif = request.getParameter("nifN");
                    if (listaCliente.ExisteNIF(nif)){
                        Huesped cliente = listaCliente.ConsultaHuespedNIF(nif);
                        reserva = new Reserva (cliente,fechaeN,fechas);
                        listaReserva.addReserva(reserva);
                        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
                        mensajeRespuesta.setTexto("Reserva modificada con exito");
                        out.println(mistream.toXML(mensajeRespuesta));
                    }else{
                        MensajeErrorNIF mensajeErrorNif = new MensajeErrorNIF();
                        mensajeErrorNif.setNIF(nif);
                        out.println(mistream.toXML(mensajeErrorNif));
                    }
                }else {
                        MensajeErrorDateNIF mensajeErrorDateNif = new MensajeErrorDateNIF();
                        mensajeErrorDateNif.setNif(nif);
                        mensajeErrorDateNif.setFecha(fechae);
                        out.println(mistream.toXML(mensajeErrorDateNif));
                }
           } catch (ParseException ex) {
               Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
           }
       }else if(Nombre.equals("EliminarHuesped")){
           listaCliente.removeHuesped(listaCliente.ConsultaHuespedNIF(request.getParameter("nif")));
           MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
           mensajeRespuesta.setTexto("Huesped eliminado con exito");
           out.println(mistream.toXML(mensajeRespuesta));
       }else if(Nombre.equals("EliminarReserva")){
           Date fecha;
           SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
           try {
               fecha = formatoDelTexto.parse(request.getParameter("fecha"));
               listaReserva.removeReserva(listaReserva.ConsultaReservaFechaNif(fecha, request.getParameter("nif")));
               MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
               mensajeRespuesta.setTexto("Reserva eliminada con exito");
               out.println(mistream.toXML(mensajeRespuesta));
           } catch (ParseException ex) {
               Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
           }
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

}
