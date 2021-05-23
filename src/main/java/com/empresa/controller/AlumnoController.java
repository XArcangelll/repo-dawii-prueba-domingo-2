package com.empresa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/rest/alumno")
public class AlumnoController {

	@Autowired
	private AlumnoService service;
	
	@GetMapping
	public ResponseEntity<List<Alumno>> lista(){
		log.info(">>> inicio >>> lista ");
		List<Alumno> lstAlumno = service.listaAlumno();
		return ResponseEntity.ok(lstAlumno);
	}
	
	@PostMapping
	public ResponseEntity<Alumno> registra(@RequestBody Alumno obj){
		log.info(">>> inicio >>> registra " + obj.getNombre());
		Alumno objAlumno = service.insertaActualizaAlumno(obj);
		return ResponseEntity.ok(objAlumno);
	}
	
	@PutMapping
	public ResponseEntity<Alumno> actualiza(@RequestBody Alumno obj){
		log.info(">>> inicio >>> actualiza " + obj.getNombre());
		Optional<Alumno> optAlumno = service.obtienePorId(obj.getIdAlumno());
		if(optAlumno.isPresent()) {
			Alumno objAlumno = service.insertaActualizaAlumno(obj);
			return ResponseEntity.ok(objAlumno);
		}else {
			log.info(">>> No existe el alumno " + obj.getIdAlumno());
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Alumno> elimina(@PathVariable("id") int idAlumno){
		log.info(">>> inicio >>> elimina " + idAlumno);
		Optional<Alumno> optAlumno = service.obtienePorId(idAlumno);
		if(optAlumno.isPresent()) {
			service.eliminaAlumno(idAlumno);
			return ResponseEntity.ok(optAlumno.get());
		}else {
			log.info(">>> No existe el alumno " + idAlumno);
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<List<Alumno>> listaPorDni(@PathVariable("dni") String dni){
		log.info(">>> inicio >>> busca " + dni);
		List<Alumno> lstAlumno  = service.listaPorDni(dni);
			return ResponseEntity.ok(lstAlumno);

	}
	
	
}
