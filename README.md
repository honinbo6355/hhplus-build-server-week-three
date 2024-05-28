항해 플러스 3주차 과제 - 서버 구축

# 콘서트 예약 서비스

<details>
<summary><b>Description</b></summary>

- **콘서트 예약 서비스**를 구현해 봅니다.
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- ~~사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.~~
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.
</details>

<details>
    <summary><b>Requirements</b></summary>

- 아래 5가지 API 를 구현합니다.
    - 유저 토큰 발급 API
    - 예약 가능 날짜 / 좌석 API
    - 좌석 예약 요청 API
    - 잔액 충전 / 조회 API
    - 결제 API
- 각 기능 및 제약사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.
- 대기열 개념을 고려해 구현합니다.
</details>

<details>
    <summary><b>API Specs</b></summary>
1️⃣ `**주요` 유저 대기열 토큰 기능**

- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

> 기본적으로 클라이언트가 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.
> 

**2️⃣ `기본` 예약 가능 날짜 / 좌석 API**

- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

> 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
> 

3️⃣ `**주요` 좌석 예약 요청 API**

- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 배정 시간 내에 결제가 완료되지 않았다면 좌석에 대한 임시 배정은 해제되어야 합니다.
- 배정 시간 내에는 다른 사용자는 예약할 수 없어야 합니다.

4️⃣ `**기본`**  **잔액 충전 / 조회 API** ( 대기열 토큰 검증 x )

- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

5️⃣ `**주요` 결제 API**

- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.
</details>

<details>
    <summary><b>💡 KEY POINT</b></summary>

- 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
- 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.
</details>

<details>
    <summary><b>ERD</b></summary>

https://www.erdcloud.com/d/uvDnPHkfF5v7DKF3i

</details>

<!--
<details>
    <summary><b>Milestone</b></summary>

- 하루=8h, 8*5일=40h
1. dummy 데이터 생성 : 3h ( ~ 4월7일) 
2. 잔액 충전 API : 5h ( ~ 4월7일) <br/>
   2-1. API 구현 <br/>
   2-2. 유닛 테스트 <br/>
3. 잔액 조회 API : 4h ( ~ 4월8일) <br/>
   3-1. API 구현 <br/>
   3-2. 유닛 테스트 <br/>
4. 예약 가능 날짜 조회 API : 4h ( ~ 4월8일) <br/>
   4-1. API 구현 <br/>
   4-2. 유닛 테스트 <br/>
5. 예약 가능한 좌석 조회 API : 4h ( ~ 4월9일) <br/>
   5-1. API 구현 <br/>
   5-2. 유닛 테스트 <br/>
6. 좌석 예약 요청 API : 5h ( ~ 4월10일) <br/>
   6-1. API 구현 <br/>
   6-2. 유닛 테스트 <br/>
7. 결제 API : 5h ( ~ 4월11일) <br/>
   7-1. API 구현 <br/>
   7-2. 유닛 테스트 <br/>
8. 유저 토큰 발급 API : 12h ( ~ 4월12일) <br/>
   8-1. API 구현 <br/>
   8-2. 유닛 테스트 <br/>

=============== 다음 주차 =============== </br>
9. 좌석 임시 배정 5분 검증 Polling 구현 <br/> <br/>
10. 잔여 시간 검증 Polling 구현 <br/> <br/>
11. 토큰 유효성 체크 인터셉터 구현 <br/> <br/>
12. 통합테스트 <br/> <br/>
</details>
-->

<details>
    <summary><b>Sequence Diagram</b></summary>

### 유저 토큰 발급
![토큰 발급 API   좌석 예약 요청 API-2024-05-28-121148](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/5e2e18df-731e-427d-b0c5-a6b7d08eeb35)

### 유저 토큰 조회
![결제 API   토큰 조회 API-2024-05-28-122813](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/65b99eb8-92cf-4191-83b0-b0ace582dde9)

