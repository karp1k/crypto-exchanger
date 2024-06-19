package testproject.cryptoexchanger.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import testproject.cryptoexchanger.model.Transaction

interface TransactionRepository : JpaRepository<Transaction, Long> {

    // todo: to complicated. required reasonable refactoring
    @Query("select t from Transaction t " +
            "left join fetch t.fromAccount fa " +
            "left join fetch t.toAccount ta " +
            "left join fetch t.bid bid " +
            "left join fetch t.ask ask " +
            "left join fetch bid.userEntity b_u " +
            "left join fetch ask.userEntity a_u " +
            "join fetch t.currency c " +
            "left join fetch fa.walletAddress fa_wa " +
            "left join fetch ta.walletAddress ta_wa " +
            "left join fetch fa_wa.wallet fa_wa_w " +
            "left join fetch ta_wa.wallet ta_wa_w " +
            "left join fetch fa_wa_w.userEntity fa_wa_w_u " +
            "left join fetch ta_wa_w.userEntity ta_wa_w_u " +
            "where ((fa_wa_w_u.username is not null and fa_wa_w_u.username = (:username)) or " +
            "(ta_wa_w_u.username is not null and ta_wa_w_u.username = (:username))) or " +
            " ((b_u.username is not null and b_u.username = (:username)) or (a_u.username is not null and a_u.username = (:username))) " +
            "order by t.createdAt asc")
    fun findByUsername(username: String): Set<Transaction>
}