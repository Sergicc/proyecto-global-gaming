package com.globalgaming.repository;

/**
 * Created by usu26 on 10/02/2017.
 */

import com.globalgaming.domain.User;
import com.globalgaming.domain.UserExt;
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
public class UserExtCriteriaRepository {
    @PersistenceContext
    EntityManager entityManager;

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }

    public List<UserExt> filterUserExtByCriteria(Map<String, Object> parameters) {

        Criteria userExtCriteria = currentSession().createCriteria(UserExt.class);
        userExtCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //idiomas mismo error que con juegos
        //Criteria idiomaCriteria = salaCriteria.createCriteria("idiomas");

        /*@RequestParam(value = "nick", required = false) String nick,
        @RequestParam(value = "idBattlenet", required = false) Boolean idBattlenet,
        @RequestParam(value = "idSteam", required = false) Boolean idSteam,
        @RequestParam(value = "idOrigin", required = false) Boolean idOrigin,
        @RequestParam(value = "idLol", required = false) Boolean idLol,
        @RequestParam(value = "pais", required = false) String pais*/

        if(parameters.get("nick")!= null) {
            String nick = (String) parameters.get("nick");
            userExtCriteria.add(Restrictions.ilike("nick", nick, MatchMode.ANYWHERE));
        }

        /** FILTERR BYY BOOLEANN VALUESS **/
        if(parameters.get("idBattlenet")!=null){

            filterByExists(parameters,userExtCriteria,"idBattlenet");
        }

        if(parameters.get("idSteam")!=null){

            filterByExists(parameters,userExtCriteria,"idSteam");
        }

        if(parameters.get("idOrigin")!=null){

            filterByExists(parameters,userExtCriteria,"idOrigin");
        }

        if(parameters.get("idLol")!=null){

            filterByExists(parameters,userExtCriteria,"idLol");
        }

        if(parameters.get("pais")!= null) {
            String pais = (String) parameters.get("pais");
            userExtCriteria.add(Restrictions.ilike("pais", pais, MatchMode.ANYWHERE));
        }

        List<UserExt> results = userExtCriteria.list();

        return results;
    }

    private void filterByExists(Map<String,Object> parameters, Criteria userExtCriteria,String key) {
        Boolean hasItem = (Boolean) parameters.get(key);

        userExtCriteria.add(Restrictions.eq(key, hasItem));
    }
}