### 예약 가능 날짜 조회
![예약 가능 날짜 조회 API   예약 가능 좌석 조회 API-2024-05-28-122950](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/dcc8557d-3fcc-4303-ae49-e5f19063eaeb)

### 예약 가능 좌석 조회
![예약 가능 날짜 조회 API   예약 가능 좌석 조회 API-2024-05-28-123025](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/0a8af15c-3ffa-4b71-bd36-92e556755674)

### 좌석 예약 요청
![토큰 발급 API   좌석 예약 요청 API-2024-05-28-123120](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/3eff3a19-6cec-4dbd-935e-8752331ab45b)

### 잔액 충전
![잔액 충전 API   잔액 조회 API-2024-05-28-123205](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/b7310a13-d19c-4829-809d-0384c34f1db1)

### 잔액 조회
![잔액 충전 API   잔액 조회 API-2024-05-28-123229](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/60727594-ee86-4181-bb10-b5af5fc51124)

### 결제
![결제 API   토큰 조회 API-2024-05-28-123320](https://github.com/honinbo6355/reservation-in-microservice/assets/29749722/e0e7afdf-79ee-445c-ad10-18b9e40339f6)

</details>

<details>
    <summary><b>Swagger</b></summary>
    
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/1747b6af-93d0-41a6-8998-2288b8394ee8)

</details>

<details>
    <summary><b>트러블 슈팅</b></summary>

- 각각의 콘서트에 대한 좌석 정보를 갯수만큼 row를 생성하는 방식이 비효율적이라고 생각했습니다.
  - 해결 : 예약 테이블에 콘서트 상세 아이디와 좌석 아이디 컬럼을 관리하게했고, 예약 가능한 좌석 조회시에는 (전체 좌석 - 예약되어있는 좌석) 과정을 통해 예약 가능한 좌석들을 추출할 수 있었습니다.
- 한 좌석에 대해 동시에 예약 요청이 들어올 경우 동시성 해결하는 방법에 대해 고민했습니다.
  - 해결 : 예약 테이블에 콘서트 상세 아이디와 좌석 아이디를 유니크키로 생성해 중복 예약이 불가능하도록 처리했습니다.
- 토큰과 대기열 테이블을 하나로 관리하는 경우 진입 여부 조회시 동시성 이슈가 발생할거라고 판단했습니다.
  - 해결 : 토큰 테이블(사이트 진입 가능 유저)와 대기열 테이블(대기 중인 유저)를 분리했습니다. 그리고 Polling 작업을 통해 토큰 여유공간에 따라 대기 중인 유저들을 토큰 테이블로 옮기는 프로세스로 처리했습니다.
</details>

<details>
    <summary><b>브랜치 전략</b></summary>

- dev : 개발자가 자유롭게 테스트할 수 있는 개발 환경
- stage : prod 배포전 테스트해볼 수 있는 환경
- prod : 운영 환경
- feature : 기능별 개발 브랜치

</details>

<details>
    <summary><b>API Interface</b></summary>

### 유저 토큰 발급 API

**POST /api/users/token**
- request body
```
{
    "userId": 1
}
```

- response body
```
{
    "result": {
        "token": "550e8400-e29b-41d4-a716-446655440000" // or null(대기열에 추가된경우)
    },
    "error": null
}
```

### 유저 토큰 조회 API

**GET /api/users/{userId}/token**

- response body
```
{
    "result": {
        "rank": 15, // or null(발급된경우)
        "token": "550e8400-e29b-41d4-a716-446655440000", // or null(대기열에 추가된경우)
        "status": "WAITING" // WAITING이면 대기, IN_PROGRESS면 진입, FINISHED면 만료
    },
    "error": null
}
```

### 예약 가능 날짜 조회 API

**GET /api/concert/{concertId}/date**

- request header
   
```
token="550e8400-e29b-41d4-a716-446655440000",
```


