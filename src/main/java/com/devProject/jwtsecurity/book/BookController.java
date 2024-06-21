package com.devProject.jwtsecurity.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService service;

	@Operation(summary = "Get a book by its id")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
	    content = @Content), 
	  @ApiResponse(responseCode = "404", description = "Book not found", 
	    content = @Content) })
	@PostMapping
	public ResponseEntity<?> save(@RequestBody BookRequest request) {
		service.save(request);
		return ResponseEntity.accepted().build();
	}

	@GetMapping
	public ResponseEntity<List<Book>> findAllBooks() {
		return ResponseEntity.ok(service.findAll());
	}
}
