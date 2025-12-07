package com.example.ddm_projetofinal.data.local

import com.example.ddm_projetofinal.data.entity.UserEntityLocal
import com.example.ddm_projetofinal.model.User

class UserLocalRepositoryImpl (
    private val dao: UserLocalDao
) : UserLocalRepository {

    override suspend fun insert(id: String, email: String, name: String, password: String) {
        val entity = UserEntityLocal (
            id = id,
            name = name,
            email = email,
            password = password
        )

        dao.insert(entity)
    }

    override suspend fun delete(id: String) {
        val existingEntity = dao.getById(id) ?: return
        dao.delete(existingEntity)
    }

    override suspend fun getSavedLogin (): User? {
        return dao.getSavedLogin()?.let { entity ->
             User (
                 id = entity.id,
                 name = entity.name,
                 email = entity.email,
                 password = entity.password
             )
        }
    }
}