package com.example.tccmobile.data.repository

import android.util.Log
import com.example.tccmobile.data.dto.StudentDto
import com.example.tccmobile.data.dto.UserDto
import com.example.tccmobile.data.entity.Student
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class UserRepository {
    suspend fun getStudentById(id: String): Student?{
        return try {

            val student = client.postgrest.from("students").select{
                filter {
                    eq("user_id", id)
                }
            }.decodeSingle<StudentDto>()

            val user = client.postgrest.from("users").select{
                filter {
                    eq("id", id)
                }
                limit(1)
            }.decodeSingle<UserDto>()


            Student(
                email = user.email,
                name = user.name,
                registry = user.registry,
                course = student.course
            )
        }catch (e: Exception){
            Log.e("SUPABASE_DEBUG", "Erro em buscar usuario $id $e")
            null
        }
    }
}