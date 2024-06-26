package store.novabook.batch.coupon.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CouponTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 255)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CouponType type;

	@NotNull
	private long discountAmount;

	@Enumerated(EnumType.STRING)
	@NotNull
	private DiscountType discountType;

	@NotNull
	private long maxDiscountAmount;

	@NotNull
	@Min(0)
	private long minPurchaseAmount;

	@NotNull
	private LocalDateTime startedAt;

	@NotNull
	private LocalDateTime expirationAt;

	@NotNull
	private int usePeriod;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@Builder
	public CouponTemplate(String name, CouponType type, long discountAmount, DiscountType discountType,
		long maxDiscountAmount, long minPurchaseAmount, LocalDateTime startedAt, LocalDateTime expirationAt,
		int usePeriod) {
		this.name = name;
		this.type = type;
		this.discountAmount = discountAmount;
		this.discountType = discountType;
		this.maxDiscountAmount = maxDiscountAmount;
		this.minPurchaseAmount = minPurchaseAmount;
		this.startedAt = startedAt;
		this.expirationAt = expirationAt;
		this.usePeriod = usePeriod;
	}

}
