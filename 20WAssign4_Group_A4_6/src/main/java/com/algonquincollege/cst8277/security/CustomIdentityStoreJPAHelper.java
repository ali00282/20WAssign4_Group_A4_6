/**************************************************************************************************
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * Group# A4_6
  * @author Tariq Ali - 040811012
 * @author Sirisha Jayampu - 040879561
 * @author Asha Alphonsa kurian - 040921013
 * @author Shahrir Ahmed - 040920464
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.List;
import java.util.Set;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.algonquincollege.cst8277.models.EmployeePojo;
import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

@Singleton
public class CustomIdentityStoreJPAHelper {

    @PersistenceContext(name=PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            user = null;
            TypedQuery<SecurityUser> q = em.createQuery("select secureUser from SecurityUser secureUser where secureUser.username = :param1",
                SecurityUser.class).setParameter("param1", username);
            user = q.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Set<String> findRoleNamesForUser(String username) {
        Set<String> rolenames = emptySet();
        //TODO
        
        try {
            rolenames = null;
            TypedQuery<SecurityRole> q = em.createQuery("select r from Securityrole r Join secureUser s where s.username = :param1",
            SecurityRole.class).setParameter("param1", username);
            List<SecurityRole> roles = q.getResultList();
            for (int i=0;i<roles.size();i++)
            {
                rolenames.add(roles.get(i).getRoleName());
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
      
    }
        return rolenames;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveSecurityUser(SecurityUser user) {
        em.persist(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveSecurityRole(SecurityRole role) {
        em.persist(role);
    }
}