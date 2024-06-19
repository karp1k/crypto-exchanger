package testproject.cryptoexchanger.api

import testproject.cryptoexchanger.dto.CreateOrderEntityDto
import testproject.cryptoexchanger.dto.OrderBookDto
import testproject.cryptoexchanger.dto.OrderEntityDto

interface OrderEntityApi {

    fun create(dto: CreateOrderEntityDto)
    fun getAll(): Set<OrderEntityDto>
    fun getAllActive(): Set<OrderBookDto>
}