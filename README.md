# hankki-server

# 👋 ABOUT HANKKI
<로고들어갈예정> <br>
**`WHAT ?`**  대학교를 중심으로 **8000원 이하의 메뉴를 가진 식당**을 제공하는 프로덕트 

**`HOW?`** 서울 내의 대학 묶음 9-10개를 중심으로 50개씩 약 500개의 식당을 모아 릴리즈하고, 추후 **유저참여형**으로 운영할 계획 

**`WHY?`** 치솟는 물가에 밥 한끼 먹기도 부담되는 요즘, 저렴한 식당을 공유하며 **함께** **잘 먹고 잘 살기 위해서!**

# ⚒️Stack 

<div align = "center">
  <img src="https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1720497281719?alt=media&token=f40aa73a-b50e-43cc-bd2a-a49e3d62086d)](https://github.com/msdio/stackticon">
</div>

# 📍ERD
![hankki](https://github.com/Team-Hankki/hankki-server/assets/109809242/36c645b6-68e6-42b8-a9e2-9ddf790a6f4f)

# 📜 Covention
## Code Convetion
| 항목                | 규칙                                                                                  |
|---------------------|---------------------------------------------------------------------------------------|
| `Class`         | **PascalCase**          |
| `Function`              | **camelCase**                                                                         |
| `Variable`                | **camelCase**                                                                         |
| `DB Table`           | **snake_case**                                                                        |
| `ENUM`, `Contatant`          | **PascalCase**                                                                        |
| `Collection`  | 리스트는 복수형, 다른 컬렉션은 접미사로 컬렉션. (Ex. **users**, userMap) |
| `LocalDateTime`       | 접미사에 **Date 혹은 At**.                                                 |

## ♣️Git Convention
### prefix
| type         | what                  |
|--------------|-----------------------|
| `feat`       | 구현                  |
| `fix`        | 수정                  |
| `refac`      | 리팩토링              |
| `chore`      | 패키지 구조 수정      |
| `docs`       | 문서 수정             |
| `infra`      | 인프라 관련 작업      |
| `hotfix`     | 운영 서버 핫픽스 작업 |
|'dependency'  | 의존성(아마 의존성만 추가하는 작업을 없을 것으로 예상되어 쓸 일은 없을듯)|

### Branch Naming
`<Prefix>/<Issue_Number>`

### Commit Message 
`[<Prefix>] <Description>`

### Pull Request
title : `[<Prefix>] <Description>`
```Markdown
## Related Issue 📌
close #<issue_num>

## Description ✔️
- 

## To Reviewers
```
### Review
| PN| what|
|------|-----|
|P1 | 꼭 반영해주세요 (Request changes) |
|P2 | 적극적으로 고려해주세요 (Request changes) |
| P3| 웬만하면 반영해 주세요 (Comment) |
| P4 | 반영해도 좋고 넘어가도 좋습니다 (Approve) |
|P5 | 그냥 사소한 의견입니다 (Approve) |

# 🤼‍♀️Team Member

| [박재연👑](https://github.com/Parkjyun) | [김가연🌱](https://github.com/kgy1008) | [박서진🌱](https://github.com/PicturePark1101)|
|--------|------- |---------- |
| <img width="600px" src= "https://github.com/Team-Hankki/hankki-server/assets/109809242/2276da80-d520-4548-8064-8011fb9e92a7"> | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/cb7addf0-a6ca-41f4-b2cc-7cb1a8a480d3">  | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/7d62d5c9-9b8d-4e43-a118-0ad4e69fb564"> |
| ✔️Server 리드 <br> ✔️DB 설계<br> ✔️홈화면 API  | ✔️소셜로그인(KAKAO, APPLE) <br> ✔️식당 API| ✔️마이페이지 API <br> ✔️족보 API |

