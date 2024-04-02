package suren.campusmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import suren.campusmate.model.User;

public interface UserRepo extends JpaRepository<User, Long>
{
    User findByEmail(String username);
}

