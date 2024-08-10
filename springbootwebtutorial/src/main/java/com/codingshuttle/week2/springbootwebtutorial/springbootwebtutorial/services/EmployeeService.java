package com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.services;

import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id){
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id));
//        //Mapping/converting Entity to DTO
//        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1,EmployeeDTO.class));
        return employeeRepository.findById(id)
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));

    }

    public List<EmployeeDTO> getAllEmployees(){
       List<EmployeeEntity> employeeEntities =employeeRepository.findAll();
        //Mapping/converting list of Entity to list of DTO
       return employeeEntities
               .stream()
               .map(employeeEntity->modelMapper.map(employeeEntity,EmployeeDTO.class))
               .collect(Collectors.toList());

    }
    public EmployeeDTO createNewEmployee( EmployeeDTO inputEmployee){

        //to convert(mapping) the inputEmployee to EmployeeEntity
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        //Saved Entity
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);
        //Mapping/converting Entity to DTO
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        //If employee does not present with that id throw exception
        isExistEmployeeById(employeeId);


       EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
       employeeEntity.setId(employeeId);
       EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
       return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);

    }
    public  void isExistEmployeeById(Long employeeId){

        //If employee does not present with that id throw exception
        boolean exists = employeeRepository.existsById(employeeId);

        if(!exists)  throw new ResourceNotFoundException("Employee not found with id :"+employeeId);

    }

    public boolean deleteEmployeeById(Long employeeId) {
         isExistEmployeeById(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;

    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExistEmployeeById(employeeId);

        //Get the employee id
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        // Iterate over the updates map and update the corresponding fields in the employee entity
        updates.forEach((field, value) -> {
            // Find the field in EmployeeEntity by name
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            // Make the field accessible for modification
            fieldToBeUpdated.setAccessible(true);
            // Set the new value for the field in the employee entity
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class); // Save and convert to DTO


    }
}
