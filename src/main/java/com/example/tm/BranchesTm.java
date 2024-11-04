package com.example.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchesTm {
    private String code;
    private String location;
    private String contactNumber;
    private String status;
    private Button remove;
}
