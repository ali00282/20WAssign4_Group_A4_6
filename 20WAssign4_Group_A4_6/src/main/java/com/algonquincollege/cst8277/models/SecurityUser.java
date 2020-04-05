/***************************************************************************f******************u************zz*******y**
 * File: SecurityUser.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */
@Entity
@Table(name="SECURITY_USER")
public class SecurityUser implements Serializable, Principal {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String username;
    protected String pwHash;
    protected Set<SecurityRole> roles;
    protected EmployeePojo employee;

    public SecurityUser() {
        super();
    }

    @Column(name="USER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
    
    @ManyToMany(targetEntity = SecurityRole.class, cascade = CascadeType.PERSIST)
    @JoinTable(name="SECURITY_USER_SECURITY_ROLE",
        joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
        inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID"))

    public Set<SecurityRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }
    
    @OneToOne
    @JoinColumn(name="EMP_ID")
    public EmployeePojo getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeePojo employee) {
        this.employee = employee;
    }

    //Principal
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityUser other = (SecurityUser)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}