package com.jesusvilla.test.home.repositories

import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.ui.model.ForYouModel
import com.jesusvilla.test.home.ui.model.UserActionsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMockRepository @Inject constructor() {

    private var userActionModel: List<UserActionsModel>
    private var forYouModel: List<ForYouModel>

    init {
        userActionModel = buildUserAction()
        forYouModel = buildForYouModel()
    }

    private fun buildUserAction(): List<UserActionsModel> {
        return listOf(
            UserActionsModel(
                com.jesusvilla.test.designsystem.R.drawable.ic_parking_lot_letter,
                R.string.home_fragment_parking_action
            ),
            UserActionsModel(
                com.jesusvilla.test.designsystem.R.drawable.ic_credit_score,
                R.string.home_fragment_credit_score_action
            ),
            UserActionsModel(
                com.jesusvilla.test.designsystem.R.drawable.ic_local_mall,
                R.string.home_fragment_local_mall_action
            ),
            UserActionsModel(
                com.jesusvilla.test.designsystem.R.drawable.ic_shield_person,
                R.string.home_fragment_shield_person_action
            )
        )
    }

    private fun buildForYouModel(): List<ForYouModel> {
        return listOf(
            ForYouModel(
                com.jesusvilla.test.designsystem.R.drawable.carrusel_img_01,
                R.string.home_fragment_first_caorusel_title,
                R.string.home_fragment_first_caorusel_body,
                R.string.home_fragment_first_caorusel_link_text,
                "https://www.pase.com.mx/promociones/#:~:" +
                    "text=Adicional%2C%20te%20regalamos%20un%20a%C3%B1o," +
                    "App%20%E2%80%9CTu%20Tag%20PASE%E2%80%9D."
            ),
            ForYouModel(
                com.jesusvilla.test.designsystem.R.drawable.carrusel_img_02,
                R.string.home_fragment_second_caorusel_title,
                R.string.home_fragment_second_caorusel_body,
                R.string.home_fragment_second_caorusel_link_text,
                "https://www.pase.com.mx/noticias/tu-tag-rosa/"
            ),
            ForYouModel(
                com.jesusvilla.test.designsystem.R.drawable.carrusel_img_03,
                R.string.home_fragment_third_caorusel_title,
                R.string.home_fragment_third_caorusel_body,
                R.string.home_fragment_third_caorusel_link_text,
                "https://www.pase.com.mx/promociones/promocion-por-lanzamiento-banco-del-bajio/#:~:" +
                    "text=PASE%20y%20Banbaj%C3%ADo%20te%20bonifican,1%20de%20septiembre%20de%202023."
            )
        )
    }

    fun getUserActions(): List<UserActionsModel> {
        return this.userActionModel
    }

    fun getForYou(): List<ForYouModel> {
        return this.forYouModel
    }
}
