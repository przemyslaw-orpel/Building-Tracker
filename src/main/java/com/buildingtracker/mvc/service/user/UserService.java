package com.buildingtracker.mvc.service.user;

import com.buildingtracker.mvc.model.user.Role;
import com.buildingtracker.mvc.model.user.User;
import com.buildingtracker.mvc.repository.user.RoleRepository;
import com.buildingtracker.mvc.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;

    }
    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User findById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sapLogin = authentication.getName();
        return userRepo.findByLogin(sapLogin);
    }

    public List<Role> findAllRoles(){
        return roleRepo.findAll();
    }

    public Role findRoleById(int id){
        return roleRepo.findById(id).orElse(null);
    }

    public void update(User user){
        userRepo.save(user);
    }

    public boolean delete(User user){
        try {
            userRepo.delete(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
