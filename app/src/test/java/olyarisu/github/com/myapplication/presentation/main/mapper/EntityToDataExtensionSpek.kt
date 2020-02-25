package olyarisu.github.com.myapplication.presentation.main.mapper

import org.junit.Assert.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class EntityToDataExtensionSpek : Spek({

    describe("convert score number to readable string for view") {
        context("when score < 999") {
            it("should be like initial value") {
                assertEquals(100.convertScoreToReadableString(), "100")
                assertEquals(0.convertScoreToReadableString(), "0")
                assertEquals(999.convertScoreToReadableString(), "999")
            }
        }

        context("when score > 999") {
            it("should be represent thousand with k and one digit after dot") {
                assertEquals(1000.convertScoreToReadableString(), "1.0k")
                assertEquals(32423.convertScoreToReadableString(), "32.4k")
                assertEquals(934484.convertScoreToReadableString(), "934.5k")
            }
        }
    }
})