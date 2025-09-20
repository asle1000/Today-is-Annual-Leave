enum class HashtagCategory(val label: String) {
    REST("휴식/충전"),
    TRAVEL("외출/여행"),
    HOBBY("취미/활동"),
    MOOD("감성/분위기");

    companion object {
        fun fromLabel(label: String): HashtagCategory {
            return entries.find { it.label == label }
                ?: throw IllegalArgumentException("Unknown category label: $label")
        }
    }
}