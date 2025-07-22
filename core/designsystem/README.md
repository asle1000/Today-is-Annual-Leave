# Design System - 색상 사용 가이드

## 개요

이 디자인 시스템은 Compose에서 사용할 수 있는 색상 체계를 제공합니다. 모든 색상은 시맨틱하게 정의되어 있어 일관된 디자인을 유지할 수 있습니다.

## 색상 구조

### 1. 배경색 (Background Colors)
- `base`: 기본 배경색
- `brand`: 브랜드 관련 배경색
- `surface`: 서피스 배경색
- `disabled`: 비활성화 상태 배경색
- `success`: 성공 상태 배경색
- `warning`: 경고 상태 배경색
- `danger`: 위험 상태 배경색
- `utilities`: 유틸리티 배경색

### 2. 텍스트 색상 (Text Colors)
- `brand`: 브랜드 관련 텍스트 색상
- `danger`: 위험 상태 텍스트 색상
- `disabled`: 비활성화 상태 텍스트 색상
- `success`: 성공 상태 텍스트 색상
- `warning`: 경고 상태 텍스트 색상
- `surface`: 서피스 텍스트 색상

### 3. 아이콘 색상 (Icon Colors)
- 텍스트 색상과 동일한 구조를 가집니다.

### 4. 보더 색상 (Border Colors)
- `base`: 기본 보더 색상
- `surface`: 서피스 보더 색상
- `brand`: 브랜드 보더 색상
- `disabled`: 비활성화 상태 보더 색상
- `success`: 성공 상태 보더 색상
- `warning`: 경고 상태 보더 색상
- `danger`: 위험 상태 보더 색상

### 5. 버튼 색상 (Button Colors)
- `brand`: 브랜드 버튼 색상
  - `background`: 배경색
  - `text`: 텍스트 색상
  - `icon`: 아이콘 색상

## 사용법

### 1. 테마 적용

```kotlin
@Composable
fun MyApp() {
    TodayIsAnnualLeaveTheme {
        // 앱 콘텐츠
    }
}
```

### 2. 색상 접근

```kotlin
@Composable
fun MyComponent() {
    val colors = TialTheme.colors
    
    // 배경색 사용
    Box(
        modifier = Modifier
            .background(colors.background.brand.primary)
            .size(100.dp)
    )
    
    // 텍스트 색상 사용
    Text(
        text = "브랜드 텍스트",
        color = colors.text.brand.primary
    )
    
    // 보더 색상 사용
    Box(
        modifier = Modifier
            .background(colors.border.brand.primary)
            .size(100.dp)
    )
}
```

### 3. Material3 ColorScheme과의 통합

테마는 자동으로 Material3 ColorScheme으로 매핑됩니다:

```kotlin
// Material3 컴포넌트는 자동으로 테마 색상을 사용합니다
Button(onClick = { }) {
    Text("버튼")
}

// 또는 직접 MaterialTheme에서 접근
val colorScheme = MaterialTheme.colorScheme
Box(
    modifier = Modifier.background(colorScheme.primary)
)
```

## 색상 팔레트

### 브랜드 색상
- Primary: `#60D194`
- Secondary: `#6BD0BB`
- Tertiary: `#E8F8F0`

### 텍스트 색상
- Primary: `#020617`
- Secondary: `#334155`
- Tertiary: `#64748B`
- Placeholder: `#94A3B8`

### 상태 색상
- Success: `#3B8216`
- Warning: `#F59E0B`
- Danger: `#EF4444`
- Disabled: `#CBD5E1`

## 예시

```kotlin
@Composable
fun ColorExample() {
    val colors = TialTheme.colors
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.background.surface.primary)
            .padding(16.dp)
    ) {
        // 브랜드 배경
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(colors.background.brand.primary)
        )
        
        // 브랜드 텍스트
        Text(
            text = "브랜드 텍스트",
            color = colors.text.brand.primary
        )
        
        // 보더 예시
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(colors.background.base.primary)
                .padding(2.dp)
                .background(colors.border.brand.primary)
        )
    }
}
```

## 주의사항

1. **일관성 유지**: 항상 `TialTheme.colors`를 통해 색상에 접근하세요.
2. **시맨틱 사용**: 색상의 의미에 맞게 사용하세요 (예: success는 성공 상태에만 사용).
3. **접근성**: 색상 대비가 충분한지 확인하세요.
4. **다크 테마**: 현재는 라이트 테마만 지원됩니다. 다크 테마 지원이 필요하면 추가 구현이 필요합니다. 