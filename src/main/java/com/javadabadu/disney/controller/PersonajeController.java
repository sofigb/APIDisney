package com.javadabadu.disney.controller;

import com.javadabadu.disney.exception.ExceptionBBDD;
import com.javadabadu.disney.models.dto.request.PersonajeRequestDTO;
import com.javadabadu.disney.models.dto.response.PersonajeResponseDTO;
import com.javadabadu.disney.models.dto.response.ResponseInfoDTO;
import com.javadabadu.disney.service.PersonajeService;
import com.javadabadu.disney.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = Uri.PERSONAJES)
public class PersonajeController {

    @Autowired
    PersonajeService personajeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PersonajeResponseDTO>> findById(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        PersonajeResponseDTO personajeDTO = personajeService.findById(id);
        return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));

    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PersonajeResponseDTO>>> findAll(HttpServletRequest request) throws ExceptionBBDD {
        List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.findAll();
        List<EntityModel<PersonajeResponseDTO>> personajes = new ArrayList<>();
        for (PersonajeResponseDTO personaje : listPersonajeResponseDTO) {
            personajes.add(EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
    }

    @PostMapping("/")
    public ResponseEntity<EntityModel<ResponseInfoDTO>> lastId(HttpServletRequest request) throws ExceptionBBDD {
        ResponseInfoDTO response = new ResponseInfoDTO("Se creó un registro", request.getRequestURI(), HttpStatus.CREATED.value());
        return ResponseEntity.created(URI.create(request.getRequestURI() + personajeService.lastValueId())).body(EntityModel.of(response, personajeService.getCollectionLink(request)));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PersonajeResponseDTO>> crear(@RequestBody PersonajeRequestDTO personaje,
                                                                   @PathVariable Integer id,
                                                                   HttpServletRequest request) throws ExceptionBBDD {
        PersonajeResponseDTO personajeDTO = personajeService.getPersistenceEntity(personaje, id);
        return ResponseEntity.ok().body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request), personajeService.getCollectionLink(request)));
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<PersonajeResponseDTO>> update(@PathVariable Integer id,
                                                                    @RequestBody Map<String, Object> propiedades,
                                                                    HttpServletRequest request) throws ExceptionBBDD {
        PersonajeResponseDTO personajeDTO = personajeService.updatePartial(id, propiedades);
        return ResponseEntity.status(HttpStatus.OK).body(EntityModel.of(personajeDTO, personajeService.getSelfLink(id, request)));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ResponseInfoDTO>> delete(@PathVariable Integer id, HttpServletRequest request) throws ExceptionBBDD {
        String body = personajeService.softDelete(personajeService.findById(id).getId());
        ResponseInfoDTO response = new ResponseInfoDTO(body, request.getRequestURI(), HttpStatus.OK.value());
        return ResponseEntity.ok().body(EntityModel.of(response, personajeService.getCollectionLink(request)));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PersonajeResponseDTO>>> findAllFilter(@RequestParam(value = "name", required = false) String nombre,
                                                                                            @RequestParam(value = "age", required = false) Integer edad,
                                                                                            @RequestParam(value = "movies", required = false) Integer idPelicula,
                                                                                            HttpServletRequest request) throws ExceptionBBDD {

        List<PersonajeResponseDTO> listPersonajeResponseDTO = personajeService.filterCharacter(nombre, edad, idPelicula);
        List<EntityModel<PersonajeResponseDTO>> personajes = new ArrayList<>();
        for (PersonajeResponseDTO personaje : listPersonajeResponseDTO) {
            personajes.add(EntityModel.of(personaje, personajeService.getSelfLink(personaje.getId(), request)));
        }
        return ResponseEntity.ok().body(CollectionModel.of(personajes, personajeService.getCollectionLink(request)));
    }

}
