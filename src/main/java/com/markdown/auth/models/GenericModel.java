package com.markdown.auth.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static java.util.Objects.isNull;

//import the getters and setters
@Data
public class GenericModel implements Serializable , Persistable<String> {

    @Id
    private String id;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public GenericModel(){
        this.id = UUID.randomUUID().toString();
    }

    //tell mongo this object is new or not yes-create a new date
    @Override
    public boolean isNew() {
        return isNull(this.createdAt);
    }
}
