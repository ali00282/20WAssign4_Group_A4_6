/***************************************************************************f******************u************zz*******y**
 * File: CustomProjectsEmployeesSerializer.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @date 2020 04
 *
 */
package com.algonquincollege.cst8277.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.algonquincollege.cst8277.models.ProjectPojo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class HollowProjectsSerializer extends StdSerializer<Set<ProjectPojo>> implements Serializable {
    private static final long serialVersionUID = 1L;

    public HollowProjectsSerializer() {
        this(null);
    }

    public HollowProjectsSerializer(Class<Set<ProjectPojo>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<ProjectPojo> originalProjects, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        
        Set<ProjectPojo> hollowProjects = new HashSet<>();
        for (ProjectPojo originalProject : originalProjects) {
            // create a 'hollow' copy of the originalProject
            ProjectPojo hollowP = new ProjectPojo();
            hollowP.setCREATED_DATE(originalProject.getCREATED_DATE());
            hollowP.setUPDATED_DATE(originalProject.getUPDATED_DATE());
            hollowP.setId(originalProject.getId());
            hollowP.setVersion(originalProject.getVersion());
            hollowP.setName(originalProject.getName());
            hollowP.setDescription(originalProject.getDescription());
            hollowProjects.add(hollowP);
        }
        generator.writeObject(hollowProjects);
    }
}
