package store.novabook.batch.store.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.entity.member.MemberGradePolicy;

public interface MemberGradePolicyRepository extends JpaRepository<MemberGradePolicy, Long> {
}
