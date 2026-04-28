package com.example.employeemanagement.Controller;

import com.example.employeemanagement.ApiResponse.ApiResponse;
import com.example.employeemanagement.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        //check id
        for(Employee e: employees){
            if(e.getId().equalsIgnoreCase(employee.getId()))
                return ResponseEntity.status(400).body(new ApiResponse("An employee with ID: " + employee.getId() + " is already exist"));
        }

        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployees(){
        return ResponseEntity.status(200).body(employees);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody @Valid Employee newEmployee, Errors errors){
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equalsIgnoreCase(id)){
                newEmployee.setId(id); // make sure the id doesn't change
                employees.set(i, newEmployee);
                return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
            }
        }

        return ResponseEntity.status(404).body(new ApiResponse("Employee with ID: " + id + " Not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id){
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i).getId().equalsIgnoreCase(id)){
                employees.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
            }
        }

        return ResponseEntity.status(404).body(new ApiResponse("Employee with ID: " + id + " Not found"));
    }

    //EXTRA ENDPOINTS
    @GetMapping("/get-position/{position}")
    public ResponseEntity<?> getByPosition(@PathVariable String position){
        if(!position.equalsIgnoreCase("supervisor") && !position.equalsIgnoreCase("coordinator"))
            return ResponseEntity.status(400).body(new ApiResponse("Invalid position, please enter supervisor or coordinator"));

        ArrayList<Employee> positionEmployees = new ArrayList<>();

        for(Employee e: employees){
            if(e.getPosition().equalsIgnoreCase(position))
                positionEmployees.add(e);
        }

        if(positionEmployees.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("There is no employees in position: " + position));

        return ResponseEntity.status(200).body(positionEmployees);
    }

    @GetMapping("get-age-range/{minAge}/{maxAge}")
    public ResponseEntity<?> getByAgeRange(@PathVariable int minAge, @PathVariable int maxAge){
        //check min and max values
        if(minAge < 26 )
            return ResponseEntity.status(400).body(new ApiResponse("Minimum age must be greater than 25"));
        if(minAge > maxAge)
            return ResponseEntity.status(400).body(new ApiResponse("Maximum age must be larger than minimum age"));

        ArrayList<Employee> employeesWithAgeRange = new ArrayList<>();

        for(Employee e: employees){
            if(e.getAge() >= minAge && e.getAge() <= maxAge)
                employeesWithAgeRange.add(e);
        }

        if(employeesWithAgeRange.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse("There is no employees within the range: (" + minAge + ", " + maxAge + ")"));

        return ResponseEntity.status(200).body(employeesWithAgeRange);
    }
}
