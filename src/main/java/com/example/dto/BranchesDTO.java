package com.example.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchesDTO {
    private String code;
    private String location;
    private String contactNumber;
    private String status;
}
