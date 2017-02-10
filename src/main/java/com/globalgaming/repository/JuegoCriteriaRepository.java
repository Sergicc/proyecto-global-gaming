package com.globalgaming.repository;

/**
 * Created by usu26 on 10/02/2017.
 */

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


/*
    @Repository
    public class JuegoCriteriaRepository { {
        @PersistenceContext
        EntityManager entityManager;

        protected Session currentSession() {
            return entityManager.unwrap(Session.class);
        }

        public List<MottoDefinition> filterMottoDefinitions(Map<String, Object> parameters) {

            Criteria mottoDefinitionCriteria = currentSession().createCriteria(MottoDefinition.class);
            mottoDefinitionCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            Criteria mottoCriteria = mottoDefinitionCriteria.createCriteria("motto");

            filterBySearchBy(parameters, mottoCriteria);

            filterByCategoria(parameters, mottoDefinitionCriteria);

            filterByMateria(parameters, mottoDefinitionCriteria);

            filterByRegion(parameters,mottoDefinitionCriteria);

            filterByRegistro(parameters,mottoDefinitionCriteria);

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

        private void filterByCategoria(Map<String, Object> parameters, Criteria mottoDefinitionCriteria) {
            if (parameters.containsKey("categorias")) {
                String[] categorias = (String[]) parameters.get("categorias");
                mottoDefinitionCriteria.add(Restrictions.in("categoria", categorias));
            }
        }
        private void filterByMateria(Map<String, Object> parameters, Criteria mottoDefinitionCriteria) {
            if (parameters.containsKey("materias")) {
                String[] materias = (String[]) parameters.get("materias");
                mottoDefinitionCriteria.add(Restrictions.in("materia", materias));
            }
        }
        private void filterByRegion(Map<String, Object> parameters, Criteria mottoDefinitionCriteria) {
            if (parameters.containsKey("regiones")) {
                String[] regiones = (String[]) parameters.get("regiones");
                mottoDefinitionCriteria.add(Restrictions.in("region", regiones));
            }
        }

        private void filterByRegistro(Map<String, Object> parameters, Criteria mottoDefinitionCriteria) {
            if (parameters.containsKey("registros")) {
                String[] registros = (String[]) parameters.get("registros");
                mottoDefinitionCriteria.add(Restrictions.in("registro", registros));
            }
        }


    }

*/
