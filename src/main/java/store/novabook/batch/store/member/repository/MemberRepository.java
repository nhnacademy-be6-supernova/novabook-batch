package store.novabook.batch.store.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import store.novabook.batch.store.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT m FROM Member m WHERE MONTH(m.birth) = :month AND m.memberStatus.id = :statusId")
	Page<Member> findByBirthMonthAndMemberStatusId(@Param("month") int month, @Param("statusId") long statusId,
		Pageable pageable);
}
