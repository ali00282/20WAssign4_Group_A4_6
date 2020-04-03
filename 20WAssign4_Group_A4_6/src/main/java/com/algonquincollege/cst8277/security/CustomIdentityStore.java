/**************************************************************************************************
 * File: CustomIdentityStore.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.security;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static java.util.Collections.emptySet;
import org.glassfish.soteria.WrappingCallerPrincipal;

import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.security.enterprise.credential.CallerOnlyCredential;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

@ApplicationScoped
@Default
public class CustomIdentityStore implements IdentityStore {

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        CredentialValidationResult result = INVALID_RESULT;

        if (credential instanceof UsernamePasswordCredential) {
            String callerName = ((UsernamePasswordCredential)credential).getCaller();
            String credentialPassword = ((UsernamePasswordCredential)credential).getPasswordAsString();
            SecurityUser user = jpaHelper.findUserByName(callerName);
            if (user != null) {
                String pwHash = user.getPwHash();
                try {
                    boolean verified = pbAndjPasswordHash.verify(credentialPassword.toCharArray(), pwHash);
                    if (verified) {
                        result = new CredentialValidationResult(new WrappingCallerPrincipal(user),
                            getRolesNamesForSecurityRoles(user.getRoles()));
                    }
                }
                catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
        // check if the credential was CallerOnlyCredential
        else if (credential instanceof CallerOnlyCredential) {
            String callerName = ((CallerOnlyCredential)credential).getCaller();
            SecurityUser user = jpaHelper.findUserByName(callerName);
            if (user != null) {
                result = new CredentialValidationResult(callerName);
            }
        }

        return result;
    }

    protected Set<String> getRolesNamesForSecurityRoles(Set<SecurityRole> roles) {
        Set<String> roleNames = emptySet();
        if (!roles.isEmpty()) {
            roleNames = roles
                .stream()
                .map(s -> s.getRoleName())
                .collect(Collectors.toSet());
        }
        return roleNames;
    }

}