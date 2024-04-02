항해 플러스 3주차 과제 - 서버 구축

# 선정 시나리오
- 콘서트 예약 서비스

# Milestone
- 하루=8h, 8*5일=40h
- 프로젝트 생성 및 기본 설정 : 6h
- 유저 토큰 발급 API : 총 12h
   - 대기열 추가 작업 : 6h
   - 토큰 발급 : 6h
- 토큰 검증 처리 : 6h
- 예약 가능 날짜 조회 API : 3h
- 예약 가능한 좌석 조회 API : 3h
- 잔액 충전 API : 4h
- 잔액 조회 API : 2h
- 좌석 예약 요청 API : 4h

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
        "token": "550e8400-e29b-41d4-a716-446655440000"
    },
    "error": null
}
```

### 예약 가능 날짜 조회 API

**GET /api/reservations**

- request header
   - userUuid+잔여시간을 hmac-sha256 암호화
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- response body
```
{
    "result": ["2024-03-01", "2024-03-12", "2024-03-17"],
    "error": null
}
```

### 예약 가능한 좌석 조회 API

**GET /api/reservations/{reserveDate}**

- request header
   - userUuid+잔여시간을 hmac-sha256 암호화
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- response body
```
{
    "result": [1, 3, 5],
    "error": null
}
```

### 좌석 예약 요청 API

**POST /api/reservations**

- request header
   - userUuid+잔여시간을 hmac-sha256 암호화
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "concertId": 1,
    "reserveDate": "2024-03-01",
    "seatNum": 1
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

**POST /api/point**

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
   - userUuid+잔여시간을 hmac-sha256 암호화
```
token="550e8400-e29b-41d4-a716-446655440000",
```

- request body
```
{
    "userId": 1,
    "reserveId": 1,
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
