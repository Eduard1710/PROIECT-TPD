package com.example.dao;

import javax.ejb.Remote;

import com.example.dto.RoleDTO;

@Remote
public interface RoleDAORemote extends GenericDAO<RoleDTO> {

}
