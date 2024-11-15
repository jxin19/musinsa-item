package com.musinsa.item.common.handler

import com.musinsa.item.common.dto.BaseResponse
import com.musinsa.item.common.exception.DeleteConstraintException
import com.musinsa.item.common.util.MessageUtil
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException) =
        BaseResponse.of(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("common.error.invalid"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse> {
        val errors: Map<String, String?> = ex.bindingResult.allErrors
            .filterIsInstance<FieldError>()
            .associate { error -> error.field to error.defaultMessage }
        return BaseResponse.of(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("common.error.validation"), errors)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) =
        BaseResponse.of(HttpStatus.BAD_REQUEST, ex.message ?: MessageUtil.getMessage("common.error.invalid"))

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException) =
        BaseResponse.of(HttpStatus.NOT_FOUND, ex.message)

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException) =
        BaseResponse.of(HttpStatus.NOT_FOUND, ex.message)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException) =
        BaseResponse.of(HttpStatus.CONFLICT, ex.message)

    @ExceptionHandler(DeleteConstraintException::class)
    fun handleDeleteConstraintException(ex: DeleteConstraintException) =
        BaseResponse.of(HttpStatus.CONFLICT, ex.message)

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception) =
        BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
}

