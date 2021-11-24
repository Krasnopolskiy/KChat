package com.controllers

class InvalidRequestData : Exception("Invalid request data")
class UserNameIsNotFreeException : Exception("Name is already taken")
class RoomNameIsNotFreeException : Exception("Name is already taken")
class InvalidCredentialsException : Exception("Invalid credentials")
class UserWasBannedException : Exception("User was banned")
