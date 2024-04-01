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

**POST /api/users/token/**
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
