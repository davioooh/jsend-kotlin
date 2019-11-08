package com.davioooh.jsend

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResponsesDeserializationTest {
    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should deserialize correctly SUCCESS response without data payload`() {
        val o = objectMapper.readValue<SuccessResponse<*>>("""{"status":"success"}""")
        assertEquals(Status.SUCCESS, o.status)
        assertNull(o.data)
    }

    @Test
    fun `should deserialize correctly SUCCESS response with data payload`() {
        val o =
            objectMapper.readValue<SuccessResponse<*>>("""{"status":"success","data":{"a":1,"b":2,"c":3}}""")
        assertEquals(Status.SUCCESS, o.status)
        assertEquals(
            mapOf(
                "a" to 1,
                "b" to 2,
                "c" to 3
            ),
            o.data
        )
    }

    @Test
    fun `should deserialize correctly FAIL response`() {
        val o =
            objectMapper.readValue<FailResponse<*>>("""{"status":"fail","data":{"errorCode":"error.filed.test"}}""")
        assertEquals(Status.FAIL, o.status)
        assertEquals(
            mapOf(
                "errorCode" to "error.filed.test"
            ),
            o.data
        )
    }

    @Test
    fun `should deserialize correctly ERROR response (minimal)`() {
        val o =
            objectMapper.readValue<ErrorResponse<*>>("""{"status":"error","message":"An error occurred..."}""")
        assertEquals(Status.ERROR, o.status)
        assertEquals("An error occurred...", o.message)
        assertNull(o.code)
        assertNull(o.data)
    }

    @Test
    fun `should deserialize correctly ERROR response with error code`() {
        val o =
            objectMapper.readValue<ErrorResponse<*>>("""{"status":"error","message":"An error occurred...","code":500}""")
        assertEquals(Status.ERROR, o.status)
        assertEquals("An error occurred...", o.message)
        assertEquals(500, o.code)
        assertNull(o.data)
    }

    @Test
    fun `should deserialize correctly ERROR response with data payload`() {
        val o =
            objectMapper.readValue<ErrorResponse<*>>("""{"status":"error","data":{"x":1,"y":"y"},"message":"An error occurred..."}""")
        assertEquals(Status.ERROR, o.status)
        assertEquals("An error occurred...", o.message)
        assertNull(o.code)
        assertEquals(
            mapOf(
                "x" to 1,
                "y" to "y"
            ),
            o.data
        )
    }

    @Test
    fun `should deserialize correctly ERROR response (all params)`() {
        val o =
            objectMapper.readValue<ErrorResponse<*>>("""{"status":"error","data":{"x":1,"y":2},"message":"An error occurred...","code":10}""")
        assertEquals(Status.ERROR, o.status)
        assertEquals("An error occurred...", o.message)
        assertEquals(10, o.code)
        assertEquals(
            mapOf(
                "x" to 1,
                "y" to 2
            ),
            o.data
        )
    }

}