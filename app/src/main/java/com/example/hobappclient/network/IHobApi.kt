package com.example.hobappclient.network

import com.example.hobappclient.data.categories.CategoryRequest
import com.example.hobappclient.data.categories.CategoryResponse
import com.example.hobappclient.data.classifications.ClassificationRequest
import com.example.hobappclient.data.classifications.ClassificationResponse
import com.example.hobappclient.data.memberships.MembershipRequest
import com.example.hobappclient.data.memberships.MembershipResponse
import com.example.hobappclient.data.messages.MessageRequest
import com.example.hobappclient.data.messages.MessageResponse
import com.example.hobappclient.data.rooms.RoomRequest
import com.example.hobappclient.data.rooms.RoomResponse
import com.example.hobappclient.data.users.UserResponse
import com.example.hobappclient.data.usersgroups.UserGroupRequest
import com.example.hobappclient.data.usersgroups.UserGroupResponse
import fstb.hobbiesly.data.login.LoginRequest
import fstb.hobbiesly.data.login.LoginResponse
import fstb.hobbiesly.data.register.RegisterRequest
import fstb.hobbiesly.data.register.RegisterResponse
import fstb.hobbiesly.data.updateprofile.UpdateProfileRequest
import fstb.hobbiesly.data.updateprofile.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.*

interface IHobApi {
    companion object{
        const val REGISTER = "register/"
        const val LOGIN = "loginng/?format=json"
        const val UPDATE_PROFILE = "update-profile/"
        const val USERS = "users/"
        const val USER_GROUPS = "usergroups/"
        const val CATEGORIES = "categories/"
        const val MEMBERSHIPS = "memberships/"
        const val CLASSIFICATIONS = "classifications/"
        const val MESSAGES = "messages/"
        const val MESSAGES_BY_ROOM = "messages/by-room/"
        const val ROOMS = "rooms/"
        const val ROOMS_BY_GROUP = "rooms/by-group/"
    }

    @POST(REGISTER)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(@Header("Authorization") token:String, @Body updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse

    @GET(USERS)
    suspend fun getUsers(@Header("Authorization") token:String): Response<ArrayList<UserResponse>>

    @GET(USER_GROUPS)
    suspend fun getUserGroups(@Header("Authorization") token:String): Response<ArrayList<UserGroupResponse>>

    @GET("$USER_GROUPS{group_id}/")
    suspend fun getUserGroupById(@Header("Authorization") token:String, @Path("group_id") groupId:String): Response<UserGroupResponse>

    @GET(USER_GROUPS)
    suspend fun getUserGroupsByCategory(@Header("Authorization") token: String,@Query("category_id") category_id: String): Response<ArrayList<UserGroupResponse>>

    @POST(USER_GROUPS)
    suspend fun postUserGroup(@Header("Authorization") token:String, @Body userGroupRequest: UserGroupRequest): Response<UserGroupResponse>

    @GET(CATEGORIES)
    suspend fun getCategories(@Header("Authorization") token:String): Response<ArrayList<CategoryResponse>>

    @POST(CATEGORIES)
    suspend fun postCategory(@Header("Authorization") token:String, @Body categoryRequest: CategoryRequest): Response<CategoryResponse>

    @GET(ROOMS)
    suspend fun getRooms(@Header("Authorization") token:String): Response<ArrayList<RoomResponse>>

    @GET("$ROOMS{id}")
    suspend fun getRoomById(@Header("Authorization") token:String,@Path("id") id: String): Response<RoomResponse>

    @GET("$ROOMS_BY_GROUP{group_id}")
    suspend fun getRoomsByGroup(@Header("Authorization") token:String,@Path("group_id") group_id: String): Response<ArrayList<RoomResponse>>

    @POST(ROOMS)
    suspend fun postRoom(@Header("Authorization") token:String, @Body roomRequest: RoomRequest): Response<RoomResponse>

    @GET(MESSAGES)
    suspend fun getMessages(@Header("Authorization") token:String): Response<ArrayList<MessageResponse>>

    @GET("$MESSAGES_BY_ROOM{room_id}")
    suspend fun getMessagesByRoom(@Header("Authorization") token:String,@Path("room_id")room_id:String): Response<ArrayList<MessageResponse>>

    @POST(MESSAGES)
    suspend fun postMessage(@Header("Authorization") token:String, @Body messageRequest: MessageRequest): Response<MessageResponse>

    @GET(MEMBERSHIPS)
    suspend fun getMemberships(@Header("Authorization") token:String): Response<ArrayList<MembershipResponse>>

    @POST(MEMBERSHIPS)
    suspend fun postMembership(@Header("Authorization") token:String, @Body membershipRequest: MembershipRequest): Response<MembershipResponse>

    @GET(CLASSIFICATIONS)
    suspend fun getClassifications(@Header("Authorization") token:String): Response<ArrayList<ClassificationResponse>>

    @POST(CLASSIFICATIONS)
    suspend fun postClassifications(@Header("Authorization") token:String, @Body classificationRequest: ClassificationRequest): Response<ClassificationResponse>

    @GET("$USERS{user}")
    suspend fun getUserME(@Header("Authorization") token:String, @Path("user") user: String?) : Response<UserResponse>

}