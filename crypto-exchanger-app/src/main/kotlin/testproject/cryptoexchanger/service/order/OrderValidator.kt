package testproject.cryptoexchanger.service.order

import testproject.cryptoexchanger.dto.CreateOrderEntityDto

class OrderValidator {

    fun validateNewOrder(dto: CreateOrderEntityDto) {
        if (dto.price.signum() == 0) {
            throw RuntimeException("specify price to place new order")
        }

        if (dto.quantity.signum() == 0) {
            throw RuntimeException("specify quantity to place new order")
        }

    }
}