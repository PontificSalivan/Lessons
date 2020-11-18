package com.serma.shopbucket.domain.usecase

import com.serma.shopbucket.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    operator fun invoke(email: String, password: String) = userRepository.login(email, password)
}