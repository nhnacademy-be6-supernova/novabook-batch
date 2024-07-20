package store.novabook.batch.store.entity.image.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.novabook.batch.store.entity.orders.OrdersBook;

/**
 * 리뷰 정보를 저장하는 엔티티 클래스.
 * 주문과 책에 대한 리뷰를 관리한다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {

	/** 리뷰의 고유 ID. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 리뷰가 참조하는 주문 객체. */
	@NotNull
	@ManyToOne
	@JoinColumn(name = "orders_book_id")
	OrdersBook ordersBook;

	/** 리뷰 내용. */
	@NotNull
	private String content;

	/** 리뷰 점수. */
	@NotNull
	private int score;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

}
