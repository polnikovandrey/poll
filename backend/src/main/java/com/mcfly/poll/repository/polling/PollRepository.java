package com.mcfly.poll.repository.polling;

import com.mcfly.poll.domain.polling.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PollRepository extends JpaRepository<Poll, Long> {

    Page<Poll> findByCreatedBy(Long userId, Pageable pageable);
}
