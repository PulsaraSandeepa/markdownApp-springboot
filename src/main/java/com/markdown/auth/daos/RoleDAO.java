package com.markdown.auth.daos;

import com.markdown.auth.models.MarkDownRoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends MongoRepository<MarkDownRoleModel, String> {
    Optional<MarkDownRoleModel> findById(String id);

}
