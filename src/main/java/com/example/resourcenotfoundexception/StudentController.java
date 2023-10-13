package com.example.resourcenotfoundexception;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@DynamicInsert
@DynamicUpdate
@RestController
public class StudentController {

	@Autowired
	StudentRepository srepo;
	
	@PostMapping("/saved")
	public ResponseEntity<Student> saves(@RequestBody Student student)
	{
		 Student user=this.srepo.save(student);
		 return new ResponseEntity<>(user,HttpStatus.CREATED);
	}
	@GetMapping("/findall")
	public ResponseEntity<List<Student>> findf()
	{
		return ResponseEntity.ok(srepo.findAll());
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<Student> findby(@PathVariable int id)
	{
		Student student = this.srepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("student","id",id));
		return ResponseEntity.ok(student);
	}
	@PutMapping("/users/{id}")
	public ResponseEntity<?> saveResource(@RequestBody Student student,@PathVariable int id)
	{
		srepo.save(student);
		return ResponseEntity.ok("resource saved.............................!");
	}
    @PutMapping("/put/{id}")
    public ResponseEntity<Student> updateById(@PathVariable int id,@RequestBody Student student)
    {
    	Optional<Student> user=srepo.findById(id);

    	if(user.isPresent())
    	{
    		Student existUser=user.get();
    		existUser.setAge( student.getAge());
    		existUser.setName(student.getName());
    	
    		
    		Student u=srepo.save(existUser);
    		
    		return new ResponseEntity<>(u,HttpStatus.ACCEPTED);
    	}
    	else
    	{
    		System.out.println("Student Details does not exist for id "+id);
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }


}

