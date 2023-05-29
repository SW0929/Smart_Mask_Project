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
