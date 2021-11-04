package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Paseadores;
import ar.edu.unlam.tallerweb1.excepciones.PaseadorConCantMaxDeMascotasException;
import ar.edu.unlam.tallerweb1.modelo.Paseador;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaseador;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorPaseadorTest {

    private Double latitud = -34.588902;
    private Double longitud = -58.409851;
    private Integer distancia = 500;
    private ModelAndView mav;
    private Usuario usuario = new Usuario();
    private ServicioPaseador servicioPaseador = mock(ServicioPaseador.class);
    private ControladorPaseador controladorPaseador = new ControladorPaseador(servicioPaseador);

    @Test
    public void verificarQueSeRecibeLaVistaDondeSeCaptaLaUbicacion() {
        mav = whenElUsuarioQuiereElegirLaOpcionParaPasearAlPerro();
        thenTieneQueVerLaVistaInicialDondeSeCaptaLaUbicacion(mav);
    }

    private ModelAndView whenElUsuarioQuiereElegirLaOpcionParaPasearAlPerro() {
        return controladorPaseador.verPaginaDePaseador();
    }

    private void thenTieneQueVerLaVistaInicialDondeSeCaptaLaUbicacion(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("paseador-inicio");
    }

    @Test
    public void recibirLasCoordenadasDeUbicacionDelUsuario() {
        givenUnUsuarioYSuUbicacion();
        mav = whenEnviaLasCoordenadas();
        thenDeberiaRecibirlasEnElController(mav);
    }

    private void givenUnUsuarioYSuUbicacion() {
        usuario.setEmail("usuario@correo.com");
        usuario.setPassword("12345");
    }

    private ModelAndView whenEnviaLasCoordenadas() {
        return controladorPaseador.obtenerPaseadoresCercanosA500mts(latitud, longitud);
    }

    private void thenDeberiaRecibirlasEnElController(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("paseador-mapa");
        assertThat(mav.getModel().get("latitud")).isEqualTo(latitud);
        assertThat(mav.getModel().get("longitud")).isEqualTo(longitud);
    }

    @Test
    public void obtenerIdDePaseador() throws IOException {
        Long id = givenDadoUnPaseador();
        mav = whenContratoAlPaseador(id);
        thenDeboObtenerSuId(mav, id);
    }

    private Long givenDadoUnPaseador() {
        Paseador paseador=crearPaseador(1L);
        paseador.setCantidadMaxima(10);
        paseador.setCantidadActual(5);

        when(servicioPaseador.obtenerPaseador(paseador.getId())).thenReturn(paseador);
        return 1L;
    }

    private ModelAndView whenContratoAlPaseador(Long id) throws IOException {
        return controladorPaseador.contratarAlPaseador(id, latitud, longitud);
    }

    private void thenDeboObtenerSuId(ModelAndView mav, Long id) {
        assertThat(mav.getViewName()).isEqualTo("paseador-exitoso");
        assertThat(mav.getModel().get("idPaseador")).isEqualTo(id);
    }

    @Test
    public void obtenerPaseador() throws IOException {
        Paseador paseador = givenUnIdYUnPaseador();
        mav = whenContratoAlPaseador(paseador.getId());
        thenDeboObtenerUnPaseador(mav, paseador);
    }

    private Paseador givenUnIdYUnPaseador() {
        Long id = 1L;
        Paseador paseador=crearPaseador(id);
        paseador.setCantidadActual(5);
        paseador.setCantidadMaxima(10);

        when(servicioPaseador.obtenerPaseador(id)).thenReturn(paseador);

        return paseador;
    }

    private Paseador crearPaseador(Long id) {
        Paseador paseador = new Paseador();
        paseador.setId(id);
        paseador.setNombre("Matias");
        paseador.setEstrellas(4);
        paseador.setLatitud(latitud);
        paseador.setLongitud(longitud);

        return paseador;
    }


    private void thenDeboObtenerUnPaseador(ModelAndView mav, Paseador paseador) {
        assertThat(mav.getModel().get("paseador")).isEqualTo(paseador);
    }

    @Test
    public void obtenerPaseadoresCercanos() {
        List<Paseador> paseadoresEsperados = givenUnaListaDePaseadores();
        mav = whenSolicitoLosPaseadores();
        thenDeboObtenerLosPaseadoresCercanos(paseadoresEsperados, mav);
    }

    private List<Paseador> givenUnaListaDePaseadores() {
        List<Paseador> paseadores = Paseadores.crearPaseadores();
        paseadores.remove(2);

        when(servicioPaseador.obtenerListaDePaseadoresCercanos(latitud, longitud, distancia)).thenReturn(paseadores);

        return paseadores;
    }

    private ModelAndView whenSolicitoLosPaseadores() {
        return controladorPaseador.obtenerPaseadoresCercanosA500mts(latitud, longitud);
    }

    private void thenDeboObtenerLosPaseadoresCercanos(List<Paseador> paseadoresEsperados, ModelAndView mav) {
        List<Paseador> obtenidos = (List<Paseador>) mav.getModel().get("paseadores");
        assertThat(obtenidos).hasSize(2);
        assertThat(obtenidos).isEqualTo(paseadoresEsperados);
    }

    @Test
    public void siElPaseadorLlegoALaCantidadMaximaNoSeLoDebeContratar() throws PaseadorConCantMaxDeMascotasException {
        Paseador paseador=givenUnPaseadorConCantidadesMaxYActual();
        mav=whenContratoAUnPaseadorQueYaTieneLaCantMaxDeMascotas(paseador);
        thenNoPodriaContratarlo(mav);
    }

    private Paseador givenUnPaseadorConCantidadesMaxYActual() throws PaseadorConCantMaxDeMascotasException {
        Paseador paseador=crearPaseador(1L);
        paseador.setCantidadActual(10);
        paseador.setCantidadMaxima(10);

        when(servicioPaseador.obtenerPaseador(1L, true)).thenThrow(PaseadorConCantMaxDeMascotasException.class);

        return paseador;
    }

    private ModelAndView whenContratoAUnPaseadorQueYaTieneLaCantMaxDeMascotas(Paseador paseador) {
        return controladorPaseador.contratarAlPaseador(paseador.getId(), latitud, longitud);
    }

    private void thenNoPodriaContratarlo(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("paseador-no-disponible");
    }
}
