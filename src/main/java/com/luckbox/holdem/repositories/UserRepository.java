package com.luckbox.holdem.repositories;


import org.springframework.data.repository.CrudRepository;

import com.luckbox.holdem.models.User;

/**
 * Created by danielseetoh on 5/5/19.
 */
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
public interface UserRepository extends CrudRepository<User, Long> {
}
