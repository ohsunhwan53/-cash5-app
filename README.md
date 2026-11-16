# 현금5억 — 안드로이드 주식 매수 체크 앱

홈 화면 아이콘과 앱 이름: **현금5억**. 모든 체크리스트는 휴대폰 안에서만 처리되며 네트워크 권한이 없습니다.

## Android Studio 없이 APK 만들기 (GitHub Actions)

1. 이 ZIP 파일을 PC에 다운로드하고 **압축을 풉니다**.
2. [github.com](https://github.com)에 로그인하고 오른쪽 위 **+ → New repository**를 누릅니다.
3. Repository name에 `cash5-app`을 입력하고 **Private → Create repository**를 선택합니다.
4. 새 저장소에서 **uploading an existing file** 또는 **Add file → Upload files**를 선택합니다.
5. 압축을 푼 `cash5_app` 폴더의 **내용물 전체**(`.github`, `app`, `build.gradle`, `settings.gradle`, `gradle.properties`, `README.md`)를 업로드 화면으로 끌어다 놓습니다. **cash5_app 폴더 자체가 아닌 폴더 안의 내용물**이어야 합니다. `.github`는 숨김 폴더이므로 윈도우 탐색기에서 **보기 → 표시 → 숨긴 항목**을 켜야 할 수 있습니다.
6. 아래 **Commit changes**를 눌러 업로드합니다. 저장소 **Actions** 탭으로 이동한 다음 **Build Android APK** 워크플로를 선택합니다. 자동으로 실행되지 않았다면 **Run workflow**를 누릅니다.
7. 빌드가 **초록색 체크**로 완료되면 실행 내역을 클릭해 페이지 아래 **Artifacts → cash5-apk**를 다운로드합니다.
8. `cash5-apk.zip`에서 `app-debug.apk`를 꺼내 갤럭시 S25로 옮겨 설치합니다. 설치 허용 요청 시 파일을 열었던 앱의 **알 수 없는 앱 설치 허용**을 설정해야 할 수 있습니다.

**중요:** `cash5_app` 폴더가 저장소 최상위에 한 겹 더 들어가거나 `.github`를 빼먹으면 GitHub Actions가 실행되지 않습니다.

## 앱 기능

- 0. 투자 원금 입력(만원)
- 1. 시장상황 체크: 약세장 투자 70%, 강세장 100%, 각각 2종목 균등 배분(약세장 40/60, 강세장 60/40)
- 2. 테마점수 1/2/3점 확인(2점 이상 통과)
- 3. 약세장 관련 상승테마 YES 시 탈락
- 4. 테마 3등주 이내가 아니면 탈락
- 5. 세력 평단가 확인
- 6. 세력 평단이 기준봉 시가 아래이면 탈락
- 7. 시가총액 700억 기준 주의사항 확인
- 8. 700억 미만 거래량 자동 통과 / 700억 이상 기준봉 대비 다음날 거래량 50% 이상 경고
- 9. 기준봉 시가/종가 중간값 계산, 그 중간값 -4% 손절가
- 10. 대장주 주가 확인
- 최종: 명시적 탈락 조건은 **종목 탈락**, 모든 통과 조건 충족은 **매수 검토해봐**, 진행 중에는 **체크 항목 확인 중**
- 거래량 경고는 8번에만 표시하며 최종 결과 화면에서는 반복하지 않음
- 앱을 다시 열어도 입력 내용이 유지됨(안드로이드 SharedPreferences), 처음부터 다시 시험하기 버튼 제공

## 기술 정보

- Android Java + WebView(앱에 HTML/CSS/JavaScript 포함), Gradle 8.9, Android Gradle Plugin 8.7.3, Java 17.
- 최소 Android 7.0(API 24), 대상 Android 14(API 34), compile SDK 35.
- 인터넷 없이 실행, 주식 주문·증권계좌 연동·데이터 송신 기능 없음.
- `app-debug.apk`는 개인 테스트용 디버그 APK입니다. Play 스토어 배포에는 릴리스 빌드와 서명이 필요합니다.
- 이 환경에서는 APK 컴파일을 수행하지 못했으므로 실제 빌드 성공은 GitHub Actions 실행 후 확인해야 합니다.
