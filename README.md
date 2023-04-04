# 모션랩스 백엔드 사전 과제

## 사용한 기술
- SpringBoot 2.7.5
- Java 11
- H2 DB
- JPA
- QueryDsl
- Restdocs
- RestAssured

## 빌드 및 실행방법

```shell
$ cd motionlabs-assignment/

# 빌드
$ chmod +x gradlew
$ ./gradlew build
$ cd build/libs

# 실행
$ java -jar motionlabs-assignment-0.0.1-SNAPSHOT.jar
```
### API 문서

### [API 문서 링크](https://seokho-ham.github.io/motionlabs-assignment/src/main/resources/static/docs/index.html)

### 요구사항 분석
<details>
<summary>펼치기/접기</summary>
<div markdown="1">

- **월경 주기 등록**
    - 유저별로 월경 주기 정보를 담는 테이블을 둬야한다.
    - 주기와 기간의 최대 범위를 지정해두어야 한다.
- **월경 기록 등록**
    - 실제 월경 기록을 등록할 수 있어야 한다.
    - **반드시 월경주기 등록이 되어 있는지 먼저 검사해야 한다.**
    - 시작일 검사가 필요하다 → ***반드시 현재날짜 이전으로만***
        - 종료일은 시작일 기준으로 주기 등록 정보를 가져와서 자동으로 설정한다.
    - 평균 월경 주기, 기간을 재계산 해서 업데이트해야한다.
- **월경 기록 삭제**
    - 삭제된 이후 평균 월경 주기, 기간 재계산해서 업데이트
    - soft delete 처리
- **월경 기록 전체 조회**
    - 전체 데이터를 제공한다.
        - 페이징은 처리하지 않는다.
    - 최신 기록 기준으로 3개월의 예상 월경, 배란기 정보를 제공해야한다.
    - 기록과 예상 정보는 각각 표시되는 형태가 다르기 때문에 해당 정보도 제공해야한다.

</div>
</details>

### 테스트 결과 (총 39개)

<details>
<summary>문서 테스트</summary>
<div markdown="1">
<img src="https://user-images.githubusercontent.com/57708971/229692671-0946be10-cf6c-4fe9-9c36-c82fa12b934e.png" width="450"/>
</div>
</details>

<details>
<summary>통합 테스트</summary>
<div markdown="1">
<img src="https://user-images.githubusercontent.com/57708971/229692681-1f901204-bcfd-41b2-94e5-cd55e571fb7f.png"/>
</div>
</details>

<details>
<summary>단위 테스트</summary>
<div markdown="1">
<img src="https://user-images.githubusercontent.com/57708971/229692691-b1dbcbe5-dbd0-40e1-af55-1f061fa8c8bf.png" width="450"/>
</div>
</details>

### ERD

<img src="https://user-images.githubusercontent.com/57708971/229665620-e1d99f0e-0726-4713-8dbd-fa5981855868.png" width="550"/>

## 구현사항

- 기능 단위로 Issue를 등록한 뒤, 각각의 브랜치를 만들어 작업 후 PR을 통해 main 브랜치에 머지하는 방식으로 개발을 진행했습니다.
- github actions를 사용하여 머지하기 전 테스트를 자동화 하였습니다.

###  RestDocs를 사용한 문서화
- API 문서를 제공하기 위해 Restdocs를 사용하였습니다.
- 문서테스트를 작성하여 API 문서를 작성함과 동시에 해당 API에 대한 E2E 테스트를 진행하였습니다.

### 테스트 작성
- 각 기능마다 문서테스트와 통합테스트를 작성했습니다.
- 외부에 의존하고 있는 부분이 DB를 제외하고는 없어서 서비스레이어에 대한 테스트를 통합테스트로 대체했습니다.
- 도메인 객체에 대해 부분적으로 테스트를 작성했습니다.
- DatabaseCleaner 클래스를 만들어 테스트 환경 격리를 진행했습니다.

### 연관관계
- 회원과 회원의 월경 주기는 1:1 관계를 갖습니다.
- 회원과 회원의 월경 기록는 1:N 관계를 갖습니다.
- 현재는 서비스의 복잡도가 낮아 직접참조를 사용하였습니다.

### 공통 응답 / 예외 처리 
- ResponseEntity를 상속받는 CustomResponseEntity를 작성하였습니다.
  - 응답 메세지를 공통된 Enum 클래스에서 관리하였습니다.
- RuntimeException을 상속받는 CustomException을 작성하였습니다.

## 고민사항

### 유저의 월경 주기 업데이트에 대한 예외 케이스
- 유저가 월경 기록을 추가하거나 삭제할 경우 유저의 월경 주기의 평균을 업데이트 해야 합니다.
- 등록 된 유저의 최신 6개월간 기록을 조회 후 평균 월경 주기를 업데이트 하도록 하였습니다.
  - 평균 월경 주기의 최대값을 45일, 최소값을 20일로 설정하였습니다.
  - 유저가 자신의 월경기록을 꾸준 등록하지 않을경우를 방지하여 계산한 평균 월경 주기가 허용 범위를 넘어갈 경우 업데이트 하지 않도록 하였습니다.
  - 월경 기록이 2개 미만일 경우 기존의 주기를 유지하도록 하였습니다. 
- 현재 요구사항에서는 기록을 등록할때 기존의 주기와 기간을 사용하여 종료일을 설정합니다.
  - ***사용자의 실제 월경 기간을 업데이트하려면 실제 기간에 대한 정보가 필요하다고 판단하여 현재는 기간을 업데이트 하지 않고 있습니다.***
 