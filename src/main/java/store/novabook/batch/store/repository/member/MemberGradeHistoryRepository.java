package store.novabook.batch.store.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.entity.member.MemberGradeHistory;

public interface MemberGradeHistoryRepository extends JpaRepository<MemberGradeHistory, Long> {
	Optional<MemberGradeHistory> findByMemberId(Long memberId);
}
