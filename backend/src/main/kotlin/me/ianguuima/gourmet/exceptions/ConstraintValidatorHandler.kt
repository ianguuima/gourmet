package me.ianguuima.gourmet.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.support.WebExchangeBindException
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ConstraintValidatorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(WebExchangeBindException::class)
    fun methodArgumentNotValidException(ex: WebExchangeBindException): FieldError {
        return processFieldErrors(ex)
    }

    private fun processFieldErrors(ex: WebExchangeBindException): FieldError {
        val result = ex.bindingResult
        val fieldErrors = result.fieldErrors

        val error = FieldError(
                "A validation error happened!"
        )

        for (fieldError in fieldErrors) {
            error.addFieldError(fieldError.field, fieldError.defaultMessage ?: "An error happened!")
        }

        return error
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class FieldError(val message: String) {

        val errors: MutableList<CustomFieldError> = ArrayList()

        fun addFieldError(path: String, message: String) {
            val error = CustomFieldError(path, message)
            errors.add(error)
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties("objectName", "bindingFailure")
    class CustomFieldError(val field: String = "", val message: String = "")
}