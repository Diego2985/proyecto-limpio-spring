package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;

public interface RepositorioListadoTurno {

    List<Turno> getListadoDeTurnos(Long userId);
}
