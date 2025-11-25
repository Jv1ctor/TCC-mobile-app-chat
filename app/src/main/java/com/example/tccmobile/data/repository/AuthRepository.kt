package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.StudentDto
import com.example.tccmobile.data.dto.UserDto
import com.example.tccmobile.data.entity.Student
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest

class AuthRepository {

    suspend fun signUpStudent(student: Student,  password: String): Boolean{
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