package com.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.model.BodyWeightModel;
import com.fitness.service.BodyWeightService;

@CrossOrigin(origins = "http://localhost:8080")  // リクエストを許可するオリジンを指定
@RestController
public class BodyWeightController {

	@Autowired
	private BodyWeightService service;
	
	@GetMapping("/body-weight")
	public ResponseEntity<List<BodyWeightModel>> getAll() {
		List<BodyWeightModel> allItems = service.getAll();
		if (allItems.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(allItems);
		}
	}
	
	@PostMapping("/body-weight")
	public ResponseEntity<Object> update(@RequestBody BodyWeightModel updateData) {
	    int updateCount = service.update(updateData);
	    if (updateCount == 0) {
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.ok().build();
	    }
	}

	@DeleteMapping("/body-weight")
	public ResponseEntity<Object> delete(@RequestParam(name = "id") Integer id) {
	    int deleteCount = service.delete(id);
	    if (deleteCount == 0) {
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.ok().build();
	    }
	}
}
