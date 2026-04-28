package com.example.employeemanagement.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.message.Message;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Employee {

    @NotBlank(message = "ID can't be blank")
    @Size(min = 3, message = "ID must be at least 3 character")
    private String id;

    @NotBlank(message = "Name can't be blank")
    @Size(min = 5, message = "Name must be at least 5 character")
    @Pattern(regexp = "^[\\p{L}]+$", message = "Must contain only characters")
    private String name;

    @Email(message = "Not a valid email address")
    private String email;

    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and have 10 digits")
    private String phoneNumber;

    @NotNull(message = "Age can't be null")
    @Min(value = 26, message = "Age must be greater than 25")
    private int age;

    @NotBlank(message = "Position can't be blank")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'")
    private String position;

    private boolean onLeave = false;

    @NotNull(message = "Hire Date can't be null")
    @PastOrPresent(message = "Hire Date must be past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull(message = "Annual Leave can't be null")
    @PositiveOrZero(message = "Annual Leave must be positive number")
    private int annualLeave;

}
