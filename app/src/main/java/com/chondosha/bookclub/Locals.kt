package com.chondosha.bookclub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.chondosha.bookclub.repositories.*

val LocalMessageRepository = staticCompositionLocalOf<MessageRepository> {
    error("No repository found!")
}
val LocalConversationRepository = staticCompositionLocalOf<ConversationRepository> {
    error("No repository found!")
}
val LocalGroupRepository = staticCompositionLocalOf<GroupRepository> {
    error("No repository found!")
}
val LocalUserRepository = staticCompositionLocalOf<UserRepository> {
    error("No repository found!")
}
val LocalLoginRepository = staticCompositionLocalOf<LoginRepository> {
    error("No repository found!")
}

@Composable
fun ProvideRepository(content: @Composable () -> Unit) {

    val messageRepository = remember { MessageRepository() }
    val conversationRepository = remember { ConversationRepository() }
    val groupRepository = remember { GroupRepository() }
    val userRepository = remember { UserRepository() }
    val loginRepository = remember { LoginRepository() }


    CompositionLocalProvider(
        LocalMessageRepository provides messageRepository,
        LocalConversationRepository provides conversationRepository,
        LocalGroupRepository provides groupRepository,
        LocalUserRepository provides userRepository,
        LocalLoginRepository provides loginRepository
    ) {
        content()
    }
}