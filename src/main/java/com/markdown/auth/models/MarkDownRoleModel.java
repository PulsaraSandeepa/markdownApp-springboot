package com.markdown.auth.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

//import the getters and setters
@Data
@Document(collection = "roles")
//because we extend the generic class
@EqualsAndHashCode(callSuper = true)

public class MarkDownRoleModel extends GenericModel {

    private String role;

    public MarkDownRoleModel(){
        super();
    }
}


