package com.example.dao.custom;

import com.example.dao.CrudDAO;
import com.example.entity.Branches;

public interface BranchesDAO extends CrudDAO<Branches> {
    public String getLastBranchId();
}
