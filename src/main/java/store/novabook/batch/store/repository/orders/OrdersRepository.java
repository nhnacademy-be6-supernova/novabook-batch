package store.novabook.batch.store.repository.orders;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import store.novabook.batch.store.entity.member.Member;
import store.novabook.batch.store.entity.orders.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Orders o WHERE o.member = :member AND o.ordersDate BETWEEN :start AND :end")
	Long sumTotalAmountByMemberAndOrdersDateBetween(@Param("member") Member member, @Param("start") LocalDateTime start,
		@Param("end") LocalDateTime end);
}