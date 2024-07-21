# novabook-batch
<p>
  <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=Spring%20Boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Batch-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2-007396?style=flat-square&logo=H2&logoColor=white"/>
  <img src="https://img.shields.io/badge/Elasticsearch-005571?style=flat-square&logo=Elasticsearch&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
</p>

`novabook-batch`는 다양한 배치 작업을 수행하는 서버입니다.
이 서버는 주기적으로 실행되는 배치 작업을 통해 데이터 처리, 쿠폰 발급, 회원 상태 업데이트, 그리고 Elasticsearch 인덱스 업데이트 등의 작업을 자동으로 처리합니다.


# 주요 기능
`novabook-batch`는 다음과 같은 배치 작업을 제공합니다:

**1. 생일 쿠폰 발급**
  - 설명: 매월 1일에 생일이 있는 회원에게 생일 쿠폰을 발급합니다.
  - 스케줄: 매월 1일 자정에 실행됩니다.
  - 관련 클래스: `BirthdayCouponJobConfig`
    
**2. 회원 등급 업데이트**
 - 설명: 분기마다 회원의 구매 금액에 따라 회원 등급을 업데이트합니다.
 - 스케줄: 매년 1월, 4월, 7월, 10월 1일 자정에 실행됩니다.
 - 관련 클래스: `QuarterlyMemberGradeJobConfig`
   
**3. 휴면 회원 상태 업데이트**
 - 설명: 최근 3개월간 로그인하지 않은 회원을 휴면 상태로 업데이트합니다.
 - 스케줄: 매일 자정에 실행됩니다.
 - 관련 클래스: `MemberStatusUpdateJobConfig`
   
**4. Elasticsearch 인덱스 업데이트**
   - 설명: 상품 정보를 Elasticsearch에 업데이트합니다.
   - 스케줄: 매일 자정에 실행됩니다.
   - 관련 클래스: `ElasticSearchJobConfig`
