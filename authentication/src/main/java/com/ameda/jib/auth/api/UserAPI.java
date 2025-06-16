package com.ameda.jib.auth.api;


import com.ameda.jib.auth.model.NewUser;
import com.ameda.jib.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: kev.Ameda
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAPI {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser ){
        userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{userId}/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable String userId ){
        userService.sendVerificationEmail(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String userId ){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String username ){
        userService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<?> getUserRoles(@PathVariable String userId ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRoles(userId));
    }

    @GetMapping("/{userId}/groups")
    public ResponseEntity<?> getUserGroups(@PathVariable String userId ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserGroups(userId));
    }

}