- response body
```
{
    "result": {
        "concertName": "아이유 콘서트",
        "concertInfos": [
            {
                "concertDetailId": 1,
                "startsAt": "2024-03-01 11:30:00"
            },
            {
                "concertDetailId": 2,
                "startsAt": "2024-03-12 14:30:00"
            },
            {
                "concertDetailId": 3,
                "startsAt": "2024-03-17 17:30:00"
            }
         ]
    },
    "error": null
}
```

### 예약 가능한 좌석 조회 API

**GET /api/concert/{concertDetailId}/seat**

- request header

```
token="550e8400-e29b-41d4-a716-446655440000",
```

- response body
```
{
    "result": [1, 2, 3],
    "error": null
}
```

### 좌석 예약 요청 API

**POST /api/reservations**

- request header
   
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "concertDetailId": 1,
    "userId": 1,
    "seatId": 1
}
```

- response body
```
{
    "result": "SUCCESS",
    "error": null
}
```

### 잔액 충전 API

**PATCH /api/users/point**

- request body
```
{
    "userId": 1,
    "point": 5000
}
```

- response body
```
{
    "result": "SUCCESS",
    "error": null
}
```

### 잔액 조회 API

**GET /api/users/{userId}/point**

- response body
```
{
    "result": {
        "userId": 1,
        "point": 5000
    },
    "error": null
}
```

### 결제 API

**POST /api/payment**

- request header
   
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "reservationId": 1,
    "userId": 1,
    "point": 5000
}
```

- response body
```
{
    "result": "SUCCESS",
    "error": null
}
```
</details>

<details>
    <summary><b>동시성 해결 전략</b></summary>

- 해결 전략 종류
  - 비관적 락
     - 트랜잭션의 충돌이 빈번하다고 판단되는 경우에 주로 사용한다. 레코드에 락을 걸어서 여러 트랜잭션이 동시에 접근하는 것을 방지할 수 있다.
       데이터 정합성을 보장할 수 있지만 성능이 떨어지는 문제가 있다. 그리고 대기 상태가 길어지면 데드락에 빠질 위험이 있다.
  - 낙관적 락
     - 비관적 락에 비해 상대적으로 충돌이 적다고 판단될 경우 사용할 수 있다. DB에 직접 Lock을 거는 방식이 아니기 때문에 성능에는 유리할 수 있다.
       그렇지만 충돌이 발생할 경우 재시도 로직을 어플리케이션 계층에 직접 추가해야 한다. 그리고 무한정 재시도를 시도할 경우 무한 루프에 빠질 위험이 있고
       재시도 횟수를 정하는 것도 유의해야 한다.
  - redis 분산락
     - redis의 싱글 스레드의 특징을 이용하는 전략이다. 다수의 트랜잭션이 동시에 접근하더라도 순차적으로 처리하기 때문에 동시성 문제를 막을 수 있다.
       그리고 DB까지 접근하는 것을 애초에 막을 수 있기 때문에 성능에도 유리하다. 하지만 레디스 서버를 구축해야 되는 수고로움이 필요하고 혹여나 레디스 서버가
       장애가 생길 경우 전체 시스템에 문제가 발생할 수 있다.
  - kafka
     - Queue 구조이기 때문에 순차적으로 처리가 가능하다. 그리고 DB 락과 redis 분산락을 사용하지 않아도 되니 성능에도 유리할 수 있다. 그렇지만 단점도 존재한다.
       컨슈머가 1대일 경우는 문제없지만 여러대가 추가될 경우 동시성 문제를 어떻게 해결해야할지 고민이 필요하다. 그리고 러닝커브가 꽤 존재한다.
       DB 락이나 redis 분산락으로 처리해보고 더 이상 견딜 수 없는 트래픽일 경우 kafka를 추가로 도입해보는게 베스트인 것 같다.
       
- 동시성 처리 케이스1 : 유저 포인트 잔액
   - 해결 방법 : DB 낙관적 락
   - 이유 : 유저 포인트에서 동시성 문제가 발생하는 경우는 멀티 디바이스에서 충전과 사용을 동시에 진행할 때이다. 이러한 경우는 극히 드물다고 생각되기 때문에
           낙관적 락을 활용해 처리하는게 효과적일 것 같다.
