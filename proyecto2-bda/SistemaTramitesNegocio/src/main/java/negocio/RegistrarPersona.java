package negocio;

import bda.itson.entidadesJPA.Persona;
import dao.ConexionJPA;
import dao.PersonaDAO;
import dtos.PersonaDTO;
import excepciones.PersistenciaException;
import interfaces.IConexion;
import interfaces.IPersona;
import interfaces.IregistrarPersona;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import otros.GeneradorNombres;

/**
 *
 * @author abelc
 */
public class RegistrarPersona implements IregistrarPersona {
/** Unidad de persistencia para la conexión JPA. */
    String unidadPersistencia = "bda.itson_SistemaTramitesPersistencia_jar_1.0-SNAPSHOTPU";

    /** Objeto para la conexión JPA. */
    IConexion conexionJPA;

    /** Objeto para el acceso a datos de Persona. */
    IPersona Personadao;

    /** Objeto para la generación de datos aleatorios. */
    GeneradorNombres g;
 /**
     * Constructor de la clase.
     * Inicializa los objetos de conexión JPA, acceso a datos de Persona y generación de datos aleatorios.
     */
    public RegistrarPersona() {
        this.conexionJPA = new ConexionJPA(unidadPersistencia);
        this.Personadao = new PersonaDAO(conexionJPA);
        this.g = new GeneradorNombres();
    }
 /**
     * Método para realizar un registro masivo de personas en el sistema.
     * @param personas Lista de objetos PersonaDTO a registrar.
     * @return True si el registro fue exitoso, False si ocurrió algún error.
     */
    @Override
    public boolean registroMasivo(List<PersonaDTO> personas) {
        List<Persona> personasEntidad = new ArrayList<>();
        for (PersonaDTO persona : personas) {
            Persona pEntidad = new Persona();;
            pEntidad.setNombres(persona.getNombres());
            pEntidad.setApellidoPaterno(persona.getApellidoPaterno());
            pEntidad.setApellidoMaterno(persona.getApellidoMaterno());
            pEntidad.setFechaNacimiento(persona.getFechaNacimiento());
            pEntidad.setRFC(persona.getRFC());
            pEntidad.setTelefono(persona.getTelefono());
            pEntidad.setEsDiscapacitado(persona.isEsDiscapacitado());
            personasEntidad.add(pEntidad);
        }
        try {
            Personadao.insercionMasivaPersonas(personasEntidad);
            return true;
        } catch (PersistenciaException ex) {
            Logger.getLogger(RegistrarPersona.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
   
    /**
     * Método para generar una lista de personas con datos aleatorios.
     * @param cantidad Cantidad de personas a generar.
     * @return Lista de objetos PersonaDTO generados.
     */
    @Override
    public List<PersonaDTO> generarlista(int cantidad) {
        List<PersonaDTO> personas = new ArrayList<>();;
        for (int i = 0; i < cantidad; i++) {
            PersonaDTO p = new PersonaDTO();
            p.setNombres(g.generarNombre());
            p.setApellidoPaterno(g.generarApellidoPaterno());
            p.setApellidoMaterno(g.generarApellidoMaterno());
            p.setFechaNacimiento(g.generarFechaAleatoria(1960, 2004));
            p.setRFC(generarRFCValidado(p));
            p.setTelefono(g.generarNumeroTelefono());
            p.setEsDiscapacitado(g.generarBooleanoAleatorio());

            personas.add(p);
        }
        return personas;
    }
/**
     * Método para generar un RFC válido para una persona.
     * @param dto Objeto PersonaDTO para el cual se generará el RFC.
     * @return RFC generado.
     */
    private String generarRFCValidado(PersonaDTO dto) {
        String rfc = generarRFC(dto);
        while (Personadao.obtenerPersonaRFC(rfc) != null) {
            rfc = generarRFC(dto);
        }
        return rfc;
    }
 /**
     * Método para generar un RFC para una persona.
     * @param dto Objeto PersonaDTO para el cual se generará el RFC.
     * @return RFC generado.
     */
    private String generarRFC(PersonaDTO dto) {
        String nombre = dto.getNombres().toUpperCase();
        String apellidoPaterno = dto.getApellidoPaterno().toUpperCase();
        String apellidoMaterno = dto.getApellidoMaterno().toUpperCase();
        Calendar fechaNacimiento = dto.getFechaNacimiento();
        String primeraLetraApellidoPaterno = apellidoPaterno.substring(0, 1);
        String primeraVocalApellidoPaterno = g.obtenerPrimeraVocal(apellidoPaterno.substring(1));
        String primeraLetraApellidoMaterno = apellidoMaterno.substring(0, 1);
        int year = fechaNacimiento.get(Calendar.YEAR) % 100;
        String anio = String.format("%02d", year);
        int mes = fechaNacimiento.get(Calendar.MONTH) + 1;
        String mesString = String.format("%02d", mes);
        int dia = fechaNacimiento.get(Calendar.DAY_OF_MONTH);
        String diaString = String.format("%02d", dia);
        //esto se lo agregue para que pueda haber algo que pueda diferencias las rfc en caso 
        //de que sean los mismos datos en diferentes clientes
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder codigoAleatorio = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            codigoAleatorio.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        String rfc = primeraLetraApellidoPaterno + primeraVocalApellidoPaterno + primeraLetraApellidoMaterno + anio + mesString + diaString + codigoAleatorio.toString();

        return rfc;
    }
}
