package hu.aharcsa.auth.security

import org.springframework.core.env.Environment
import java.lang.UnsupportedOperationException

object Profiles {
    const val FORM_AUTH = "FORM_AUTH"
    const val BASIC_AUTH = "BASIC_AUTH"
    const val JDBC = "JDBC"
}

enum class Login {
    FORM,
    BASIC;
    companion object {
        fun from(env: Environment): Login {
            return when {
                env.activeProfiles.contains(Profiles.FORM_AUTH) -> {
                    FORM
                }
                env.activeProfiles.contains(Profiles.BASIC_AUTH) -> {
                    BASIC
                }
                else -> {
                    throw UnsupportedOperationException()
                }
            }
        }
    }

}