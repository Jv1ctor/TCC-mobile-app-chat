package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.StudentDto
import com.example.tccmobile.data.dto.UserDto
import com.example.tccmobile.data.entity.Student
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.time.ExperimentalTime

class AuthRepository {

    suspend fun signIn(registry: String, password: String): Boolean{
        return try {

            Log.d("SUPABASE_DEBUG", "registry: $registry")

            val user = client.postgrest.from("users").select {
                filter {
                    eq("registry", registry)
                }
            }.decodeSingle<UserDto>()

            val isStudent = client.postgrest.from("students").select {
                filter {
                    eq("user_id", user.id)
                }
            }.decodeSingleOrNull<StudentDto>()

            Log.d("SUPABASE_DEBUG", "user: ${user.toString()}")

            val auth = client.auth.signInWith(Email){
                this.email = user.email
                this.password = password
            }

            client.auth.updateUser {
                data = buildJsonObject {
                    put("isStudent", isStudent != null)
                }
            }

            Log.d("SUPABASE_DEBUG", "Auth retornou: $auth")
            true
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "ERRO NO SIGNIN", e)
            false
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun signUpStudent(student: Student, password: String): Boolean{
        return try {
            val auth = client.auth.signUpWith(Email){
                this.email = student.email
                this.password = password
            } ?: return false

            Log.d("SUPABASE_DEBUG", "Auth retornou: $auth")
            val userDto = UserDto(
                id = auth.id,
                name = student.name,
                email = student.email,
                registry = student.registry,
                createdAt = null
            )

           client.postgrest.from("users")
                .insert(userDto)

            val studentDto = StudentDto(
                userId = auth.id,
                course = student.course
            )

            client.postgrest.from("students")
                .insert(studentDto)
            true
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "ERRO NO SIGNUP", e)
            false
        }
    }

}