package com.example.bo.custom;

import com.example.bo.SuperBO;
import com.example.dto.BranchesDTO;

import java.util.List;

public interface BranchesBO extends SuperBO {
    boolean addBranch(BranchesDTO dto);
    List<BranchesDTO> getAllBranch();
    boolean updateBranch(BranchesDTO dto);
    boolean isExistBranch(String id);
    BranchesDTO searchBranch(String id);
    boolean deleteBranch(String id);
    public String getLastBranchId();
}