- 동시성 처리 케이스2 : 좌석 예약
   - 해결 방법 : redis 분산락
   - 이유 : 현재 상태에서는 unique key를 활용해서 동시성을 제어하고 있다. DB에서 예외를 발생시키는 것보다 redis에서 락을 잡아서 처리하는게 성능적으로
           나을거라고 판단했다.
</details>

<details>
    <summary><b>Query 분석 및 DB Index 설계</b></summary>

- slow query 예상 시나리오 : DB Index 성능 분석을 위해 극단적인 상황을 가정하였다. 토큰 발급 요청이 500만건이 발생했을경우, 토큰 조회시 내 대기순서가 몇번인지 조회하는 쿼리에서 slow query가 발생할거라고 판단했다.
- Index 추가 시도 : reservation_queue 테이블에는 유저 1명당 하나의 고유한 대기열을 가질 수 있다. 카디널리티가 높은 컬럼을 인덱스로 생성하면 효과를 극대화 할 수 있다고 판단했고, user_id를 unique_key로 생성했다.
- 성능 테스트 내용
   - 실행 쿼리
      ```sql
      select count(*) from test.reservation_queue where status = 'WAITING' and updated_at < (select updated_at from test.reservation_queue where user_id = 15)
      ```
   - 인덱스 추가 전 결과
      - <img width="210" alt="image" src="https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/f7dee556-b167-4c0b-bdcd-241f3eacf1fc"> <br />
   - 인덱스 추가한 쿼리
       ```sql
       ALTER TABLE test.reservation_queue ADD CONSTRAINT reservation_queue_user_id_uk UNIQUE(user_id);
       ```
   - 인덱스 추가 후 결과
      - <img width="193" alt="image" src="https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/b9439f4a-9eb3-4fcf-8009-6a9e863058da">
   - 결론
      - 데이터량이 크면 클수록 인덱스 효과가 극대화 되고, 카디널리티가 낮은 컬럼은 인덱스로 생성해도 큰 효과가 없다는걸 알게되었다. 결론적으로 user_id를 인덱스로 추가해 쿼리 성능을 2배 개선하였다.
</details>

<details>
    <summary><b>대기열 시스템 설계 리팩토링</b></summary>

- 주요 작업 : 기존에 DB로 구현했던 대기열 시스템을 Redis로 전환
- 대기열 토큰 설계
   - WaitingQueue(대기열 큐) : Redis SortedSet 자료구조의 score값을 활용해서 순번을 정한다. 처음 진입하는 유저는 대기열 큐에 저장되고, 이미 대기중인 유저는 기존 순번을 유지한다.
   - OngoingToken(활성화 토큰) : Redis Set 자료구조로 진입 가능한 유저들이 존재하는 곳이다.
- 주요 프로세스
   - N명의 유저들이 토큰 발급 API 요청시 대기열 큐에 N명 저장
   - 특정 시간마다 Polling 작업을 통해 대기열 큐에서 활성화 토큰 자료구조로 이동시키는 로직 처리(ex : 최대 토큰 발급 유저 100명, 현재 토큰 발급 유저가 90명이면 대기열 큐에서 유저 10명을 토큰 자료구조로 이동)
   - 만료시간이 지난 토큰은 Polling 작업을 통해 삭제 처리.(Set 자료구조의 member 단위로 TTL을 설정해 삭제시키려고 했지만, Redis에서는 지원하지않아서 시도 못함..)
- 프로세스 설계 이유
   - 최대 토큰 발급 유저수를 정함으로써 트래픽의 최대치가 예측 가능하다.
   - 비어있는 토큰 발급수만큼 대기열 유저가 채워지기 때문에 해당 유저의 대기 순위가 유의미한 효과가 있다.
</details>

<details>
    <summary><b>이벤트 기반 트랜잭션 처리 전략</b></summary>
    https://honinbo-world.tistory.com/108
</details>

