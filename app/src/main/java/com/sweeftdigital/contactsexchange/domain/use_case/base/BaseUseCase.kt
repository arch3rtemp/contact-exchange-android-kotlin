package com.sweeftdigital.contactsexchange.domain.use_case.base

interface BaseUseCase<ARG_TYPE, RETURN_TYPE> {
    suspend fun start(arg: ARG_TYPE? = null): RETURN_TYPE
}