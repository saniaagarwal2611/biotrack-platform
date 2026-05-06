package com.biotrack.iamservice.service.implementation;

import com.biotrack.iamservice.dto.request.UserRequestDTO;
import com.biotrack.iamservice.dto.response.UserResponseDTO;
import com.biotrack.iamservice.entity.User;
import com.biotrack.iamservice.enums.Role;
import com.biotrack.iamservice.enums.UserStatus;
import com.biotrack.iamservice.exception.IdNotFoundException;
import com.biotrack.iamservice.repository.UserRepository;
import com.biotrack.iamservice.service.UserService;
import com.biotrack.iamservice.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO requestDTO) {
        UserStatus status = requestDTO.getStatus() != null ? requestDTO.getStatus() : UserStatus.ACTIVE;
        User user = new User(
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getPhone(),
                requestDTO.getRole(),
                status,
                passwordEncoder.encode(requestDTO.getPassword())
        );
        User saved = userRepository.save(user);
        return DtoMapper.toUserResponseDTO(saved);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(DtoMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        return DtoMapper.toUserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) throws IdNotFoundException {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));

        existing.setName(requestDTO.getName());
        existing.setEmail(requestDTO.getEmail());
        existing.setPhone(requestDTO.getPhone());
        existing.setRole(requestDTO.getRole());

        if (requestDTO.getStatus() != null) {
            existing.setStatus(requestDTO.getStatus());
        }

        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }

        User updated = userRepository.save(existing);
        return DtoMapper.toUserResponseDTO(updated);
    }

    @Override
    public String deleteUser(Long id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
        return "User deleted successfully with ID: " + id;
    }

    @Override
    public UserResponseDTO updateUserRole(Long id, Role role) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        user.setRole(role);
        User updated = userRepository.save(user);
        return DtoMapper.toUserResponseDTO(updated);
    }

    @Override
    public UserResponseDTO updateUserStatus(Long id, UserStatus status) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        user.setStatus(status);
        User updated = userRepository.save(user);
        return DtoMapper.toUserResponseDTO(updated);
    }

    @Override
    public String resetPassword(Long id, String newPassword) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password reset successfully for user ID: " + id;
    }

    @Override
    public List<String> getUserPermissions(Long id) throws IdNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("User not found with ID: " + id));
        return Collections.singletonList("ROLE_" + user.getRole());
    }
}
