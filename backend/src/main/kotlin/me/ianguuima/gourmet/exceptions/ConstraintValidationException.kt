package me.ianguuima.gourmet.exceptions

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.ArrayList

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ConstraintValidationException(override val message: String?, val fieldErrors: MutableList<FieldError> = ArrayList()) : RuntimeException()