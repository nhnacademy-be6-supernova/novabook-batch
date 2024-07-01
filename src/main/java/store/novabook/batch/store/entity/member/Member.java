package store.novabook.batch.store.entity.member;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String loginId;

	@NotNull
	private String loginPassword;

	@NotNull
	private String name;

	@NotNull
	private String number;

	@NotNull
	private String email;

	@NotNull
	private LocalDateTime birth;

	@NotNull
	private LocalDateTime latestLoginAt;

	@NotNull
	private int authentication;

	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "member_status_id")
	private MemberStatus memberStatus;

	@Builder
	public Member(String loginId,
		String loginPassword,
		String name,
		String number,
		String email,
		LocalDateTime birth,
		LocalDateTime latestLoginAt,
		int authentication,
		MemberStatus memberStatus) {
		this.loginId = loginId;
		this.loginPassword = loginPassword;
		this.name = name;
		this.number = number;
		this.email = email;
		this.birth = birth;
		this.latestLoginAt = latestLoginAt;
		this.authentication = authentication;
		this.memberStatus = memberStatus;
	}
}
