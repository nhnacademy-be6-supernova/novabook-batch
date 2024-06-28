package store.novabook.batch.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.NotNull;
import store.novabook.batch.coupon.entity.CouponTemplate;
import store.novabook.batch.coupon.entity.CouponType;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
	Optional<CouponTemplate> findTopByTypeOrderByCreatedAtDesc(@NotNull CouponType type);
}
