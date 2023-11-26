# 블로그 검색 서비스

---

Open API 를 통한 블로그 검색 서비스입니다.
API 는 2가지 입니다.

## 개발 환경

- Language: `Java 17`
- Framework: `Spring boot 3.1.5`
- Database: `H2 (in memory)`

## 다운로드

- [Jar 파일 다운로드](./blog-0.0.1-SNAPSHOT.jar)

## 실행 방법

```sh
$ java -jar blog-0.0.1-SNAPSHOT.jar
```

## Swagger URL

http://localhost:8080/swagger-ui/index.html

### `GET` /blog/search

### Request

| Name     | Type    | Description                                      | Required |
|----------|---------|--------------------------------------------------|----------|
| query    | String  | 쿼리                                               | O        |
| size     | Integer | 문서 수, 기본값 20                                     | X        |
| sort     | String  | 정렬방식, ACCURACY(정확도순), RECENCY(최신순), 기본값 ACCURACY | X        |
| blogType | String  | 조회 블로그 대상, KAKAO, NAVER, 기본값 KAKAO               | X        |
| page     | Integer | 페이지, 기본값 1                                       | X        |
| size     | Integer | 문서 수, 기본값 20                                     | X        |

### Response

| Name      | Type   | Description |
|-----------|--------|-------------|
| page      | int    | 페이지         |
| size      | int    | 조회 요청한 문서 수 |
| totalPage | int    | 전체 페이지수     |
| blogs     | Blog[] | 검색된 문서 List |

#### Blog

| Name      | Type     | Description |
|-----------|----------|-------------|
| blogname  | String   | 블로그 이름      |
| contents  | String   | 블로그 글 요약    |
| title     | String   | 블로그 글 제목    |
| url       | String   | 블로그 글 URL   |
| thumbnail | String   | 이미지 URL     |
| datetime  | Datetime | 블로그 글 작성시간  |

### Sample

#### Request

http://localhost:8080/blog/search?blogType=KAKAO&query=hello&sort=ACCURACY&page=1&size=20

#### Response

```shell
HTTP/1.1 200
Content-Type: application/json
{
  "page": 1,
  "size": 1,
  "totalPage": 800,
  "blogs": [
    {
      "title": "<b>Hello</b> SpringBoot!",
      "contents": "위에 @Controller 어노테이션을 추가해줍니다. @Controller public class HelloWorldController { @GetMapping(&#34;/exam00/<b>hello</b>&#34;) public String <b>Hello</b>(Model model){ return &#34;/exam00/<b>hello</b>.jsp&#34;; } } ✅ 프로퍼티파일에 환경설정 값 추가 한글깨짐 방지를 위해 파일 인코딩을 UTF-8로 설정합니다. 프로퍼티 파일안에...",
      "url": "https://devjunyeok.tistory.com/171",
      "blogName": "Just Do It!",
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/KcNZkOTENHW",
      "dateTime": "2023-10-04 15:47:56"
    }
  ]
}
```

---

### `GET` /blog/hot-keywords

- 검색어 순위 Top 10 을 조회할 수 있습니다.

### Response

| Name  | Type         | Description |
|-------|--------------|-------------|
| items | HotKeyword[] | 검색 데이터      |

#### HotKeyword

| Name    | Type   | Description |
|---------|--------|-------------|
| keyword | String | 검색 단어       |
| count   | String | 검색 수        |

### Sample

#### Request

http://localhost:8080/blog/hot-keywords

#### Response

```shell
HTTP/1.1 200 
Content-Type: application/json
[
  {
    "keyword": "hello",
    "count": 2
  }
]
```