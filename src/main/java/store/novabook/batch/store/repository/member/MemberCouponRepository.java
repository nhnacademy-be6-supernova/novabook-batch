package store.novabook.batch.store.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.entity.member.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
