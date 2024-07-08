package store.novabook.batch.store.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.entity.member.MemberStatus;

public interface MemberStatusRepository extends JpaRepository<MemberStatus, Long> {
	Optional<MemberStatus> findByName(String name);
}
