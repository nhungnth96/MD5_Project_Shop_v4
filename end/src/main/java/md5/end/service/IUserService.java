package md5.end.service;

import md5.end.model.dto.request.ProfileEditForm;
import md5.end.model.dto.request.RegisterForm;
import md5.end.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    Page<User> findAll(int page , int size);
    Optional<User> findById(Long id);
    Optional<User> findByTel (String tel);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    User save(RegisterForm registerDto) throws LoginException;
    User update (Long id, ProfileEditForm profileEditForm) throws LoginException;
    void changeStatus(User user);
    List<User> searchAllByFullNameContainingIgnoreCase(String name);
}
