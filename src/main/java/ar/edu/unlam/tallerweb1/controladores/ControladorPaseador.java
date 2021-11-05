package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.converter.Coordenadas;
import ar.edu.unlam.tallerweb1.converter.DatosTiempo;
import ar.edu.unlam.tallerweb1.converter.Ubicacion;
import ar.edu.unlam.tallerweb1.excepciones.PaseadorConCantMaxDeMascotasException;
import ar.edu.unlam.tallerweb1.modelo.Paseador;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaseador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ControladorPaseador {
    private ServicioPaseador servicioPaseador;

    @Autowired
    public ControladorPaseador(ServicioPaseador servicioPaseador) {
        this.servicioPaseador = servicioPaseador;
    }

    @RequestMapping(path = "/ver-paseadores", method = RequestMethod.POST)
    public ModelAndView obtenerPaseadoresCercanosA500mts(@RequestParam Double latitud, @RequestParam Double longitud) {
        try {
            ModelMap model = new ModelMap();
            Integer distancia = 500;
            List<Paseador> paseadores = servicioPaseador.obtenerListaDePaseadoresCercanos(latitud, longitud, distancia);
            Ubicacion ubicacion = servicioPaseador.obtenerDireccionDeUbicacionActual(latitud, longitud);
            model.put("paseadores", paseadores);
            model.put("ubicacion", ubicacion);
            return new ModelAndView("paseador-mapa", model);
        } catch (Exception e) {
            return new ModelAndView("error");
        }
    }

    @RequestMapping("/paseador")
    public ModelAndView verPaginaDePaseador() {
        return new ModelAndView("paseador-inicio");
    }

    @RequestMapping(path = "/contratar-paseador", method = RequestMethod.POST)
    public ModelAndView contratarAlPaseador(@RequestParam Long idPaseador, @RequestParam Double latitud, @RequestParam Double longitud) {
        ModelMap model = new ModelMap();
        try {
            Paseador paseador = servicioPaseador.obtenerPaseador(idPaseador, true);
            Coordenadas coordenadasPaseador = new Coordenadas(paseador.getLatitud(), paseador.getLongitud());
            Coordenadas coordenadasUsuario = new Coordenadas(latitud, longitud);
            DatosTiempo distanciaYTiempo = servicioPaseador.obtenerDistanciaYTiempo(coordenadasUsuario, coordenadasPaseador);
            String obtenerImagen = servicioPaseador.obtenerImagenDeRutaDePaseadorAUsuario(coordenadasUsuario, coordenadasPaseador);
            model.put("idPaseador", idPaseador);
            model.put("paseador", paseador);
            model.put("imagen", obtenerImagen);
            model.put("distanciaYTiempo", distanciaYTiempo);
            return new ModelAndView("paseador-exitoso", model);
        } catch (PaseadorConCantMaxDeMascotasException e) {
            return new ModelAndView("paseador-no-disponible");
        } catch (UnsupportedEncodingException e) {
            model.put("mensaje", "No se pudo obtener la imagen de la ruta");
            return new ModelAndView("paseador-exitoso", model);
        } catch (IOException e) {
            model.put("mensaje", "No se pudo obtener el tiempo de llegada ni la distancia");
            return new ModelAndView("paseador-exitoso", model);
        }
    }
}
