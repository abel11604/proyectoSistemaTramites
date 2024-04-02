/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import bda.itson.entidadesJPA.Persona;
import java.util.List;

import bda.itson.entidadesJPA.Vehiculo;
import excepciones.PersistenciaException;

/**
 * Interfaz de la clase VehiculoDAO
 * 
 * @author ID145
 */
public interface IVehiculoDAO {

    /**
     * Método que agrega un Vehiculo
     * 
     * @param vehiculo
     * @return
     */
    public Vehiculo agregar(Vehiculo vehiculo);

    /**
     * Método para consultar un Vehiculo por su id.
     * 
     * @param id
     * @return
     */
    public Vehiculo consultar(Long id);

    /**
     * Método para realizar una inserción masiva de Vehiculos.
     * @param vehiculos
     * @return
     * @throws PersistenciaException
     */
    public List<Vehiculo> insercionMasivaVehiculo(List<Vehiculo> vehiculos) throws PersistenciaException;
    
    public List<Vehiculo> consultarVehiculosPersona(Persona persona) throws PersistenciaException;
    
    Vehiculo consultarNumeroSeria(String numeroSerie) throws PersistenciaException;
}
