package com.javadabadu.disney.models.mapped;

import com.javadabadu.disney.models.dto.*;
import com.javadabadu.disney.models.entity.*;

import java.util.List;

public interface ModelMapperDTO {
    PeliculaResponseDTO peliculaToResponseDTO(Pelicula pelicula);

    PersonajeResponseDTO personajeToResponseDTO(Personaje personaje);

    List<PersonajeResponseDTO> listPersonajeToResponseDTO(List<Personaje> listPersonaje);

    Personaje personajeRequestDtoToPersonaje(PersonajeRequestDTO personajeRequestDTO);

    GeneroResponseDTO generoToResponseDTO(Genero genero);

    Genero generoRequestDtoToPersonaje(GeneroRequestDTO generoRequestDTO);

    List<GeneroResponseDTO> listGeneroToResponseDTO(List<Genero> listGenero);

    List<AudioVisualResponseDTO> listAudiovisualToResponseDTO(List<AudioVisual> listAudiovisual);

    AudioVisualResponseDTO audioVisualToResponseDTO(AudioVisual audiovisual);

    AudioVisual audioVisualResponseToAudiovisual(AudioVisualResponseDTO audiovisualDTO);

    SerieResponseDTO serieToResponseDTO(Serie serie);

    Pelicula responseDtoToPelicula(PeliculaResponseDTO peliculaResponseDTO);

    Serie responseDtoToSerie(SerieResponseDTO serieResponseDTO);

    SerieDtoPatch seriePatchDto(Serie serie);

    PeliculaPatchDTO peliculaPatchDTO(Pelicula pelicula);

    Serie requestDtoToSerie(SerieRequestDTO serieRequestDTO);

}
