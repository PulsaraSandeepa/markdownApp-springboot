package com.markdown.auth.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

//import the getters and setters
@Data
//because we extend the generic class
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class MarkDownUserModel extends GenericModel{

    //set unique fields
    @Indexed(direction = IndexDirection.DESCENDING, unique =true)
    private String username;

    @Indexed(direction = IndexDirection.DESCENDING, unique =true)
    private String displayName;

    @Indexed(direction = IndexDirection.DESCENDING, unique =true)
    private String email;

    private String jwtToken;
    private String password;
    private List<String> roles;

    public MarkDownUserModel(){
        super();
    }
}
