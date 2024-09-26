package com.example.jettrips.utils

import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsKtTest {

    @Test
    fun isValidEmail() {

    }


    @Test
    fun parseTravelBlogs_validJson_shouldReturnList() {
        // Given a valid JSON string
        val jsonString = """
            [
                {
                    "name": "Nomadic Matt",
                    "url": "https://www.nomadicmatt.com",
                    "description": "A popular travel blog providing budget travel tips, guides, and itineraries."
                },
                {
                    "name": "The Blonde Abroad",
                    "url": "https://www.theblondeabroad.com",
                    "description": "A blog focusing on solo female travel with photography and lifestyle content."
                }
            ]
        """.trimIndent()

        // When parsing the JSON
        val result = jsonString.parseTravelBlog()

        // Then the list should contain 2 TravelBlog objects with correct data
        assertEquals(2, result.size)
        assertEquals("Nomadic Matt", result[0].name)
        assertEquals("https://www.nomadicmatt.com", result[0].url)
        assertEquals(
            "A popular travel blog providing budget travel tips, guides, and itineraries.",
            result[0].description
        )

        assertEquals("The Blonde Abroad", result[1].name)
        assertEquals("https://www.theblondeabroad.com", result[1].url)
        assertEquals(
            "A blog focusing on solo female travel with photography and lifestyle content.",
            result[1].description
        )
    }

    @Test
    fun parseTravelBlogs_emptyJson_shouldReturnEmptyList() {
        // Given an empty JSON array
        val jsonString = "[]"

        // When parsing the JSON
        val result = jsonString.parseTravelBlog()

        // Then the result should be an empty list
        assertTrue(result.isEmpty())
    }
}