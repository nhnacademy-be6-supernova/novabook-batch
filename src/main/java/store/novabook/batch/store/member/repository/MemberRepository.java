package store.novabook.batch.store.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
