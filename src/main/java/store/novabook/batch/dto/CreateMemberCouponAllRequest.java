package store.novabook.batch.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record CreateMemberCouponAllRequest(List<Long> memberIdList, String couponCode, LocalDateTime expirationAt) {
}
