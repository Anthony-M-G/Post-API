package com.cr.app.postapi.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    @NotNull(message = "El campos postId es requerido")
    @Min(1)
    @Positive
    private Long postId;
    @NotEmpty(message = "El campo email es requerido")
    @NotNull(message = "El campo email es requerido")
    @Email(message = "Formato inválido de email")
    private String email;
    @NotEmpty(message = "El name body es requerido")
    private String name;
    @NotEmpty(message = "El campo body es requerido")
    private String body;

}
