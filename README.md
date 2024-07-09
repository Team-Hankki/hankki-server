# hankki-server

# 👋 ABOUT HANKKI

 <div align = "center">
  <img witdth= "600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/275fabbb-b1c1-4589-b9e0-ec6b870dc401">
</div>
 
**`WHAT?`**  대학교를 중심으로 **8000원 이하의 메뉴를 가진 식당**을 제공하는 프로덕트 

**`HOW?`** 서울 내의 대학 묶음 9-10개를 중심으로 50개씩 약 500개의 식당을 모아 릴리즈하고, 추후 **유저참여형**으로 운영할 계획 

**`WHY?`** 치솟는 물가에 밥 한끼 먹기도 부담되는 요즘, 저렴한 식당을 공유하며 **함께** **잘 먹고 잘 살기 위해서!**

<br>

# 🤼‍♀️Team Member

| [👑박재연👑](https://github.com/Parkjyun) | [🌱김가연🌱](https://github.com/kgy1008) | [🌱박서진🌱](https://github.com/PicturePark1101)|
|--------|------- |---------- |
| <img width="600px" src= "https://github.com/Team-Hankki/hankki-server/assets/109809242/2276da80-d520-4548-8064-8011fb9e92a7"> | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/cb7addf0-a6ca-41f4-b2cc-7cb1a8a480d3">  | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/7d62d5c9-9b8d-4e43-a118-0ad4e69fb564"> |
| ✔️CI/CD 구축<br>✔️DB 설계 <br>✔️홈화면 API <br>✔️제보 API  | ✔️소셜로그인(KAKAO, APPLE) <br> ✔️식당 API <br> ✔️Discord 알림 구현| ✔️마이페이지 API <br> ✔️족보 API |

# ⚒️Stack 

<div align = "center">
  <img src="https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1720497281719?alt=media&token=f40aa73a-b50e-43cc-bd2a-a49e3d62086d)](https://github.com/msdio/stackticon">
</div>

<br><br>
# 📍ERD
![hankki (1)](https://github.com/Team-Hankki/hankki-server/assets/109809242/20ea9569-ac38-4c28-a62d-3b616384793b)

<br><br>
# 📜 Covention
## Code Convetion
| 항목                | 규칙                                                                                  |
|---------------------|---------------------------------------------------------------------------------------|
| `Class`         | **PascalCase**          |
| `Function`              | **camelCase**                                                                         |
| `Variable`                | **camelCase**                                                                         |
| `DB Table`           | **snake_case**                                                                        |
| `ENUM`, `Contatant`          | **PascalCase**                                                                        |

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
| `dependency `  | 의존성 |

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
<br><br>
# 🗂️ Package
```
├── 🗂️ Dockerfile-dev
├── 🗂️ server-yml
└── 🗂️ org.hankki.hankkiserver
│   └── 🗂️ hankkiserver
│       ├── 💽 HankkiserverApplication
│       │   ├── 🗂️ api
│       │   │   ├── 📂 DomainA
│       │   │   │   ├── controller
│       │   │   │   │   └── request
│       │   │   │   └── service
│       │   │   │       └── response
│       │   │   │       └── commmand
│       │   ├── 🗂️ auth
│       │   │   ├── 📂 config
│       │   │   ├── 📂 filter
│       │   │   └── 📂 jwt
│       │   ├── 🗂️ common
│       │   ├── 🗂️ domain
│       │   │   ├── 📂 common
│       │   │   ├── 📂 config
│       │   │   ├── 📂 DomainA
│       │   │   │   ├── model
│       │   │   │   └── repository
│       │   └── 🗂️ external
│       │       ├── 📂 config
│       │       └── 📂 openfeign
│       │            ├── apple
│       │            └── kakao

```
<br><br>
