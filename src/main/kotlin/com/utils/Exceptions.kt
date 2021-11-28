package com.utils

class InvalidRequestData : Exception("Invalid request data")
class UserNameIsNotFreeException : Exception("User name is already taken")
class RoomNameIsNotFreeException : Exception("Room name is already taken")
class InvalidCredentialsException : Exception("Invalid credentials")
class InvalidCodeException : Exception("Invalid code")
class InvalidSessionException : Exception("Invalid session")
class UserNotInRoomException : Exception("User not in room")
