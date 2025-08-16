package com.dayoff.core.ui.model

/**
 *  Created by KyunghyunPark at 2025. 8. 9.

 */
enum class ConsumptionType(val title: String, val description: String) {
    CONSUME(title = "연차 소진", description = "사용 가능한 연차 일수가 줄어듭니다."),
    NOT_CONSUME(title = "연차 미소진", description = "사용 가능한 연차는 그대로! 기록으로만 남겨요.");

    companion object {
        fun toList(): List<Pair<String, String>> {
            val options = ConsumptionType.entries.toTypedArray().map { type ->
                type.title to type.description
            }

            return options
        }
    }
}