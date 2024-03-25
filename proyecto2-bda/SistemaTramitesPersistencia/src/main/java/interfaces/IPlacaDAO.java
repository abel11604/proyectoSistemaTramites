/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import bda.itson.entidadesJPA.Placa;

/**
 *
 * @author ID145
 */
public interface IPlacaDAO {
    /**
     * Método que agrega una placa
     * 
     * @param placa Placa a agregar
     * @return Placa agregada
     */
    public Placa agregar(Placa placa);

    /**
     * Método para consultar una placa por su id.
     * 
     * @param idPlaca Identificador de la placa a consultar.
     * @return Placa consultada.
     */
    public Placa consultar(Long idPlaca);

}
