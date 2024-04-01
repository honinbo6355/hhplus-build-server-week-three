# hhplus-build-server-week-three
항해 플러스 3주차 과제 - 서버 구축

# 선정 시나리오
- 콘서트 예약 서비스

# Milestone
- 하루=8h, 8*5일=40h
- 프로젝트 생성 및 기본 설정 : 4h
- 유저 토큰 발급 API : 16h
- 예약 가능 날짜 조회 API : 2h
- 예약 가능한 좌석 조회 API : 2h
- 잔액 충전 API : 3h
- 잔액 조회 API : 2h
- 좌석 예약 요청 API : 11h


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
        "userUuid": "550e8400-e29b-41d4-a716-446655440000",
        "rank": 5,
        "remainingTime": "5분 30초"
    }
    "error": null
}
```

### 예약 가능 날짜 조회 API

**GET /api/reservations**
- request header
```
{
    "userUuid": "550e8400-e29b-41d4-a716-446655440000",
    "rank": 5,
    "remainingTime": "5분 30초"
}
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
```
{
    "userUuid": "550e8400-e29b-41d4-a716-446655440000",
    "rank": 5,
    "remainingTime": "5분 30초"
}
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
```
{
    "userUuid": "550e8400-e29b-41d4-a716-446655440000",
    "rank": 5,
    "remainingTime": "5분 30초"
}
```

- request body
```
{
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
```
{
    "userUuid": "550e8400-e29b-41d4-a716-446655440000",
    "rank": 5,
    "remainingTime": "5분 30초"
}
```

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
