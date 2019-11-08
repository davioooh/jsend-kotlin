package com.davioooh.jsend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertEquals

class ResponsesSerializationTest {
    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should serialize correctly SUCCESS response without data payload`() {
        val s = objectMapper.writeValueAsString(SuccessResponse<Unit>())
        assertEquals("""{"status":"success"}""", s)
    }

    @Test
    fun `should serialize correctly SUCCESS response with data payload`() {
        val s = objectMapper.writeValueAsString(
            SuccessResponse(
                mapOf(
                    "a" to 1,
                    "b" to 2,
                    "c" to 3
                )
            )
        )
        assertEquals("""{"data":{"a":1,"b":2,"c":3},"status":"success"}""", s)
    }

    @Test
    fun `should serialize correctly FAIL response`() {
        val s = objectMapper.writeValueAsString(
            FailResponse(
                mapOf(
                    "errorCode" to "error.filed.test"
                )
            )
        )
        assertEquals("""{"data":{"errorCode":"error.filed.test"},"status":"fail"}""", s)
    }

    @Test
    fun `should serialize correctly ERROR response (minimal)`() {
        val s = objectMapper.writeValueAsString(
            ErrorResponse<Unit>("An error occurred...")
        )
        assertEquals("""{"message":"An error occurred...","status":"error"}""", s)
    }

    @Test
    fun `should serialize correctly ERROR response with error code`() {
        val s = objectMapper.writeValueAsString(
            ErrorResponse<Unit>(message = "An error occurred...", code = 500)
        )
        assertEquals("""{"message":"An error occurred...","code":500,"status":"error"}""", s)
    }

    @Test
    fun `should serialize correctly ERROR response with data payload`() {
        val s = objectMapper.writeValueAsString(
            ErrorResponse(message = "An error occurred...", data = object {
                val x = 1
                val y = "y"
            })
        )
        assertEquals("""{"message":"An error occurred...","data":{"x":1,"y":"y"},"status":"error"}""", s)
    }

    @Test
    fun `should serialize correctly ERROR response (all params)`() {
        val s = objectMapper.writeValueAsString(
            ErrorResponse(message = "An error occurred...", code = 10, data = object {
                val x = 1
                val y = 2
            })
        )
        assertEquals("""{"message":"An error occurred...","code":10,"data":{"x":1,"y":2},"status":"error"}""", s)
    }

}