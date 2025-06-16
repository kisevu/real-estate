package com.ameda.jib.auth.api;

import com.ameda.jib.auth.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleApi {


    private final RoleService roleService;

    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?>  assignRole(@PathVariable String userId, @RequestParam String roleName){
        roleService.assignRole(userId,roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/unassign/users/{userId}")
    public ResponseEntity<?> unassignRole(@PathVariable String userId, @RequestParam String roleName ){
        roleService.deleteRoleFromUser(userId,roleName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
