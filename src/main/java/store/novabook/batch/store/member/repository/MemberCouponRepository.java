package store.novabook.batch.store.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.member.entity.MemberCoupon;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
