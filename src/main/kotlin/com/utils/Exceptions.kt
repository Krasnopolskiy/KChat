package com.utils

class InvalidRequestData : Exception("Invalid request data")
class UserNameIsNotFreeException : Exception("Name is already taken")
class RoomNameIsNotFreeException : Exception("Name is already taken")
class InvalidCredentialsException : Exception("Invalid credentials")
class InvalidCodeException : Exception("Invalid code")
class UserWasBannedException : Exception("User was banned")
class UserNotInRoomException : Exception("User not in room")
