## smart_mask_capston - COMMIT변경사항

**1. DatabaseHelper.kt**
-  Package 및 .kt file 생성

**2. AlarmFragment.kt / CalendarFragment.kt**
- db code 추가

**3. SettingFragment.kt**
- stream null 초기화 구문 변경 >> 초기화 선언 안할 시, DB, APP 터짐현상 발생.
```
{
var socket: BluetoothSocket? = null
var outputStream: OutputStream? = null
var inputStream: InputStream? = null
}
```

**4. alarm_fragment.xml / calendar_fragment.xml**
- UI 약간 수정(글씨, 높이)

**5. build.gradle**
- 7.3.1 -> 7.2.1 변경했음. -> 오류나면 다시 변경 해도 됨.
---
## 23.06.03 변경사항

### Error1.
binding.dateTextView.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
 - class : CalendarFragment.kt
 - error : month부분 06월 03일 경우 23-06-3로 출력되었음.
 - resolution : 23-06-03 형태로 출력 되도록 수정

### Error2.
var binding: SettingFragmentBinding? = null
 - class : SettingFragment.kt
 - error : alarm.kt에서 end시, bluetooth connect null error 발생
 - resolution : lateinit -> 일반var선언으로 변경. (늦은 선언 -> 즉시 선언)

### Error3.
binding!!.bluetoothOnButton.setBackgroundColor(Color.rgb(153,255,204))
 - class : SettingFragment.kt
 - error : bluetooth 관련된 객체, 함수 사용 시, null error 발생 
 - resolution : Error2 문제에 이어 binding 변수 사용 부분들도 binding -> binding!! 로 모두 변경. (!! = null검사) 
