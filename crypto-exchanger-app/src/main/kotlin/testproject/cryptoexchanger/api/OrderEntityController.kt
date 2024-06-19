package testproject.cryptoexchanger.api

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import testproject.cryptoexchanger.api.OrderEntityController.Companion.PATH_V1
import testproject.cryptoexchanger.dto.CreateOrderEntityDto
import testproject.cryptoexchanger.dto.OrderBookDto
import testproject.cryptoexchanger.dto.OrderEntityDto
import testproject.cryptoexchanger.service.order.OrderEntityService

@RequestMapping(PATH_V1)
@RestController
class OrderEntityController(private val orderEntityService: OrderEntityService) : OrderEntityApi {

    companion object {
        const val PATH_V1 = "/api/v1/order"
    }

    @PostMapping
    override fun create(@RequestBody dto: CreateOrderEntityDto) {
        orderEntityService.placeOrder(dto, SecurityContextHolder.getContext().authentication.name)
    }

    @GetMapping
    override fun getAll(): Set<OrderEntityDto> {
        return orderEntityService.getAllByUsername(SecurityContextHolder.getContext().authentication.name)
    }

    @GetMapping("/book")
    override fun getAllActive(): Set<OrderBookDto> {
        return orderEntityService.getAllActive()
    }


}