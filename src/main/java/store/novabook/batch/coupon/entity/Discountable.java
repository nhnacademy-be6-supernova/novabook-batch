package store.novabook.batch.coupon.entity;

public interface Discountable {
	long getDiscountAmount();

	DiscountType getDiscountType();
}
