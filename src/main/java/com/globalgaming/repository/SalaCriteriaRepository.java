package com.globalgaming.repository;

/**
 * Created by usu26 on 10/02/2017.
 */

import com.globalgaming.domain.Sala;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

//import com.codegoons.diesli.domain.MottoDefinition;


@Repository
public class SalaCriteriaRepository {
    @PersistenceContext
    EntityManager entityManager;

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<Sala> filterSalaByCriteria(Map<String, Object> parameters) {

        Criteria salaCriteria = currentSession().createCriteria(Sala.class);
        salaCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        //Criteria idiomaCriteria = salaCriteria.createCriteria("idiomas");

        if(parameters.get("nombre")!= null) {
            String nombre = (String) parameters.get("nombre");
            salaCriteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
        }

        if(parameters.get("descripcion")!= null) {
            String descripcion = (String) parameters.get("descripcion");
            salaCriteria.add(Restrictions.ilike("descripcion", descripcion, MatchMode.ANYWHERE));
        }

        if(parameters.get("juego")!= null) {
            String juego = (String) parameters.get("juego");
            salaCriteria.add(Restrictions.ilike("juego", juego, MatchMode.ANYWHERE));
        }

//        if(parameters.get("idioma")!= null) {
//            String idioma = (String) parameters.get("idioma");
//            salaCriteria.add(Restrictions.ilike("idioma", idioma, MatchMode.ANYWHERE));
//        }

        // filtro por Límite de usuarios
        if ((parameters.get("minLimiteUsuarios") != null && parameters.get("minLimiteUsuarios") instanceof Integer)
            && (parameters.get("maxLimiteUsuarios") != null && parameters.get("maxLimiteUsuarios") instanceof Integer)
            ) {

            filterByLimiteUsuariosBetween(parameters, salaCriteria);
        }

        if (parameters.get("maxLimiteUsuarios") != null && parameters.get("minLimiteUsuarios") == null && parameters.get("maxCapacidadJugadores") instanceof Integer) {

            filterByMaxLimiteUsuarios(parameters, salaCriteria);
        }
        if (parameters.get("minLimiteUsuarios") != null && parameters.get("maxLimiteUsuarios") == null && parameters.get("minCapacidadJugadores") instanceof Integer) {

            filterByMinLimiteUsuarios(parameters, salaCriteria);
        }

        List<Sala> results = salaCriteria.list();

        return results;
    }


    private void filterByLimiteUsuariosBetween(Map<String, Object> parameters, Criteria salaCriteria) {
        Integer minLimiteUsuarios = (Integer) parameters.get("minLimiteUsuarios");
        Integer maxLimiteUsuarios = (Integer) parameters.get("maxLimiteUsuarios");

        salaCriteria.add(Restrictions.between("capacidadJugadores",minLimiteUsuarios,maxLimiteUsuarios));
    }


    private void filterByMaxLimiteUsuarios(Map<String, Object> parameters, Criteria salaCriteria) {
        Integer maxLimiteUsuarios = (Integer) parameters.get("maxLimiteUsuarios");

        salaCriteria.add(Restrictions.le("LimiteUsuarios", maxLimiteUsuarios));
    }
    private void filterByMinLimiteUsuarios(Map<String, Object> parameters, Criteria salaCriteria) {
        Integer minLimiteUsuarios = (Integer) parameters.get("minLimiteUsuarios");

        salaCriteria.add(Restrictions.ge("LimiteUsuarios", minLimiteUsuarios));
    }


}



