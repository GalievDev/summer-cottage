package dev.galiev.sc.helper

import java.time.LocalDate

enum class SolsticeDay(val from: LocalDate, val to: LocalDate) {
    SOLSTICE(
        LocalDate.of(LocalDate.now().year, 6, 21),
        LocalDate.of(LocalDate.now().year, 6, 22)
    );

    fun getDays(): Pair<LocalDate, LocalDate> {
        return if (from.isLeapYear) {
            Pair(
                LocalDate.of(from.year, from.month, from.dayOfMonth - 1),
                LocalDate.of(to.year, to.month, to.dayOfMonth - 1)
            )
        } else {
            Pair(
                LocalDate.of(from.year, from.month, from.dayOfMonth),
                LocalDate.of(to.year, to.month, to.dayOfMonth)
            )
        }
    }
}