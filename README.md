# hankki-server

# 👋 ABOUT HANKKI

<div align="center">
  <img witdth= "200px" src=https://github.com/Team-Hankki/hankki-server/assets/109809242/ce261e93-5fde-48dc-a4bc-12a17fc6f91e"/>

**`WHAT?`**  대학교를 중심으로 **8000원 이하의 메뉴를 가진 식당**을 제공하는 프로덕트

**`HOW?`** 서울 내의 대학 묶음 9-10개를 중심으로 50개씩 약 500개의 식당을 모아 릴리즈하고, 추후 **유저참여형**으로 운영할 계획

**`WHY?`** 치솟는 물가에 밥 한끼 먹기도 부담되는 요즘, 저렴한 식당을 공유하며 **함께** **잘 먹고 잘 살기 위해서!**

</div>
<br><br>

# 🤼‍♀️Team Member

| [👑박재연👑](https://github.com/Parkjyun) | [🌱김지안🌱](https://github.com/kgy1008) | [🌱박서진🌱](https://github.com/PicturePark1101)|
|--------|------- |---------- |
| <img width="600px" src= "https://github.com/Team-Hankki/hankki-server/assets/109809242/2276da80-d520-4548-8064-8011fb9e92a7"> | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/cb7addf0-a6ca-41f4-b2cc-7cb1a8a480d3">  | <img width="600px" src="https://github.com/Team-Hankki/hankki-server/assets/109809242/7d62d5c9-9b8d-4e43-a118-0ad4e69fb564"> |

# ⚒️Stack

<div align = "center">
  <img src="https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1737068021699?alt=media&token=b8c8583c-65b0-4111-b9d5-7dcba0c3e065)](https://github.com/msdio/stackticon">
</div>


# 🏗️Architecture
![한끼아키텍쳐](https://github.com/user-attachments/assets/3ed4c337-3e4f-426b-999e-8f1c0c796cd8)

<br><br>
# 📍ERD
![hankki-DB](https://github.com/user-attachments/assets/03b5a06f-e938-4f6b-90d4-68c383962c8d)

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
│       │   │   ├── 📂 advice
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
│       │   │   ├── 📂 code
│       │   │   ├── 📂 exception
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
