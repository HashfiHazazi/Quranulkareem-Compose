package com.ergophile_project.quranulkareem.data.kotpref

import com.chibatching.kotpref.KotprefModel

object LastReadState: KotprefModel() {
    var readQoranType by intPref(-1)

    var surahName by stringPref("")

    var surahNum by intPref(-1)

    var ayahNumber by intPref(-1)

    var index by intPref(-1)
}