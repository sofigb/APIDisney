package com.javadabadu.disney.models.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;

@Getter
@Setter
public class SerieDtoPatch {
    private Integer id;
    private String titulo;
    private String imagen;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar fechaCreacion;
    private GeneroResponseDTO genero;
    private Byte temporadas;
    private Byte capitulos;
}
