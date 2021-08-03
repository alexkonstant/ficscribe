package org.fawks.ficscribe.repository;

import org.fawks.ficscribe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: UserDetailsRepository
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/1/21
 */
public interface UserDetailsRepository extends JpaRepository<User, String> {
}
