package md5.end.service.impl;


import md5.end.model.dto.request.ProfileEditForm;
import md5.end.model.dto.request.RegisterForm;
import md5.end.model.entity.user.Role;
import md5.end.model.entity.user.RoleName;
import md5.end.model.entity.user.User;
import md5.end.repository.IUserRepository;
import md5.end.service.IRoleService;
import md5.end.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByTel(String tel) {
        return userRepository.findByTel(tel);
    }


    @Override
    public List<User> searchAllByFullNameContainingIgnoreCase(String name) {
        return userRepository.searchAllByFullNameContainingIgnoreCase(name);
    }

    @Override
    public User save(RegisterForm registerDto) throws LoginException {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new LoginException("Username is existed");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new LoginException("Email is existed");
        }
        Set<Role> roles = new HashSet<>();
        if(registerDto.getRoles()==null||registerDto.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName(RoleName.ROLE_BUYER));
        } else {
            for (String role : registerDto.getRoles()) {
                if(!(role.equalsIgnoreCase("admin")|| role.equalsIgnoreCase("seller")||role.equalsIgnoreCase("buyer"))){
                    throw new LoginException("Case role name is wrong");
                }
                switch (role) {
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "seller":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                    case "buyer":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_BUYER));
                }
            }

        }
        return userRepository.save( User.builder()
                .fullName(registerDto.getFullName())
                .username(registerDto.getUsername())
                .password(encoder.encode(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .createdDate(LocalDate.now().toString())
                .status(true)
                .roles(roles)
                .build());
    }

    @Override
    public User update(Long id, ProfileEditForm profileEditForm) throws LoginException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(profileEditForm.getName());
            user.setEmail(profileEditForm.getEmail());
        }
        return null;
    }

    @Override
    public void changeStatus(User user) {
        user.setStatus(!user.isStatus());
        userRepository.save(user);
    }
}
