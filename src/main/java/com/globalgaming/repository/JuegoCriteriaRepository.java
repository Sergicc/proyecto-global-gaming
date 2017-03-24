package com.globalgaming.repository;

/**
 * Created by usu26 on 10/02/2017.
 */

import com.globalgaming.domain.Juego;
import org.springframework.stereotype.Repository;


//import com.codegoons.diesli.domain.MottoDefinition;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



    @Repository
    public class JuegoCriteriaRepository {
        @PersistenceContext
        EntityManager entityManager;

        protected Session currentSession() {
            return entityManager.unwrap(Session.class);
        }

        public List<Juego> filterJuegoByCriteria(Map<String, Object> parameters) {

            Criteria juegoCriteria = currentSession().createCriteria(Juego.class);
            juegoCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

          //  Criteria idiomaCriteria = juegoCriteria.createCriteria("idiomas");

            if(parameters.get("titulo")!= null) {
                String titulo = (String) parameters.get("titulo");
                juegoCriteria.add(Restrictions.ilike("titulo", titulo, MatchMode.ANYWHERE));
            }

            if(parameters.get("descripcion")!= null) {
                String descripcion = (String) parameters.get("descripcion");
                juegoCriteria.add(Restrictions.ilike("descripcion", descripcion, MatchMode.ANYWHERE));
            }

            if(parameters.get("desarrollador")!= null) {
                String desarrollador = (String) parameters.get("desarrollador");
                juegoCriteria.add(Restrictions.ilike("desarrollador", desarrollador, MatchMode.ANYWHERE));
            }

            if(parameters.get("genero")!= null) {
                String genero = (String) parameters.get("genero");
                juegoCriteria.add(Restrictions.ilike("genero", genero, MatchMode.ANYWHERE));
            }

            if(parameters.get("edadRecomendada")!= null) {
                String edadRecomendada = (String) parameters.get("edadRecomendada");
                juegoCriteria.add(Restrictions.eq("edadRecomendada", Integer.parseInt(edadRecomendada)));
            }

            if(parameters.get("idioma")!= null) {
                String idioma = (String) parameters.get("idioma");
                juegoCriteria.add(Restrictions.ilike("idioma", idioma, MatchMode.ANYWHERE));
            }

            // filtro por Capacidad de jugadores
            if ((parameters.get("minCapacidadJugadores") != null && parameters.get("minCapacidadJugadores") instanceof Integer)
                && (parameters.get("maxCapacidadJugadores") != null && parameters.get("maxCapacidadJugadores") instanceof Integer)
                ) {

                filterByCapacidadJugadoresBetween(parameters, juegoCriteria);
            }

            if (parameters.get("maxCapacidadJugadores") != null && parameters.get("minCapacidadJugadores") == null && parameters.get("maxCapacidadJugadores") instanceof Integer) {

                filterByMaxCapacidadJugadores(parameters, juegoCriteria);
            }
            if (parameters.get("minCapacidadJugadores") != null && parameters.get("maxCapacidadJugadores") == null && parameters.get("minCapacidadJugadores") instanceof Integer) {

                filterByMinCapacidadJugadores(parameters, juegoCriteria);
            }
            // filtro por Valoracion Web
            if ((parameters.get("minValoracionWeb") != null && parameters.get("minValoracionWeb") instanceof Double)
                && (parameters.get("maxValoracionWeb") != null && parameters.get("maxValoracionWeb") instanceof Double)
                ) {

                filterByValoracionWebBetween(parameters, juegoCriteria);
            }

            if (parameters.get("maxValoracionWeb") != null && parameters.get("minValoracionWeb") == null && parameters.get("maxValoracionWeb") instanceof Double) {

                filterByMaxValoracionWeb(parameters, juegoCriteria);
            }
            if (parameters.get("minValoracionWeb") != null && parameters.get("maxValoracionWeb") == null && parameters.get("minValoracionWeb") instanceof Double) {

                filterByMinValoracionWeb(parameters, juegoCriteria);
            }

            // filtro por Valoracion Users
            if ((parameters.get("minValoracionUsers") != null && parameters.get("minValoracionUsers") instanceof Double)
                && (parameters.get("maxValoracionUsers") != null && parameters.get("maxValoracionUsers") instanceof Double)
                ) {

                filterByValoracionUsersBetween(parameters, juegoCriteria);
            }

            if (parameters.get("maxValoracionUsers") != null && parameters.get("minValoracionUsers") == null && parameters.get("maxValoracionUsers") instanceof Double) {

                filterByMaxValoracionUsers(parameters, juegoCriteria);
            }
            if (parameters.get("minValoracionUsers") != null && parameters.get("maxValoracionUsers") == null && parameters.get("minValoracionUsers") instanceof Double) {

                filterByMinValoracionUsers(parameters, juegoCriteria);
            }

            List<Juego> results = juegoCriteria.list();

            return results;
        }


        private void filterByCapacidadJugadoresBetween(Map<String, Object> parameters, Criteria juegoCriteria) {
            Integer minCapacidadJugadores = (Integer) parameters.get("minCapacidadJugadores");
            Integer maxCapacidadJugadores = (Integer) parameters.get("maxCapacidadJugadores");

            juegoCriteria.add(Restrictions.between("capacidadJugadores",minCapacidadJugadores,maxCapacidadJugadores));
        }


        private void filterByMaxCapacidadJugadores(Map<String, Object> parameters, Criteria juegoCriteria) {
            Integer maxCapacidadJugadores = (Integer) parameters.get("maxCapacidadJugadores");

            juegoCriteria.add(Restrictions.le("capacidadJugadores", maxCapacidadJugadores));
        }
        private void filterByMinCapacidadJugadores(Map<String, Object> parameters, Criteria juegoCriteria) {
            Integer minCapacidadJugadores = (Integer) parameters.get("minCapacidadJugadores");

            juegoCriteria.add(Restrictions.ge("capacidadJugadores", minCapacidadJugadores));
        }


        private void filterByValoracionWebBetween(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double minValoracionWeb = (Double) parameters.get("minValoracionWeb");
            Double maxValoracionWeb = (Double) parameters.get("maxValoracionWeb");

            juegoCriteria.add(Restrictions.between("valoracionWeb",minValoracionWeb,maxValoracionWeb));
        }
        private void filterByMinValoracionWeb(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double maxValoracionWeb = (Double) parameters.get("maxValoracionWeb");

            juegoCriteria.add(Restrictions.le("valoracionWeb", maxValoracionWeb));
        }
        private void filterByMaxValoracionWeb(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double minValoracionWeb = (Double) parameters.get("minValoracionWeb");

            juegoCriteria.add(Restrictions.ge("valoracionWeb", minValoracionWeb));
        }


        private void filterByValoracionUsersBetween(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double minValoracionUsers = (Double) parameters.get("minValoracionUsers");
            Double maxValoracionUsers = (Double) parameters.get("maxValoracionUsers");

            juegoCriteria.add(Restrictions.between("valoracionUsers",minValoracionUsers,maxValoracionUsers));
        }
        private void filterByMinValoracionUsers(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double minValoracionUsers = (Double) parameters.get("minValoracionUsers");

            juegoCriteria.add(Restrictions.ge("valoracionUsers", minValoracionUsers));
        }
        private void filterByMaxValoracionUsers(Map<String, Object> parameters, Criteria juegoCriteria) {
            Double maxValoracionUsers = (Double) parameters.get("maxValoracionUsers");

            juegoCriteria.add(Restrictions.le("valoracionUsers", maxValoracionUsers));
        }




















            /*
            Criteria mottoCriteria = filterJuegoByCriteria.createCriteria("motto");

            filterBySearchBy(parameters, mottoCriteria);

            filterByCategoria(parameters, filterJuegoByCriteria);

            filterByMateria(parameters, filterJuegoByCriteria);

            filterByRegion(parameters,filterJuegoByCriteria);

            filterByRegistro(parameters,filterJuegoByCriteria);

            List<MottoDefinition>  results = mottoCriteria.list();

            return results;
        }




        private void filterBySearchBy(Map<String, Object> parameters, Criteria mottoCriteria) {


            String searchTerm = (String) parameters.get("searchTerm");


            //TODO: Aquí es dónde se filtra el tipo de búsqueda ( Empieza por, acaba en, etc...) habría que añadir u case más para el default o poner en el front a default el 1 y que quite el restriction
            if (parameters.containsKey("searchBy")) {
                Integer searchByParam = (Integer) parameters.get("searchBy");

                switch (searchByParam) {
                    case 1:

                        break;
                    case 2:
                        mottoCriteria.add(Restrictions.ilike("nombre", searchTerm, MatchMode.EXACT));
                        break;
                    case 3:
                        mottoCriteria.add(Restrictions.ilike("nombre", searchTerm, MatchMode.START));
                        break;
                    case 4:
                        mottoCriteria.add(Restrictions.ilike("nombre", searchTerm, MatchMode.END));
                        break;
                    case 5:
                        mottoCriteria.add(Restrictions.ilike("nombre", searchTerm, MatchMode.ANYWHERE));
                        break;
                    case 6:
                        mottoCriteria.add(Restrictions.not(Restrictions.ilike("nombre", searchTerm, MatchMode.START)));
                        break;
                    case 7:
                        mottoCriteria.add(Restrictions.not(Restrictions.ilike("nombre", searchTerm, MatchMode.END)));
                        break;
                    case 8:
                        mottoCriteria.add(Restrictions.not(Restrictions.ilike("nombre", searchTerm, MatchMode.ANYWHERE)));
                        break;
                }
            }
            else{
                mottoCriteria.add(Restrictions.ilike("nombre", searchTerm, MatchMode.EXACT));
            }
        }

        private void filterByCategoria(Map<String, Object> parameters, Criteria filterJuegoByCriteria) {
            if (parameters.containsKey("categorias")) {
                String[] categorias = (String[]) parameters.get("categorias");
                filterJuegoByCriteria.add(Restrictions.in("categoria", categorias));
            }
        }
        private void filterByMateria(Map<String, Object> parameters, Criteria filterJuegoByCriteria) {
            if (parameters.containsKey("materias")) {
                String[] materias = (String[]) parameters.get("materias");
                filterJuegoByCriteria.add(Restrictions.in("materia", materias));
            }
        }
        private void filterByRegion(Map<String, Object> parameters, Criteria filterJuegoByCriteria) {
            if (parameters.containsKey("regiones")) {
                String[] regiones = (String[]) parameters.get("regiones");
                filterJuegoByCriteria.add(Restrictions.in("region", regiones));
            }
        }

        private void filterByRegistro(Map<String, Object> parameters, Criteria filterJuegoByCriteria) {
            if (parameters.containsKey("registros")) {
                String[] registros = (String[]) parameters.get("registros");
                filterJuegoByCriteria.add(Restrictions.in("registro", registros));
            }
        }
*/

    }



