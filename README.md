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

# ERD
https://private-user-images.githubusercontent.com/29749722/319140201-a8f9c6f4-f701-4b0d-9649-604e192b7f8d.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTIxMzk0MTksIm5iZiI6MTcxMjEzOTExOSwicGF0aCI6Ii8yOTc0OTcyMi8zMTkxNDAyMDEtYThmOWM2ZjQtZjcwMS00YjBkLTk2NDktNjA0ZTE5MmI3ZjhkLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA0MDMlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNDAzVDEwMTE1OVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTgxYTRiMTkzMzI5OWYyYjZmMmI1NmY5ZjA4YTQxZGQ1MzE0OGUyMTA3YmYxMzczYzkzNTE2MGE5ZWM2Yzk2MjYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.zs_Cr3CpnNZRAzupc8TiltTRxnMAhD5oR52tG2cLLM0

# Milestone
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
7. 유저 토큰 발급 API : 12h ( ~ 4월12일) <br/>
   7-1. API 구현 <br/>
   7-2. 유닛 테스트 <br/>

=============== 다음 주차 =============== </br>
8. 좌석 임시 배정 5분 검증 Polling 구현 <br/> <br/>
9. 잔여 시간 검증 Polling 구현 <br/> <br/>
10. dummy 대기열 추가 Polling 구현 <br/> <br/>
11. 통합테스트 <br/> <br/>

## Sequence Diagram

### 유저 토큰 발급
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/be43a603-ff2e-44c4-9a01-68dbe47de1ef)

### 유저 토큰 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/640d697a-ecb7-45b8-aaa1-73efa0996bef)

### 예약 가능 날짜 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/cf9a972e-26b6-4b24-a4c7-ca41b4e039d1)

### 예약 가능 좌석 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/a3e0e445-56c6-44ca-8aa1-446ebc0597cf)

### 좌석 예약 요청
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/f9a63529-1037-4cf8-a47a-fce43688bf8e)

### 잔액 충전
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/76e2e38c-8a75-43a9-b45d-b97b9deb9b13)

### 잔액 조회
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/5f1037ae-0973-4435-86f3-00536e2a7abd)

### 결제
![image](https://github.com/honinbo6355/hhplus-build-server-week-three/assets/29749722/9f38d5ec-1e17-475b-965a-f078ac952ef6)

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

### 유저 토큰 조회 API

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
