package com.serma.shopbucket.domain.usecase

import com.serma.shopbucket.domain.repository.UserRepository

class ValidateNicknameUseCase(private val userRepository: UserRepository) {
    operator fun invoke(nickname: String) = userRepository.validateNickname(nickname)
}