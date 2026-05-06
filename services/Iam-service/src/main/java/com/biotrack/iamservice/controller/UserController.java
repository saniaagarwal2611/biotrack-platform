package com.biotrack.iamservice.controller;

import com.biotrack.iamservice.dto.request.UserRequestDTO;
import com.biotrack.iamservice.dto.response.UserResponseDTO;
import com.biotrack.iamservice.enums.Role;
import com.biotrack.iamservice.enums.UserStatus;
import com.biotrack.iamservice.exception.IdNotFoundException;
import com.biotrack.iamservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        return new ResponseEntity<>(userService.addUser(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @RequestBody UserRequestDTO requestDTO) throws IdNotFoundException {
        return new ResponseEntity<>(userService.updateUser(id, requestDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateUserRole(@PathVariable Long id,
                                                          @RequestParam Role role) throws IdNotFoundException {
        return new ResponseEntity<>(userService.updateUserRole(id, role), HttpStatus.OK);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<List<String>> getUserPermissions(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(userService.getUserPermissions(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable Long id,
                                                @RequestParam String newPassword) throws IdNotFoundException {
        return new ResponseEntity<>(userService.resetPassword(id, newPassword), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws IdNotFoundException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponseDTO> updateUserStatus(@PathVariable Long id,
                                                            @RequestParam UserStatus status) throws IdNotFoundException {
        return new ResponseEntity<>(userService.updateUserStatus(id, status), HttpStatus.OK);
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UserResponseDTO> assignRole(@PathVariable Long id,
                                                      @RequestParam Role role) throws IdNotFoundException {
        return new ResponseEntity<>(userService.updateUserRole(id, role), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(Arrays.asList(Role.values()), HttpStatus.OK);
    }
}
