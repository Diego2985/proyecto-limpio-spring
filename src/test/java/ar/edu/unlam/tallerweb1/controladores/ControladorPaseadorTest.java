package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Paseador;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioPaseador;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorPaseadorTest {

    private Double latitud = -34.588902;
    private Double longitud = -58.409851;
    private ModelAndView mav;
    private Usuario usuario=new Usuario();
    private ServicioPaseador servicioPaseador=mock(ServicioPaseador.class);
    private ControladorPaseador controladorPaseador=new ControladorPaseador(servicioPaseador);

    @Test
    public void verificarQueSeRecibeLaVistaDondeSeCaptaLaUbicacion(){
        mav=whenElUsuarioQuiereElegirLaOpcionParaPasearAlPerro();
        thenTieneQueVerLaVistaInicialDondeSeCaptaLaUbicacion(mav);
    }

    private ModelAndView whenElUsuarioQuiereElegirLaOpcionParaPasearAlPerro() {
        return controladorPaseador.verPaginaDePaseador();
    }

    private void thenTieneQueVerLaVistaInicialDondeSeCaptaLaUbicacion(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("paseador-inicio");
    }

    @Test
    public void recibirLasCoordenadasDeUbicacionDelUsuario(){
        givenUnUsuarioYSuUbicacion();
        mav=whenEnviaLasCoordenadas();
        thenDeberiaRecibirlasEnElController(mav);
    }

    private void givenUnUsuarioYSuUbicacion() {
        usuario.setEmail("usuario@correo.com");
        usuario.setPassword("12345");
    }

    private ModelAndView whenEnviaLasCoordenadas() {
        return controladorPaseador.recibirLasCoordenadasDeUbicacion(latitud, longitud);
    }

    private void thenDeberiaRecibirlasEnElController(ModelAndView mav) {
        assertThat(mav.getViewName()).isEqualTo("paseador-mapa");
        assertThat(mav.getModel().get("latitud")).isEqualTo(latitud);
        assertThat(mav.getModel().get("longitud")).isEqualTo(longitud);
    }

    @Test
    public void obtenerIdDePaseador(){
        Long id=givenDadoUnPaseador();
        mav=whenContratoAlPaseador(id);
        thenDeboObtenerSuId(mav, id);
    }

    private Long givenDadoUnPaseador() {
        return 1L;
    }

    private ModelAndView whenContratoAlPaseador(Long id) {
        return controladorPaseador.contratarAlPaseador(id);
    }

    private void thenDeboObtenerSuId(ModelAndView mav, Long id) {
        assertThat(mav.getViewName()).isEqualTo("paseador-exitoso");
        assertThat(mav.getModel().get("idPaseador")).isEqualTo(id);
    }

    @Test
    public void obtenerPaseador(){
        Paseador paseador=givenUnIdYUnPaseador();
        mav=whenContratoAlPaseador(paseador.getId());
        thenDeboObtenerUnPaseador(mav, paseador);
    }

    private Paseador givenUnIdYUnPaseador() {
        Long id=1L;
        Paseador paseador=new Paseador();
        paseador.setId(id);
        paseador.setNombre("Matias");
        paseador.setEstrellas(4);
        paseador.setLatitud(latitud);
        paseador.setLongitud(longitud);

        when(servicioPaseador.obtenerPaseador(id)).thenReturn(paseador);

        return paseador;
    }

    private void thenDeboObtenerUnPaseador(ModelAndView mav, Paseador paseador) {
        assertThat(mav.getModel().get("paseador")).isEqualTo(paseador);
    }
}
