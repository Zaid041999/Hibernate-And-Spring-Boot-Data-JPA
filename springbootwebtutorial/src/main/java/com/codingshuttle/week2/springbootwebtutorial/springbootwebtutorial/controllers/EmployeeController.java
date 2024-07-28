package com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.controllers;

import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;

import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
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
  public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputEmployee){

      EmployeeDTO savedEmployeeDTO = employeeService.createNewEmployee(inputEmployee);
      return new ResponseEntity<>(savedEmployeeDTO, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long employeeId){
      return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

  @DeleteMapping(path = "/{employeeId}")
  public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
      boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
      if(!gotDeleted) return ResponseEntity.notFound().build();
      return ResponseEntity.ok(true);
    }

  @PatchMapping(path = "/{employeeId}")
  public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
