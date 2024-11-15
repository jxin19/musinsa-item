package com.musinsa.item.brand.domain.property

import com.musinsa.item.common.util.MessageUtil
import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import com.musinsa.item.common.domain.StringValidator

@Embeddable
@Access(AccessType.FIELD)
class BrandName : StringValidator {

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private var _value: String = ""

    protected constructor()

    constructor(name: String) {
        validateLength(name, MessageUtil.getMessage("brand.error.name.too.short", MIN_LENGTH))
        validateRule(name, REGEX, MessageUtil.getMessage("brand.error.name.length.invalid", MIN_LENGTH, MAX_LENGTH))
        this._value = name.trim()
    }

    val value: String
        get() = _value


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BrandName

        return _value == other._value
    }

    override fun hashCode(): Int {
        return _value.hashCode()
    }

    override fun toString(): String {
        return "Name(value=$_value)"
    }

    companion object {
        private const val MIN_LENGTH: Int = 1
        private const val MAX_LENGTH: Int = 100
        private const val REGEX: String = "^.{$MIN_LENGTH,$MAX_LENGTH}$"
    }

}
