package com.musinsa.item.item.domain.property

import com.musinsa.item.common.util.MessageUtil
import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import com.musinsa.item.common.domain.PriceValidator
import java.math.BigDecimal

@Embeddable
@Access(AccessType.FIELD)
class RetailPrice : PriceValidator {

    @Column(name = "retail_price", precision = 10, scale = 2, nullable = false)
    private var _value: BigDecimal = BigDecimal.ZERO

    protected constructor()

    constructor(retailPrice: BigDecimal? = null) {
        validate(retailPrice, MessageUtil.getMessage("item.error.invalid.retailprice"))
        this._value = retailPrice!!
    }

    val value: BigDecimal
        get() = _value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RetailPrice

        return _value == other._value
    }
    override fun hashCode(): Int {
        return _value.hashCode()
    }

    override fun toString(): String = "RetailPrice(_value=$_value)"

}
