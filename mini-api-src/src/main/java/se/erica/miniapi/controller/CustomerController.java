package se.erica.miniapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final List<CustomerResponse> customers = new ArrayList<>();

    public CustomerController() {
        customers.add(new CustomerResponse(1L, "Erica", "erica@example.com"));
        customers.add(new CustomerResponse(2L, "Anna", "anna@example.com"));
    }

    @GetMapping
    public List<CustomerResponse> getCustomers(@RequestParam(required = false) String email) {
        if (email == null || email.isBlank()) {
            return customers;
        }

        return customers.stream()
                .filter(customer -> customer.email().equalsIgnoreCase(email))
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customers.stream()
                .filter(customer -> customer.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        if (request.email() == null || request.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        boolean emailExists = customers.stream()
                .anyMatch(customer -> customer.email().equalsIgnoreCase(request.email()));

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with this email already exists");
        }

        long nextId = customers.stream()
                .mapToLong(CustomerResponse::id)
                .max()
                .orElse(0L) + 1;

        CustomerResponse created = new CustomerResponse(nextId, request.name(), request.email());
        customers.add(created);
        return created;
    }

    @GetMapping("/status")
    public String status() {
        return "API is running";
    }

    public record CustomerResponse(Long id, String name, String email) {
    }

    public record CreateCustomerRequest(String name, String email) {
    }
}