package com.markdown.auth.services.impl;

import com.markdown.auth.daos.RoleDAO;
import com.markdown.auth.dtos.RoleDTO;
import com.markdown.auth.models.MarkDownRoleModel;
import com.markdown.auth.models.MarkDownUserModel;
import com.markdown.auth.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceimpl implements RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createRole(RoleDTO roleDTO) {

        MarkDownRoleModel markDownRoleModel = modelMapper.map(roleDTO, MarkDownRoleModel.class);

        roleDAO.save(markDownRoleModel);

        modelMapper.map(markDownRoleModel, roleDTO);
    }

    @Override
    public RoleDTO roleInfo(String roleId) {

        Optional<MarkDownRoleModel> markDownRoleModelOptional = roleDAO.findById(roleId);

        if (markDownRoleModelOptional.isPresent()) {
            final MarkDownRoleModel markDownRoleModel = markDownRoleModelOptional.get();

            return modelMapper.map(markDownRoleModel, RoleDTO.class);
        }
        return null;
    }
}
