package testproject.cryptoexchanger.repository;

import org.springframework.data.jpa.repository.JpaRepository
import testproject.cryptoexchanger.model.WalletAddress

interface WalletAddressRepository : JpaRepository<WalletAddress, Long> {

}