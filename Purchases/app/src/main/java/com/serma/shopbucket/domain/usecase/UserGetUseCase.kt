package com.serma.shopbucket.domain.usecase

import com.serma.shopbucket.domain.repository.UserRepository

class UserGetUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id: String) = userRepository.getUser(id)
}