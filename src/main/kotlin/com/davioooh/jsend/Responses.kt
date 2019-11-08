package com.davioooh.jsend

import com.davioooh.jsend.Status.*


/**
 * JSend response status.
 *
 * @author David Castelletti
 */
enum class Status { SUCCESS, FAIL, ERROR }


/**
 * Generic JSend response.
 *
 * @param <T> type of response data.
 *
 * @author David Castelletti
 */
abstract class JSendResponse<T>(val status: Status, val data: T? = null)


/**
 * Success response.
 *
 * @param <T> type of response data.
 *
 * @author David Castelletti
 */
class SuccessResponse<T>(data: T? = null) : JSendResponse<T>(SUCCESS, data)


/**
 * Fail response.
 *
 * @param <T> type of response data.
 *
 * @author David Castelletti
</T> */
class FailResponse<T>(data: T) : JSendResponse<T>(FAIL, data)


/**
 * Error response.
 *
 * @param <T> type of response data.
 *
 * @author David Castelletti
</T> */
class ErrorResponse<T>(val message: String, val code: Int? = null, data: T? = null) :
    JSendResponse<T>(ERROR, data)