항해 플러스 3주차 과제 - 서버 구축

# 콘서트 예약 서비스

<aside>
💡 아래 명세를 잘 읽어보고, 서버를 구현합니다.

</aside>

## Description

- **콘서트 예약 서비스**를 구현해 봅니다.
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- ~~사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.~~
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

## Requirements

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

## API Specs

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

<aside>
💡 **KEY POINT**

</aside>

- 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
- 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.


# Milestone
- 하루=8h, 8*5일=40h
1. dummy 데이터 생성 : 3h ( ~ 4월7일)
2. 잔액 충전 API : 4h
3. 잔액 조회 API : 3h
4. 예약 가능 날짜 조회 API : 3h
5. 예약 가능한 좌석 조회 API : 3h
6. 좌석 예약 요청 API : 5h

- 유저 토큰 발급 API : 총 12h
   - 대기열 추가 작업 : 6h
   - 토큰 발급 : 6h
- 토큰 검증 처리 : 6h

## Sequence Diagram

### 유저 토큰 발급
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/41048081-2cf3-4a43-9d57-ef8783900de4)

### 예약 가능 날짜 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/cb5ced57-a508-4cb4-abab-73eaf6e56dc1)

### 예약 가능 좌석 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/456bfa53-566c-4779-a116-311e1372be94)

### 좌석 예약 요청
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/6b9acee4-e63a-4f36-bea9-7e5f77d8664f)

### 잔액 충전
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/0f7886df-8842-46b1-a462-980a94c2fe53)

### 잔액 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/f76564c1-061f-47c1-9ced-35e3e251c195)

### 결제
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/94035e4e-d9f4-4ddc-8744-6242f9b90d4e)

## API Interface

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
        "userUuid": "550e8400-e29b-41d4-a716-446655440000"
    },
    "error": null
}
```

### 대기열 조회 API

**GET /api/users/token**

- request header
   
```
userUuid="550e8400-e29b-41d4-a716-446655440000",
```


- response body
```
{
    "result": {
        "waitingCount": 50, // 앞에 대기중인 유저수
        "rank": 15, // 내 순서
        "status": "WAITING" // WAITING이면 대기, IN_PROGRESS면 진입
    },
    "error": null
}
```

### 예약 가능 날짜 조회 API

**GET /api/concert/{concertDetailId}/date**

- request header
   
```
userUuid="550e8400-e29b-41d4-a716-446655440000",
```


- response body
```
{
    "result": ["2024-03-01 11:30:00", "2024-03-12 14:30:00", "2024-03-17 17:30:00"],
    "error": null
}
```

### 예약 가능한 좌석 조회 API

**GET /api/concert/{concertDetailId}/seat**

- request header

```
userUuid="550e8400-e29b-41d4-a716-446655440000",
```

- response body
```
{
    "result": ["S1", "S3", "S5"],
    "error": null
}
```

### 좌석 예약 요청 API

**POST /api/reservations**

- request header
   
```
userUuid="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "concertDetailId": 1,
    "seatNum": "S1"
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

**PATCH /api/point**

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

**GET /api/point/{userId}**

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

**POST /api/pay**

- request header
   
```
userUuid="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "reservationId": 1,
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
