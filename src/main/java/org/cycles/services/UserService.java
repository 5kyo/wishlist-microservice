package org.cycles.services;

import io.quarkus.elytron.security.common.BcryptUtil;

import org.cycles.dto.UserDto;
import org.cycles.entites.User;
import org.cycles.repositories.ProductRepository;
import org.cycles.repositories.UserRepository;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {
    
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Uni<List<User>> getAllUsers(){
        return userRepository.listAll(Sort.by("userName"));
    }

    @Transactional
    public Uni<User> getSingleUser(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public Uni<Response> createUser(UserDto userDto){
        if (userDto == null || userDto.getUserId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setUserSurname(userDto.getUserSurname());
        user.setUserNickname(userDto.getUserNickname());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(BcryptUtil.bcryptHash(userDto.getUserPassword()));
        user.setUserPhoneNumber(userDto.getUserPhoneNumber());
        user.setUserRole(userDto.getUserRole());
        user.setUserActive(userDto.getUserActive());

        return userRepository.persistAndFlush(user)
                    .replaceWith(Response.ok(user).status(Response.Status.CREATED)::build);
    }

    @Transactional
    public Uni<Response> deleteUser(Long id){
        return userRepository.deleteById(id)
            .call(() -> productRepository.flush())
            .replaceWith(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    @Transactional
    public Uni<Response> updateUser(Long userId, UserDto userDto){
        if(userDto == null){
            throw new WebApplicationException("User was not send on request.", 422);
        }
        return userRepository.findById(userId)
                                // .onItem()
                                // .transform(entity -> Response.ok(entity).build()
                        .onItem()
                        .ifNotNull()
                        .invoke(user -> {
                            user.setUserName(userDto.getUserName());
                            user.setUserSurname(userDto.getUserSurname());
                            user.setUserNickname(userDto.getUserNickname());
                            user.setUserEmail(userDto.getUserEmail());
                            user.setUserPassword(BcryptUtil.bcryptHash(userDto.getUserPassword()));
                            user.setUserPhoneNumber(userDto.getUserPhoneNumber());
                            user.setUserRole(userDto.getUserRole());  
                        })
                        .call(() -> userRepository.flush())
                        .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                        .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);
        

        // return userRepository.flush(user)
        //             .replaceWith(Response.ok(user).status(Response.Status.ACCEPTED)::build);
    }

    // public UserDto mapUserToDto(User user){
    //     System.out.println(user.getUserId());
    //     return UserDto.builder()
    //             .userId(user.getUserId())
    //             .userActive(user.getUserActive())
    //             .userName(user.getUserName())
    //             .userNickname(user.getUserNickname())
    //             .userEmail(user.getUserEmail())
    //             .userRole(user.getUserRole())
    //             .userSurname(user.getUserSurname())
    //             .userPassword(user.getUserPassword())
    //             .userPhoneNumber(user.getUserPhoneNumber())
    //             .build();
    // }
    
}
