package org.fawks.ficscribe.repository;

import org.fawks.ficscribe.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName:StoryRepository
 * @Description:
 * @Author : Alexander Konstantinov
 * @Create: 20.07.21 16:16
 */
public interface StoryRepository extends JpaRepository<Story, Long> {
}
