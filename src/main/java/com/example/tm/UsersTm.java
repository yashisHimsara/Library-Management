package com.example.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersTm {
    private String userId;
    private String userName;
    private String email;
    private Button delete;
}
