/*
 * Departamento de Informática y Automática
 * Universidad de Salamanca - Programación III
 * Copyright (C) J.R. García-Bermejo Giner
 */
package controller;

import data.Model;

import java.util.List;
// VERSION_03

/**
 *
 * @author coti
 */
public class Controller {

  private final Model m = new Model();

  public String[][] obtenerListado() {
    return m.listaComoTablaDeCadenas();
  }

  public String importar() {
    return m.importarListaDeArchivoDelimitado(m.getFileDatosDelimitados(), "\t");
  }

  public String exportar() {
    return m.exportarListAarchivoDelimitado(m.getFileDatosDelimitados(), "\t");
  }

  public void ordenarPor(String opcion) {
    m.ordenarPor(opcion);
  }

  public int getNumeroDeDiscosDuros() {
    return m.getLista().size();
  }

  public String guardar() {
    return m.guardar(m.getFileDatosBinarios());
  }

  public String leer() {
    return m.leer(m.getFileDatosBinarios());
  }

  public boolean agregadoDiscoApartirDe(List<String> datos) {

    return m.hemosAgregadoDiscoApartirDe(datos);
  }

  public List<String> obtenerListaDeDatosParaElDiscoQueTieneEste(String codigo) {
    return m.listaDeCadenasDelDiscoConCodigo(codigo);
  }

  public boolean eliminadoDiscoConCodigo(String posibleCodigo) {
    return m.hemosEliminadoElDiscoDeCuyoCodigoEs(posibleCodigo);
  }

  public String porQueSeDescarta(List<String> datos) {
    return m.porQueSeDescarta(datos);
  }

  public List<String> getNombresDeCampos() {
    return m.getNombresDeCampos();
  }
} // Fin de Controller
