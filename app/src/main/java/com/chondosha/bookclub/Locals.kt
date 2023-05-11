package com.chondosha.bookclub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.chondosha.bookclub.repositories.ConversationRepository
import com.chondosha.bookclub.repositories.GroupRepository
import com.chondosha.bookclub.repositories.MessageRepository
import com.chondosha.bookclub.repositories.UserRepository

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

@Composable
fun ProvideRepository(content: @Composable () -> Unit) {

    val messageRepository = remember { MessageRepository() }
    val conversationRepository = remember { ConversationRepository() }
    val groupRepository = remember { GroupRepository() }
    val userRepository = remember { UserRepository() }


    CompositionLocalProvider(
        LocalMessageRepository provides messageRepository,
        LocalConversationRepository provides conversationRepository,
        LocalGroupRepository provides groupRepository,
        LocalUserRepository provides userRepository
    ) {
        content()
    }
}