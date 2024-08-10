package com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;

import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
  /*  @GetMapping(path = "/getsecretmessage")
    public String getMySecretMessage(){
        return "Secret Message:@1!!!32#$5%4";
    }*/

   /* //We should service layer in between we directly don't use repository in controller
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }*/
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*@GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name="employeeId") Long id){
        return employeeRepository.findById(id).orElse(null);
    }//here repository returning the Entity but when we use service layer the we will use dto(For which we have to map the data)
    // a proper mvc format*/


    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name="employeeId") Long id){
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))// If present, return HTTP 200 with employee data
               // .orElse(ResponseEntity.notFound().build());// If not present, return HTTP 404
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id :"+id));
    }




    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name = "inputAge") Integer age,
                                                @RequestParam(required = false) String sortingBy){
       return ResponseEntity.ok(employeeService.getAllEmployees()) ;
    }

  /*  @PostMapping
    public String createNewEmployee(){
        return "Hi from Post";
    }*/
  @PostMapping
  public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){

      EmployeeDTO savedEmployeeDTO = employeeService.createNewEmployee(inputEmployee);
      return new ResponseEntity<>(savedEmployeeDTO, HttpStatus.CREATED); // Return 201 Created with the saved employee
  }

  @PutMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,@PathVariable Long employeeId){
      return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));// return HTTP 200 with employee data
    }

  @DeleteMapping(path = "/{employeeId}")
  public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
      boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
      if(!gotDeleted) return ResponseEntity.notFound().build();// If not present, return HTTP 404
      return ResponseEntity.ok(true);// If present, return HTTP 200 with employee data
    }

  @PatchMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();// If not present, return HTTP 404
        return ResponseEntity.ok(employeeDTO);// If present, return HTTP 200 with employee data
    }
}
