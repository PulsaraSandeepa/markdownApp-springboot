package com.markdown.auth.controllers;


import com.markdown.auth.dtos.RoleDTO;
import com.markdown.auth.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //create role
    @PostMapping("/create")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        System.out.println(roleDTO.getRole());

        checkNotNull(roleDTO);
        roleService.createRole(roleDTO);

        return roleDTO;
    }

    //get info about a specific role
    @GetMapping("/info/{roleID}")
    public RoleDTO getRoleInfo(@PathVariable String roleID) {
        System.out.println("role ID: " + roleID);

        return roleService.roleInfo(roleID);
    }

    //delete a role

    //modify a role

}
