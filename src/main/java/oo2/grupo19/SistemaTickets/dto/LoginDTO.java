package oo2.grupo19.SistemaTickets.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoginDTO {
    private String email;
    private String password;
}
