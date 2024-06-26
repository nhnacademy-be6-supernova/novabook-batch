package store.novabook.batch.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.batch.dto.CreateMemberCouponAllRequest;
import store.novabook.batch.dto.CreateMemberCouponAllResponse;

@FeignClient(name = "memberCouponClient", url = "${coupon.service.url}")
public interface CouponAdapter {
	@PostMapping("/api/v1/coupon/members/coupons")
	ResponseEntity<CreateMemberCouponAllResponse> saveMemberCoupon(@RequestBody CreateMemberCouponAllRequest request);
}