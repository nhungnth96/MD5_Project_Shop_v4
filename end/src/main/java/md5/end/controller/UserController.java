package md5.end.controller;

import md5.end.model.dto.request.ProfileEditForm;
import md5.end.model.entity.user.User;
import md5.end.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<User>> findAll() {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
//    public ResponseEntity<Page<User>> findAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
//        return new ResponseEntity<>(userService.findAll(page,size), HttpStatus.OK);
//    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> searchByName(@RequestParam(name = "q") String fullName) {
        if(userService.searchAllByFullNameContainingIgnoreCase(fullName).isEmpty()){
            return new ResponseEntity<>("No result found.", HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.searchAllByFullNameContainingIgnoreCase(fullName), HttpStatus.OK);
    }
    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
        }
        if (userOptional.get().getRoles().size() > 1) {
            return new ResponseEntity<>("Can't change status", HttpStatus.BAD_REQUEST);
        }
        userService.changeStatus(userOptional.get());
        return new ResponseEntity<>("Change status successfully", HttpStatus.OK);
    }

}
