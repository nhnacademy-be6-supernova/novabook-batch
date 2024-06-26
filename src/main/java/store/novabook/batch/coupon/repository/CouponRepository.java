package store.novabook.batch.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
