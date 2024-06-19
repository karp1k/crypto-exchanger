package testproject.cryptoexchanger.mapper

import testproject.cryptoexchanger.dto.CreateOrderEntityDto
import testproject.cryptoexchanger.dto.OrderBookDto
import testproject.cryptoexchanger.dto.OrderEntityDto
import testproject.cryptoexchanger.model.Account
import testproject.cryptoexchanger.model.OrderEntity
import testproject.cryptoexchanger.model.UserEntity
import java.math.BigDecimal

object OrderMapper {

    fun toEntity(
        dto: CreateOrderEntityDto,
        baseAccount: Account,
        quoteAccount: Account,
        hold: BigDecimal,
        userEntity: UserEntity,
    ): OrderEntity {
        return OrderEntity(
            price = dto.price,
            quantity = dto.quantity,
            direction = dto.direction,
            baseAccount = baseAccount,
            quoteAccount = quoteAccount,
            hold = hold,
            userEntity = userEntity
        )
    }

    fun toDto(orderEntity: OrderEntity): OrderEntityDto {
        return OrderEntityDto(
            id = orderEntity.id!!,
            price = orderEntity.price,
            baseCurrencyCode = orderEntity.quoteAccount.currency.code,
            quoteCurrencyCode = orderEntity.baseAccount.currency.code,
            quantity = orderEntity.quantity,
            quantityFulfilled = orderEntity.quantityFulfilled,
            hold = orderEntity.hold,
            direction = orderEntity.direction,
            status = orderEntity.status,
            createdAt = orderEntity.createdAt!!,
            modifiedAt = orderEntity.modifiedAt!!
        )
    }

    fun toOrderBookDto(orderEntity: OrderEntity): OrderBookDto {
        return OrderBookDto(
            baseCurrencyCode = orderEntity.baseAccount.currency.code,
            quoteCurrencyCode = orderEntity.quoteAccount.currency.code,
            price = orderEntity.price,
            quantity = orderEntity.quantity,
            direction = orderEntity.direction
        )
    }
}