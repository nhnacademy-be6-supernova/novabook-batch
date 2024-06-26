package store.novabook.batch.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record CreateMemberCouponAllResponse(List<CreateMemberCouponResponse> memberCouponResponseList) {
}
