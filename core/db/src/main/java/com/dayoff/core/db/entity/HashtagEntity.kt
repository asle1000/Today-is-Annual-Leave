package com.dayoff.core.db.entity

import HashtagCategory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "hashtag")
data class HashtagEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "emoji") val emoji: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: HashtagCategory
)

/**

 휴식/충전
    [텍스트 2: 😴텍스트: 완전휴식]
    [텍스트 2: 🏠 텍스트: 집콕데이]
    [텍스트 2: 🛏️ 텍스트: 늦잠자기]
    [텍스트 2: 🧖️텍스트: 셀프케어]
    [텍스트 2: 🔋️ 텍스트: 재충전완료]
    [텍스트 2: ☁️️ 텍스트: 멍때리기]

 외출/여행
    [텍스트 2: ✈텍스트: 짧은여행]
    [텍스트 2: 🧺텍스트: 근교나들이]
    [텍스트 2: 🚗텍스트: 드라이브데이]
    [텍스트 2: 🌊텍스트: 바다보러]
    [텍스트 2: 🌲텍스트: 자연속으로]
    [텍스트 2: 🔍 텍스트: 새로운장소발견]

 취미/활동
     [텍스트 2: 🎨텍스트: 취미시간]
     [텍스트 2: 🏋️텍스트: 운동]
     [텍스트 2: 🍜텍스트: 맛집탐방]
     [텍스트 2: 🛍️텍스트: 쇼핑데이]
     [텍스트 2: 🎬️텍스트: 문화생활]
     [텍스트 2: 📵텍스트: 디지털디톡스]

 감성/분위기
     [텍스트 2: 🌤️텍스트: 느긋한하루]
     [텍스트 2: 💖텍스트: 행복한 하루]
     [텍스트 2: 🌙텍스트: 혼자만의시간]
     [텍스트 2: 🌀텍스트: 무계획의자유]
     [텍스트 2: 🎵텍스트: 잔잔한하루]
     [텍스트 2: ⏳텍스트: 시간이멈춘듯한날]

 */